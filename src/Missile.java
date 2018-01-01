import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile extends GraphicObj{ //GraphicsObj를 상속 받아서 미사일을 나타내는 클래스를 정의한다.
	
	GameBoard game; //게임 화면에 나타내주기 위해서
	Ship ship;  //우주선
	Enemy enemy; //적이랑 충돌검사를 위해서
	int HEAD; //우주선이 바라보는 방향
	
	public Missile(GameBoard game, Ship ship){ //미사일 생성자
		this.game = game;
		this.ship = ship;
		x = ship.x; //우주선의 x좌표 받아온다.
		y = ship.y; //우주선의 y좌표 받아온다.
		HEAD = ship.HEAD; //우주선이 바라보는 방향 을받아온다.
	}
	
	@Override
	public void update(){
		if(HEAD == ship.HEAD_LEFT) //우주선이왼쪽을 바라볼 때 미사일의 x좌표 변화
			x -= 4;
		else if(HEAD == ship.HEAD_RIGHT) //우주선이 오른쪽을 바라볼떄 미사일의 x좌표 변화
			x += 4;
		else if(HEAD == ship.HEAD_UP) //우주선이 위쪽을 바라볼떄 미사일의 y좌표 변화
			y -= 4;
		else if(HEAD == ship.HEAD_DOWN)//우주선이 아래쪽을 바라볼떄 미사일의 y좌표 변화
			y += 4;
	}
	
	public boolean collision(Enemy e){ //미사일과 적이 충돌시에
	      return e.getBounds().intersects(getBounds()); //적과의 충돌 여부 검사
	  }
	@Override
	public void draw(Graphics g){ //미사일을 그린다.
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, 10, 10);
	}

	public Rectangle getBounds(){
		return new Rectangle(x, y, 10, 10);
	}
}
