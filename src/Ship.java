import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship extends GraphicObj{ //GraphicObj 를 상속 받아서 배를 나타내는 클래스를 정의한다.
	Color color;

	public static final int WIDTH = 30;  // 우주선의 크기
	public static final int HEIGHT = 30; // 우주선의 높이
	public static final int HEAD_LEFT = 1; //우주선의 방향
	public static final int HEAD_RIGHT = 2; //우주선의 방향
	public static final int HEAD_UP = 3; //우주선의 방향
	public static final int HEAD_DOWN = 4; //우주선의 방향
	public static final int CYCLE = 100; //장전하는데 1초가 걸리게 설정
	public static final int SPEED = 3; //배의 속도
	
	BufferedImage img = null;		//이미지 버퍼
	Font font;

	boolean CRASH = false; //우주선이 적이랑 부딪혔을떄
	boolean RELOADING = false; // 장전여부, 초기에는 false를 줘서 장전중이지 않음을 의미
	
	int load_counter = 0; //현재 장전 사이클 카운터 세기위해서
	int HEAD = 1; //배 현재 머리 위치, 어디를 바라보고 있는지
	int Present_Bullet = 0; //현재 사용한 총알 수(0에서 시작해서 6까지)
	int dx, dy; //이동속도

	public Ship(){ //우주선 생성자    
		x = 310;       //우주선의 초기 위치 설정
		y = 320;
		font = new Font("Italic",font.BOLD,17);
	}

	@Override
	public void update(){
		if(RELOADING && load_counter != CYCLE){  //재장전 중이고 장전 시간인 1초가 아직 지나지 않았을 때
			load_counter++; 
			Present_Bullet = 6; //총알을 다 써서 더이상 못쏘게

		}
		else if(RELOADING && load_counter == CYCLE){ //재장전이 끝났을 경우
			RELOADING = false; //다음 재장전을 가능하게 함.
			Present_Bullet = 0; //사용한 총알 수 초기화
			load_counter = 0; //재장전 시간 0으로.
		}
		x += dx; //우주선의 좌표를 바꿔준다.
		y += dy;
	}

	public void keyPressed(KeyEvent event){ 
		if(event.getKeyCode() == KeyEvent.VK_LEFT){ //화살표 왼쪽 방향키를 눌렀을 경우
			if(x>30){ //x좌표가 30이상일 경우
				HEAD = HEAD_LEFT; //우주선이 왼쪽을 바라본다.
				dx = -SPEED; //x 좌표값을 변경
			} else {
				dx = 0;		//왼쪽 테두리를 벗어나면 오른쪽 끝에 우주선이 나타나도록 한다.
				x = 580;
			}
		}
		if(event.getKeyCode() == KeyEvent.VK_RIGHT){ //화살표 오른쪽 키를 눌렀을 경우
			if(x<580){ //x좌표가 580보다 작을 경우
				HEAD = HEAD_RIGHT; //우주선이 오른쪽을 바라본다.
				dx = SPEED; //x 좌표값을 변경
			} else {
				dx = 0;
				x = 30;			//오른쪽 테두리를 벗어나면 왼쪽 끝에 우주선이 나타나도록 한다.
			}
		}
		if(event.getKeyCode() == KeyEvent.VK_UP){ //화살표 위 키를 눌렀을 경우
			if(y>30){ //y좌표가 30 이상일 경우
				HEAD = HEAD_UP; //우주선이 위를 바라보게 한다.
				dy = -SPEED; //y좌표값을 변경
			} else {
				dy = 0;			//위쪽 테두리를 벗어나면 아래쪽 끝에 우주선이 나타나도록 한다.
				y = 550;
			}

		}
		if(event.getKeyCode() == KeyEvent.VK_DOWN){ //화살표 아래키를 눌렀을 경우
			if(y<550){
				HEAD = HEAD_DOWN; //우주선이 아래를 바라보게 한다.
				dy = SPEED;; //y좌표값을 변경
			} else {
				dy = 0;
				y = 0;			//아래쪽 테두리를 벗어나면 위쪽 끝에 우주선이 나타나도록 한다.
			}
		} 
	}

	public void keyReleased(KeyEvent e) { //키보드에서 손을 뗏을 때 멈추게 한다.
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
			dx = 0;
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
			dy = 0;
	}
	
	@Override
	public void draw(Graphics g){

		String dir = "";		// 문자열 초기화

		if(HEAD == HEAD_LEFT){ //머리가 왼쪽을 바라보는 경우의 이미지 리소스 설정
			dir = "res/p_left.png";
		}
		else if(HEAD == HEAD_RIGHT){ //머리가 오른쪽을 바라보는 경우의 이미지 리소스 설정
			dir = "res/p_right.png";
		}
		else if(HEAD == HEAD_UP){ //머리가 위로 향하는 경우의 이미지 리소스 설정
			dir = "res/p_up.png";
		}
		else if(HEAD == HEAD_DOWN){ //머리가 아래로 향하는 경우의 이미지 리소스 설정
			dir = "res/p_down.png";
		}
		try{ //예외 처리
			img = ImageIO.read(new File(dir));		//이미지 리소스 로딩
		}catch (IOException e) { //이미지가 없을 경우
			System.out.println("no image");
			System.exit(1);
		}
		
		g.drawImage(img, x, y, WIDTH, HEIGHT, null); //해당 위치 드로잉

		if(RELOADING){ //총알을 장전 중일 경우 RELOADING!!!이라는 글씨를 띄운다.
			g.setColor(color.BLUE);
			g.setFont(font);
			g.drawString("RELOADING!!!!!",  655, 255);
		} 
	}

	public Rectangle getBounds(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}


}
