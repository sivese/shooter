
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends GraphicObj{ //GraphicObj 를 상속 받아서 적군 클래스를 만든다.
	public static final int SPEED = 1; //속도= 1
	
	Ship ship;
	int width, height; //적의 크기 지정
	BufferedImage img = null; //이미지 버퍼

	public Enemy(int x, int y, int width, int height, Ship ship){ //Enemy 클래스 생성자
		this.width = width;
		this.height = height;
		this.x = x; //적의 x좌표 위치 값
		this.y = y; //적의 y좌표 위치 값
		this.ship = ship; //우주선에 대한 정보를 받아온다.
	}
	
	@Override
	public void update(){ //적 Enemy의 위치를 변경하기 위한 함수. 오버라이딩 사용
		super.update();
		if(ship.x != x){ //우주선의 x 좌표가 적의 x좌표와 다를 때
			if(ship.x<x)
				x-=SPEED;       //적이 우주선의 x축을 추적한다.
			else
				x+=SPEED; 
		}
		if(ship.y!=y){   //우주선의 y 좌표가 적의 y좌표와 다를 때
			if(ship.y>y)
				y+=SPEED;     //적이 우주선의 y축을  추적한다.
			else
				y-=SPEED;
		}
		
		
	}
	
	public boolean collision(){  //우주선과의 충돌시에 함수
		 return ship.getBounds().intersects(getBounds()); //충돌 여부 반환
	}
	@Override
	public void draw(Graphics g){ //적을 그려준다.
		try{         //예외 처리
			   img = ImageIO.read(new File("res/target.png"));		//이미지 리소스 로딩
			  }catch (IOException e) { 
			   System.out.println("no image");
			   System.exit(1);
			  }
		g.drawImage(img, x, y, width, height, null); //해당 위치 드로잉

	}

	
	public Rectangle getBounds(){
	      return new Rectangle(x, y, width, height);
	   }
}
