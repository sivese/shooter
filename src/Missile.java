import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile extends GraphicObj{ //GraphicsObj�� ��� �޾Ƽ� �̻����� ��Ÿ���� Ŭ������ �����Ѵ�.
	
	GameBoard game; //���� ȭ�鿡 ��Ÿ���ֱ� ���ؼ�
	Ship ship;  //���ּ�
	Enemy enemy; //���̶� �浹�˻縦 ���ؼ�
	int HEAD; //���ּ��� �ٶ󺸴� ����
	
	public Missile(GameBoard game, Ship ship){ //�̻��� ������
		this.game = game;
		this.ship = ship;
		x = ship.x; //���ּ��� x��ǥ �޾ƿ´�.
		y = ship.y; //���ּ��� y��ǥ �޾ƿ´�.
		HEAD = ship.HEAD; //���ּ��� �ٶ󺸴� ���� ���޾ƿ´�.
	}
	
	@Override
	public void update(){
		if(HEAD == ship.HEAD_LEFT) //���ּ��̿����� �ٶ� �� �̻����� x��ǥ ��ȭ
			x -= 4;
		else if(HEAD == ship.HEAD_RIGHT) //���ּ��� �������� �ٶ󺼋� �̻����� x��ǥ ��ȭ
			x += 4;
		else if(HEAD == ship.HEAD_UP) //���ּ��� ������ �ٶ󺼋� �̻����� y��ǥ ��ȭ
			y -= 4;
		else if(HEAD == ship.HEAD_DOWN)//���ּ��� �Ʒ����� �ٶ󺼋� �̻����� y��ǥ ��ȭ
			y += 4;
	}
	
	public boolean collision(Enemy e){ //�̻��ϰ� ���� �浹�ÿ�
	      return e.getBounds().intersects(getBounds()); //������ �浹 ���� �˻�
	  }
	@Override
	public void draw(Graphics g){ //�̻����� �׸���.
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, 10, 10);
	}

	public Rectangle getBounds(){
		return new Rectangle(x, y, 10, 10);
	}
}
