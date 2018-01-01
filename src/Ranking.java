import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.print.attribute.IntegerSyntax;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ranking extends JPanel implements KeyListener{

	JFrame f;
	Image img; //�̹����� �ҷ����� ���ؼ�
	Image img2;
	Color color;
	String name;
	Scanner input; //������ ���� ��ü
	String names[] = new String[100]; //��ŷ���� �̸� ���� �迭
	int scores[] = new int[100]; //��ŷ���� ���� ���� �迭
	String string_temp; //���� �ӽ� ����
	int scorecount = 0; //��� �� �������ؼ�.. ó���� 0���� �ʱ�ȭ
	int temp; //���� �ӽù���

	public Ranking(){
		super();

		f = new JFrame(); //�����ӿ� �г��� �߰��ϰ� ����

		f.setTitle("My Game");
		f.add(this);
		f.setSize(850, 650);
		f.setResizable(false);
		f.setVisible(true);
		this.addKeyListener(this);



		try{
			img = ImageIO.read(new File("res/Woodland.jpg"));		//�̹��� ���ҽ� �ε�
		}catch (IOException e) {
			System.out.println("no image");
			System.exit(1);
		}
		try{
			img2 = ImageIO.read(new File("res/Ranking.png"));		//�̹��� ���ҽ� �ε�
		}catch (IOException e) {
			System.out.println("no image");
			System.exit(1);
		}

		this.requestFocus(); //Ű���� ��Ŀ���� ��û�ϰ� �г��� ��Ŀ���� ���� �� �ְ� �Ѵ�.
		setFocusable(true);
		super.repaint(); //�̹����� ó���� �ε��� ������ �׷��ֱ� ���ؼ�

		try {
			input = new Scanner(Paths.get("res/score.txt"));//SCANNER��ü�� �̿��Ͽ� ������ ������. ������ ��δ� PathsŬ������ �̿��Ͽ� ����
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			while(input.hasNext()){ 
				names[scorecount] = input.nextLine();//�̸��� ������ �д´�.
				string_temp = input.nextLine(); //������ ���� �д´�.
				scores[scorecount] = Integer.parseInt(string_temp); //������ ���ڿ��̿��� ���������� ��ȯ
				scorecount++;
			}
		}catch(Exception e){
			e.toString();
		}

		//���� ����(��������)
		for(int j=0; j<scorecount;j++){

			for(int i=0; i<scorecount-j; i++){

				if(scores[i] < scores[i+1]){
					temp = scores[i];
					string_temp = names[i];
					scores[i] = scores[i+1];
					names[i] = names[i+1];
					scores[i+1] = temp;
					names[i+1] = string_temp;
				}

			}
		}

	}

	@Override	
	public void paint(Graphics g){  //�� �����尡 paint �޼ҵ带 ����ϸ� ����ȭ ���ش�.

		super.paint(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this); //������ �̹��� �׷���.
		g.setColor(color.BLACK);
		g.fillRect(30, 30, 400, 580);			

		g.drawImage(img2, 300, 0, 700, 913, this); //�ι�° ������ �̹��� �׷���.

		//���� �ҷ��� ���� ȭ�鿡 ���
		for(int i=0; i<scorecount; i++){
			g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,60));
			g.setColor(color.RED);
			g.drawString(String.valueOf(i+1), 100, 100*(i+1)); //���ڸ� ���ڿ��� �ٲ㼭 �׷��ش�. 

			g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,40));
			g.setColor(color.YELLOW);
			g.drawString(names[i], 150, 100*(i+1)); //�̸��� ����Ѵ�.
			
			g.setColor(color.GREEN.darker());
			g.drawString(String.valueOf(scores[i]), 300, 100*(i+1)); //���ڸ� ����Ѵ�.
			
			repaint();
		}

		g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,50)); 
		g.setColor(color.YELLOW);
		g.drawString("Press SPACE", 500, 540); //�ȳ��� ���
		g.drawString("to get BACK", 500, 600);

	}


	public void keyPressed(KeyEvent event) { 
		// TODO Auto-generated method stub
		if((event.getKeyCode() == KeyEvent.VK_SPACE)) { //�����̽� �ٸ� ������ ���� �޴��� ���ư���.
			new MainMenu(); //��ü �����
			f.dispose(); //��ü�� �����ϸ� â�� �����ϱ�, ������ â�� ����.
		}
	}


	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
