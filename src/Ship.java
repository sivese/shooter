import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship extends GraphicObj{ //GraphicObj �� ��� �޾Ƽ� �踦 ��Ÿ���� Ŭ������ �����Ѵ�.
	Color color;

	public static final int WIDTH = 30;  // ���ּ��� ũ��
	public static final int HEIGHT = 30; // ���ּ��� ����
	public static final int HEAD_LEFT = 1; //���ּ��� ����
	public static final int HEAD_RIGHT = 2; //���ּ��� ����
	public static final int HEAD_UP = 3; //���ּ��� ����
	public static final int HEAD_DOWN = 4; //���ּ��� ����
	public static final int CYCLE = 100; //�����ϴµ� 1�ʰ� �ɸ��� ����
	public static final int SPEED = 3; //���� �ӵ�
	
	BufferedImage img = null;		//�̹��� ����
	Font font;

	boolean CRASH = false; //���ּ��� ���̶� �ε�������
	boolean RELOADING = false; // ��������, �ʱ⿡�� false�� �༭ ���������� ������ �ǹ�
	
	int load_counter = 0; //���� ���� ����Ŭ ī���� �������ؼ�
	int HEAD = 1; //�� ���� �Ӹ� ��ġ, ��� �ٶ󺸰� �ִ���
	int Present_Bullet = 0; //���� ����� �Ѿ� ��(0���� �����ؼ� 6����)
	int dx, dy; //�̵��ӵ�

	public Ship(){ //���ּ� ������    
		x = 310;       //���ּ��� �ʱ� ��ġ ����
		y = 320;
		font = new Font("Italic",font.BOLD,17);
	}

	@Override
	public void update(){
		if(RELOADING && load_counter != CYCLE){  //������ ���̰� ���� �ð��� 1�ʰ� ���� ������ �ʾ��� ��
			load_counter++; 
			Present_Bullet = 6; //�Ѿ��� �� �Ἥ ���̻� �����

		}
		else if(RELOADING && load_counter == CYCLE){ //�������� ������ ���
			RELOADING = false; //���� �������� �����ϰ� ��.
			Present_Bullet = 0; //����� �Ѿ� �� �ʱ�ȭ
			load_counter = 0; //������ �ð� 0����.
		}
		x += dx; //���ּ��� ��ǥ�� �ٲ��ش�.
		y += dy;
	}

	public void keyPressed(KeyEvent event){ 
		if(event.getKeyCode() == KeyEvent.VK_LEFT){ //ȭ��ǥ ���� ����Ű�� ������ ���
			if(x>30){ //x��ǥ�� 30�̻��� ���
				HEAD = HEAD_LEFT; //���ּ��� ������ �ٶ󺻴�.
				dx = -SPEED; //x ��ǥ���� ����
			} else {
				dx = 0;		//���� �׵θ��� ����� ������ ���� ���ּ��� ��Ÿ������ �Ѵ�.
				x = 580;
			}
		}
		if(event.getKeyCode() == KeyEvent.VK_RIGHT){ //ȭ��ǥ ������ Ű�� ������ ���
			if(x<580){ //x��ǥ�� 580���� ���� ���
				HEAD = HEAD_RIGHT; //���ּ��� �������� �ٶ󺻴�.
				dx = SPEED; //x ��ǥ���� ����
			} else {
				dx = 0;
				x = 30;			//������ �׵θ��� ����� ���� ���� ���ּ��� ��Ÿ������ �Ѵ�.
			}
		}
		if(event.getKeyCode() == KeyEvent.VK_UP){ //ȭ��ǥ �� Ű�� ������ ���
			if(y>30){ //y��ǥ�� 30 �̻��� ���
				HEAD = HEAD_UP; //���ּ��� ���� �ٶ󺸰� �Ѵ�.
				dy = -SPEED; //y��ǥ���� ����
			} else {
				dy = 0;			//���� �׵θ��� ����� �Ʒ��� ���� ���ּ��� ��Ÿ������ �Ѵ�.
				y = 550;
			}

		}
		if(event.getKeyCode() == KeyEvent.VK_DOWN){ //ȭ��ǥ �Ʒ�Ű�� ������ ���
			if(y<550){
				HEAD = HEAD_DOWN; //���ּ��� �Ʒ��� �ٶ󺸰� �Ѵ�.
				dy = SPEED;; //y��ǥ���� ����
			} else {
				dy = 0;
				y = 0;			//�Ʒ��� �׵θ��� ����� ���� ���� ���ּ��� ��Ÿ������ �Ѵ�.
			}
		} 
	}

	public void keyReleased(KeyEvent e) { //Ű���忡�� ���� ���� �� ���߰� �Ѵ�.
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
			dx = 0;
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
			dy = 0;
	}
	
	@Override
	public void draw(Graphics g){

		String dir = "";		// ���ڿ� �ʱ�ȭ

		if(HEAD == HEAD_LEFT){ //�Ӹ��� ������ �ٶ󺸴� ����� �̹��� ���ҽ� ����
			dir = "res/p_left.png";
		}
		else if(HEAD == HEAD_RIGHT){ //�Ӹ��� �������� �ٶ󺸴� ����� �̹��� ���ҽ� ����
			dir = "res/p_right.png";
		}
		else if(HEAD == HEAD_UP){ //�Ӹ��� ���� ���ϴ� ����� �̹��� ���ҽ� ����
			dir = "res/p_up.png";
		}
		else if(HEAD == HEAD_DOWN){ //�Ӹ��� �Ʒ��� ���ϴ� ����� �̹��� ���ҽ� ����
			dir = "res/p_down.png";
		}
		try{ //���� ó��
			img = ImageIO.read(new File(dir));		//�̹��� ���ҽ� �ε�
		}catch (IOException e) { //�̹����� ���� ���
			System.out.println("no image");
			System.exit(1);
		}
		
		g.drawImage(img, x, y, WIDTH, HEIGHT, null); //�ش� ��ġ �����

		if(RELOADING){ //�Ѿ��� ���� ���� ��� RELOADING!!!�̶�� �۾��� ����.
			g.setColor(color.BLUE);
			g.setFont(font);
			g.drawString("RELOADING!!!!!",  655, 255);
		} 
	}

	public Rectangle getBounds(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}


}
