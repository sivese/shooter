import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameBoard extends JPanel implements KeyListener{ 
	ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //���׸� Ŭ������ ���(ArrayList�� �÷��� �� �ϳ� )
	ArrayList<Missile> missiles = new ArrayList<Missile>(); //���׸� Ŭ������ ���(ArrayList�� �÷��� �� �ϳ�) 
	Enemy enemy;
	Ship ship;
	Missile missile;
	ScoreBoard scoreBoard;
	Color color;

	JFrame f;
	JTextField text;
	String name;
	JLabel label;
	JPanel information; //��ŷ �Է� â
	JButton button;

	Image img;

	boolean on = true; //���� ���� ��� ���ؼ�



	public GameBoard(){  //������ 

		f = new JFrame(); //�����ӿ� �г��� �߰��ϰ� ����
		information=new JPanel();
		button=new JButton("�Է�");
		text=new JTextField(10);
		label=new JLabel("�̸��� �Է��Ͻÿ�.");
		button.addActionListener(new ActionListener() { //���� Ŭ����
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == button)
					saveScore(); //��ư Ŭ���� ���� ����
			}
		});

		text.addActionListener(new ActionListener() { //����Ŭ����
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == text)
					saveScore(); //���� �Է½� ��������
			}
		});
		information.add(label);
		information.add(text);
		information.add(button);

		f.setTitle("My Game");
		f.add(this);
		f.setSize(850, 650);
		f.setResizable(false); //ũ�� ���� �Ұ��� �ϰ� �ϱ� ���ؼ�
		f.setVisible(true);
		this.addKeyListener(this);
		this.requestFocus();
		this.setFocusable(true); //Ű������ ��Ŀ���� �ֱ� ���ؼ�

		try{
			img = ImageIO.read(new File("res/Woodland.jpg"));	//�̹��� ���ҽ� �ε�
		}catch (IOException e) {
			System.out.println("no image");
			System.exit(1);
		}



		ship = new Ship(); //ship��ü ����
		scoreBoard = new ScoreBoard(ship); //scoreBorad ��ü����
		scoreBoard.resetGame(); //������ �ð� �ʱ�ȭ

		for(int i=0;i<6;i++){
			makeEnemy();	// �ʱ� �� 6�� ����
		}

		(new Thread(new MyThread())).start(); //�⺻ �����带 �����Ѵ�.
		(new Thread(new createEnemy())).start(); //�� ��ȯ �����带 �����Ѵ�.

	}//������ ����

	// �̻����� ����� missiles�� �߰��Ѵ�
	public void makeMissile() {	
		missiles.add(new Missile(this, ship));
	}
	// ���� ������ ��ġ�� ���� enemies�� �߰��Ѵ�

	public void makeEnemy() {	
		int ex, ey; //������ ��ǥ
		do {
			ex = (int) (Math.random() * 580); //x�� ���� ��ǥ
			ey = (int) (Math.random() * 500); //y�� ���� ��ǥ
		}while(!((ex > 520 || ex < 20) && (ey > 450 || ey < 20))); //������ ��ġ�� ���� �����ǵ���

		enemies.add(new Enemy(ex, ey, 50, 50, ship));
	}
	//�� �����尡 paint �޼ҵ带 ����ϸ� ����ȭ ���ش�
	
	
	public synchronized void paint(Graphics g){  
		super.paint(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this); //�̹��� ���
		setOpaque(false); //���� ��Ȱ��ȭ

		scoreBoard.draw(g); //���ھ� ���带 �׸���.
		ship.draw(g); //���ּ��� �׸���.

		try {
			for(Missile m : missiles){ //��ȯ�� �̻��� ������ŭ �̻����� �׸���.
				m.draw(g);
			}
			for(Enemy e : enemies) { //��ȯ�� ����ŭ ���� �׸���.
				e.draw(g);
			}
		} catch (Exception e) {

		}


		if(ship.CRASH){ //���ּ��� ���� �ε��� ���
			scoreBoard.life --; //��� ���� ����
			ship.CRASH = false; //�ٽ� �����ϱ� ���ؼ� false�� ����
			ship.x = 300; //�׾��� �� ���ȯ ��ġ ����
			ship.y = 300;

			if(scoreBoard.life == 0){ //����� ������ 0���� ��
				g.setColor(color.RED);
				g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,70)); // ������ �۾��� GAME OVER�� ��Ÿ����.
				g.drawString("GAME OVER", 100, 200);
				on = false; //���� ���� ����
				try {
					this.finalize(); //���� ������ ����
				} catch (Throwable e) { //������ ����� ���� ����
					e.printStackTrace();
				}
			}
		}
		if(scoreBoard.VICTORY){ //60�ʰ� ������ ���ӿ��� �̱�� VICTORY �۾��� ��Ÿ����.
			g.setColor(color.CYAN);
			g.drawString("VICTORY", 270, 300);
		}

	}
	
	//�� ��ȯ ������
	public class createEnemy implements Runnable { //Runnable ����
		public void run() {
			while(on) { //���� ���� ������ ��
				try {
					Thread.sleep(500); //0.5�ʸ��� ������ ����
					if(enemies.size() < 3 + (6 - (ScoreBoard.TIME / 10))) //10�ʰ�  ���� ���� ���� �� ���� ����� �Ѵ�. 
						makeEnemy(); 
					scoreBoard.LEVEL = 6 -scoreBoard.TIME / 10;	//�ð��� ���� ���� ���
					repaint(); //��ü�� �����ϰ� �ٷ� �׷��ش�.
				} catch(InterruptedException e) {
				}
			}
			Score(); //���� �Է�ĭ �ҷ�����
		}
	}

	//���� ���� ������

	public class MyThread implements Runnable{ //Runnable ����
		public void run(){
			while(on){ //������ �������϶� 
				ship.update(); //���� ��ġ �޾ƿ�
				int ms, es;  //ms �� es�� ��ü�� ��������� Ȯ���ϱ� ���� ������ ����

				//�̻��ϰ� ���� �浹�� üũ�Ͽ� �浹�� �̻��ϰ�, �� ����
				ms = missiles.size(); //�浹 üũ�� �̻��� ����
				es = enemies.size(); //ȭ�鿡 ��Ÿ�� �ִ�  �� ����
				for( int i = 0; i < missiles.size(); i++){ 
					for(int j = 0; j < enemies.size(); j++){
						if(missiles.size() != ms || enemies.size() != es) // ó�� ũ��� �ٸ��� for���� �������´�
							break; //remove�ϸ��� ��Ұ� ������Ƿ� �Ϻη� ms�� es�� �����ϰ�, ��Ұ� ����ǹǷ� ������ ���ϱ� ���ؼ�

						missile = missiles.get(i); //���° �̻������� �޾ƿ´�. 
						missile.update(); //�� �̻����� ��ġ ����
						enemy = enemies.get(j); //Ȯ�� ���� ���� ���� �޾ƿ´�.

						if(missile.collision(enemy)){	// �̻��ϰ� �޾ƿ� ������ ���� �浹�ϸ�
							ScoreBoard.SCORE += 50; //���� 50�� ����
							missiles.remove(i);	// �̻����� ����
							enemies.remove(j); // ���� ����
						}
					}
					if(missile.x<-10 || missile.x>630 || missile.y<-10 || missile.y>640) {
						missiles.remove(i); // �̻����� ȭ�� ������ ������ �����Ѵ�
					}
				}
				//���ּ��� ���� �浹 üũ
				for(int i = 0; i < enemies.size(); i++) {
					enemies.get(i).update(); //������Ʈ ��Ų ���� ������ ������
					if(enemies.get(i).collision()) { // ���� ���ּ��� �浹�ϸ�
						ship.CRASH = true; //�浹�� true�� �ش�.
					}
				}

				scoreBoard.update(); // �������� �����Ѵ�

				if(scoreBoard.VICTORY) //�¸��ϸ� ������ �����Ų��.
					on = false;

				repaint();
				try{
					Thread.sleep(10); //0.01�ʸ��� ������ ���� 
				}catch(InterruptedException e){
				}
			}
		}
	}

	//���� �Է�â ��ȯ
	public void Score(){

		f.setLayout(null);
		f.add(information);
		information.setBounds(210,240,200,70);
		information.setVisible(true);
		f.setVisible(true);
		text.requestFocus(); //�ؽ�Ʈâ�� ��Ŀ�� �ش�,
	}
	
	//���� �Է� �Լ�
	public void saveScore(){
		PrintWriter out=null; //�Է� ���� �ʱ�ȭ
		try{
			name = text.getText(); //�ؽ�Ʈ â�� �Է��� ���� ����
			out=new PrintWriter(new FileWriter("res/score.txt",true)); //true�� ���������ν�, ������ �̾ ����Ѵ�.
			out.println(name); //�̸� �Է�
			out.println(ScoreBoard.SCORE); //���� �Է�
		}catch(IOException e){}
		finally{
			if(out!=null)
				out.close(); //���� �ݱ�
		}
		f.dispose(); //��ü�� �����ϸ� â�� �����ϱ�, ������ â�� ����
		new MainMenu(); // ���� ��� �� ���� ȭ������ ���ư�.
	}
 
      //Ű ����
	
	public void keyPressed(KeyEvent e) {
		if(on) { //������ ���� ���� ��
			ship.keyPressed(e); 
			if(ship.Present_Bullet<6 && e.getKeyCode() == KeyEvent.VK_SPACE){ //�Ѿ��� 6���� ���� �� ���� �Ұ�
                                                                              //�����̽� �� ������ �� ����
				ship.Present_Bullet++; //�Ѿ� �߻� �� ����
				makeMissile();	// �̻����� �����
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				ship.Present_Bullet = 0;	// RŰ�� ������ 1�� �ڿ� �Ѿ��� �����Ѵ�
				ship.RELOADING = true; //�ٽ� ������ �� �� �ְ� ����� �ش�.
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		ship.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}


