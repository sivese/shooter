
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends GraphicObj{ //GraphicObj �� ��� �޾Ƽ� ���� Ŭ������ �����.
	public static final int SPEED = 1; //�ӵ�= 1
	
	Ship ship;
	int width, height; //���� ũ�� ����
	BufferedImage img = null; //�̹��� ����

	public Enemy(int x, int y, int width, int height, Ship ship){ //Enemy Ŭ���� ������
		this.width = width;
		this.height = height;
		this.x = x; //���� x��ǥ ��ġ ��
		this.y = y; //���� y��ǥ ��ġ ��
		this.ship = ship; //���ּ��� ���� ������ �޾ƿ´�.
	}
	
	@Override
	public void update(){ //�� Enemy�� ��ġ�� �����ϱ� ���� �Լ�. �������̵� ���
		super.update();
		if(ship.x != x){ //���ּ��� x ��ǥ�� ���� x��ǥ�� �ٸ� ��
			if(ship.x<x)
				x-=SPEED;       //���� ���ּ��� x���� �����Ѵ�.
			else
				x+=SPEED; 
		}
		if(ship.y!=y){   //���ּ��� y ��ǥ�� ���� y��ǥ�� �ٸ� ��
			if(ship.y>y)
				y+=SPEED;     //���� ���ּ��� y����  �����Ѵ�.
			else
				y-=SPEED;
		}
		
		
	}
	
	public boolean collision(){  //���ּ����� �浹�ÿ� �Լ�
		 return ship.getBounds().intersects(getBounds()); //�浹 ���� ��ȯ
	}
	@Override
	public void draw(Graphics g){ //���� �׷��ش�.
		try{         //���� ó��
			   img = ImageIO.read(new File("res/target.png"));		//�̹��� ���ҽ� �ε�
			  }catch (IOException e) { 
			   System.out.println("no image");
			   System.exit(1);
			  }
		g.drawImage(img, x, y, width, height, null); //�ش� ��ġ �����

	}

	
	public Rectangle getBounds(){
	      return new Rectangle(x, y, width, height);
	   }
}
