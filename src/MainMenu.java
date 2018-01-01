import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements KeyListener{ //ó�� ���� �޴� JPanel�� ��ӹް�, KeyListener�� ����

	private int currentOption = 1; //ó���� Start�� ����Ű�� �ִ�.

	JFrame f; 
	Image img; 
	Color color;
	ScoreBoard scoreBoard;
	Ship ship;
	String name;
	JPanel information;

			
	public MainMenu(){
		super();
		f = new JFrame(); //�����ӿ� �г��� �߰��ϰ� ����
		
		f.setTitle("My Game");
		f.add(this);
		f.setSize(850, 650);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false); //ũ�� ���� X 
		f.setVisible(true);
		this.addKeyListener(this);

		try{ //�̹��� ���� ó��
			   img = ImageIO.read(new File("res/Woodland.jpg")); //�̹��� ���ҽ� �ε�
			  }catch (IOException e) {
			   System.out.println("no image");
			   System.exit(1);
			  }

		
		this.requestFocus();
		setFocusable(true);  //Ű���� ��Ŀ���� ��û�ϰ� �г��� ��Ŀ���� ���� �� �ְ� �Ѵ�.
		super.repaint(); //�̹����� ó���� ��Ÿ���� ���ؼ�
	}
	
	public void paint(Graphics g){  //�޴��� �׷��ش�.
		
		super.paint(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this); //�̹����� �ҷ��´�.
		
		g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,110));
		g.setColor(color.RED);
		g.drawString("GO_SOILDER", 100, 100); //Go_SOILDER �۾� ��Ÿ����.
		g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,70));
		g.setColor(color.BLUE);
		g.drawString("START", 300, 400); //START �۾� ��Ÿ����.
		g.drawString("SCOREBOARD", 300, 470); //SCOREBoard �۾� ��Ÿ����.
		g.drawString("QUIT", 300, 540); //QUIT �۾� ��Ÿ����.
		
		
		switch(currentOption){  //�޴��������Ҷ� �Ķ��۾����� ����� �۾��� ��Ÿ���� ���� switch case��
		case 1:
			g.setColor(color.YELLOW);
			g.drawString("START", 300, 400);
			break;
		case 2:			
			g.setColor(color.YELLOW);
			g.drawString("SCOREBOARD", 300, 470);
			break;
		case 3:				
			g.setColor(color.YELLOW);
			g.drawString("QUIT", 300, 540);
			break;
			}
	}
	
	public void keyPressed(KeyEvent event) { //Ű���� �� �Ʒ� ��ư�� �̿��ؼ� �޴� ����
		// TODO Auto-generated method stub
		
		if(event.getKeyCode() == KeyEvent.VK_DOWN){ //�Ʒ� ��ư�� �������
			currentOption++;
			if(currentOption == 4)
				currentOption = 1; //QUIT���� ȭ��ǥ �Ʒ�Ű�� ������ START ��ư�� �ȴ�.
		repaint();	
		}
		if(event.getKeyCode() == KeyEvent.VK_UP){ //�� ��ư�� �������
				currentOption--;
				if(currentOption == 0)
					currentOption = 3;  //START���� ȭ��ǥ ����ư�� ������ QUIT ��ư�� �ȴ�.
				repaint();}
		if((event.getKeyCode() == KeyEvent.VK_SPACE)) { //�����̽� �ٸ� ���� �޴��� ������ ���
			selectOption(); //�ش� �޴��� ����
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void selectOption() { //�޴� ����
		if(currentOption == 1) { //START ��ư�� �������� ���
			new GameBoard(); //GameBoard ��ü ����
			f.dispose(); //��ü�� �����ϸ� â�� �����ϱ�, ������ â�� ����
		}

		if(currentOption == 2) {//SCOREBOARD ��ư�� �������� ���
			new Ranking(); //��ŷ ��ü ����
			f.dispose(); //��ü�� �����ϸ� â�� �����ϱ�, ������ â�� ����
		}
		
		if(currentOption == 3) { //QUIT ��ư�� �������� ���
			System.exit(0); //����
		}
	}
	
	public static void main(String[] args) { //���� �Լ�
		new MainMenu();
	}

}
