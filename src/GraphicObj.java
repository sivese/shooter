import java.awt.Graphics;


public abstract class GraphicObj { //추상클래스를 사용해서 상속시켜주려고 GraphicObj 클래스를 만들었다.
	int x = 0, y = 0;
	
	public void update(){  //위치 변경, 추상메소드
		
	}
	
	boolean collision(){	//우주선과 적이 충돌 했을 경우
		return false;
	}
	
	public void draw(Graphics g) { //화면에 그리는데 사용, 추상메소드
		
		
	}

	
}




