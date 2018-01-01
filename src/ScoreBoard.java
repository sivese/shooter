import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ScoreBoard extends GraphicObj{ //GraphicObject�� ��� �޾Ƽ� ���ھ�带 ��Ÿ���� Ŭ������ ����
	 //JPanel�� ����� ���ʿ�-->GameBoard���� �׸��� ������
	
	Color color;
	Font font;
	static int SCORE = 0; //ó�� ���� 0�� ����
	static int TIME = 60; //ó�� �ð� 60�� ����
	static int LEVEL = 1;
	int counter = 0;
	public static final int ONE_SEC = 100; //1���� ������ ��������
	boolean VICTORY = false; //ó������ �¸����� �ʾ����� ����
	public int life = 3; //��� ->3��
	Ship ship; //���� �Ѿ��� ������ �������� ���ؼ�
	

	public void resetGame(){ //������ ������ �ٽ� ������ �����Ҷ� �ð��� ������ �ʱ�ȭ�Ѵ�.
		TIME = 60;
		SCORE = 0;
	}
	
	public ScoreBoard(Ship ship){ //ScoreBoard ������
		this.ship = ship;
		font = new Font("Italic",font.BOLD,17);
	}
	
	@Override
	
	public void update(){  //�ð��� ���������� �ð� 1�ʾ� ����
		if(counter == ONE_SEC){  
			TIME = TIME - 1;
			counter = 1;  //counter�ϳ��� ��ǻ� 10ms 
		}
		else
			counter++;
		
		if(TIME == 0 && life > 0) //�ð��� 0�̵Ǹ� �¸��ϰ� ����
			VICTORY = true;
		
	}
	
	public void draw(Graphics g){ //���ھ� ���� �׸���

		g.setColor(color.GREEN.darker());
		g.drawRect(30, 30, 580, 580);	//���� ���� ��輱
		
		g.setColor(color.GREEN.darker());
		g.fillRect(640, 7, 185, 590); //������ Ʋ
		
		g.setColor(color.RED);
		g.setFont(font);
		g.drawString("SCORE", 655, 35); //���� ����
		
		g.setColor(color.WHITE);
		g.setFont(new Font("Plain",font.PLAIN,15));
		g.drawString(" "+SCORE, 655, 55);//���� ����
		
		g.setFont(font);
		g.setColor(color.LIGHT_GRAY);
		g.drawString("TIME", 655, 135); //�ð� ����
		
		g.setColor(color.WHITE);
		g.setFont(new Font("Plain",font.PLAIN,15));
		g.drawString(" "+TIME, 655, 155); //���� �ð�
		
		g.setColor(color.DARK_GRAY);
		g.setFont(font);
		g.drawString("Bullets", 655, 235);//SHOT GUN źȯ ����
		
		g.setColor(color.RED);
		g.setFont(font);
		g.drawString("LIFE", 655, 335);//LIFE ����
		for(int i=0; i <life ; i++){
			g.fillRoundRect(655 + i*40, 355, 10, 10, 10, 10);
		}
		
		g.setColor(color.BLUE);
		g.setFont(font);
		g.drawString("LEVEL",  655,  435);
		g.drawString(Integer.toString(LEVEL), 655, 470); //level�� ���ڿ��̹Ƿ� ���ڿ��� �ٲ㼭 �����
		
		g.setColor(color.RED); //����� źȯ
		for(int i=0;i<ship.Present_Bullet;i++){
			g.fillRect(655+i*15, 255, 7, 18);
		}
		
		g.setColor(color.YELLOW);//���� źȯ
		for(int i=0;i<6-ship.Present_Bullet;i++){
			g.fillRect(655+(ship.Present_Bullet+i)*15, 255, 7, 18);
		}
		
		
		
	}
}
