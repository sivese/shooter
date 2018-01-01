import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ScoreBoard extends GraphicObj{ //GraphicObject를 상속 받아서 스코어보드를 나타내는 클래스를 정의
	 //JPanel의 상속이 불필요-->GameBoard위에 그리기 때문에
	
	Color color;
	Font font;
	static int SCORE = 0; //처음 점수 0점 설정
	static int TIME = 60; //처음 시간 60초 설정
	static int LEVEL = 1;
	int counter = 0;
	public static final int ONE_SEC = 100; //1초의 기준을 지정해줌
	boolean VICTORY = false; //처음에는 승리하지 않았음을 설정
	public int life = 3; //목숨 ->3개
	Ship ship; //현재 총알의 개수를 가져오기 위해서
	

	public void resetGame(){ //게임이 끝나고 다시 게임을 시작할때 시간과 점수를 초기화한다.
		TIME = 60;
		SCORE = 0;
	}
	
	public ScoreBoard(Ship ship){ //ScoreBoard 생성자
		this.ship = ship;
		font = new Font("Italic",font.BOLD,17);
	}
	
	@Override
	
	public void update(){  //시간초 지날때마다 시간 1초씩 감소
		if(counter == ONE_SEC){  
			TIME = TIME - 1;
			counter = 1;  //counter하나당 사실상 10ms 
		}
		else
			counter++;
		
		if(TIME == 0 && life > 0) //시간이 0이되면 승리하게 설정
			VICTORY = true;
		
	}
	
	public void draw(Graphics g){ //스코어 보드 그리기

		g.setColor(color.GREEN.darker());
		g.drawRect(30, 30, 580, 580);	//게임 보드 경계선
		
		g.setColor(color.GREEN.darker());
		g.fillRect(640, 7, 185, 590); //점수판 틀
		
		g.setColor(color.RED);
		g.setFont(font);
		g.drawString("SCORE", 655, 35); //점수 쓰기
		
		g.setColor(color.WHITE);
		g.setFont(new Font("Plain",font.PLAIN,15));
		g.drawString(" "+SCORE, 655, 55);//현재 점수
		
		g.setFont(font);
		g.setColor(color.LIGHT_GRAY);
		g.drawString("TIME", 655, 135); //시간 쓰기
		
		g.setColor(color.WHITE);
		g.setFont(new Font("Plain",font.PLAIN,15));
		g.drawString(" "+TIME, 655, 155); //남은 시간
		
		g.setColor(color.DARK_GRAY);
		g.setFont(font);
		g.drawString("Bullets", 655, 235);//SHOT GUN 탄환 정보
		
		g.setColor(color.RED);
		g.setFont(font);
		g.drawString("LIFE", 655, 335);//LIFE 정보
		for(int i=0; i <life ; i++){
			g.fillRoundRect(655 + i*40, 355, 10, 10, 10, 10);
		}
		
		g.setColor(color.BLUE);
		g.setFont(font);
		g.drawString("LEVEL",  655,  435);
		g.drawString(Integer.toString(LEVEL), 655, 470); //level은 숫자열이므로 문자열로 바꿔서 출력함
		
		g.setColor(color.RED); //사용한 탄환
		for(int i=0;i<ship.Present_Bullet;i++){
			g.fillRect(655+i*15, 255, 7, 18);
		}
		
		g.setColor(color.YELLOW);//남은 탄환
		for(int i=0;i<6-ship.Present_Bullet;i++){
			g.fillRect(655+(ship.Present_Bullet+i)*15, 255, 7, 18);
		}
		
		
		
	}
}
