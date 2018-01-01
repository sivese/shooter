import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements KeyListener{ //처음 메인 메뉴 JPanel을 상속받고, KeyListener를 구현

	private int currentOption = 1; //처음에 Start를 가리키고 있다.

	JFrame f; 
	Image img; 
	Color color;
	ScoreBoard scoreBoard;
	Ship ship;
	String name;
	JPanel information;

			
	public MainMenu(){
		super();
		f = new JFrame(); //프레임에 패널을 추가하고 설정
		
		f.setTitle("My Game");
		f.add(this);
		f.setSize(850, 650);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false); //크기 조정 X 
		f.setVisible(true);
		this.addKeyListener(this);

		try{ //이미지 예외 처리
			   img = ImageIO.read(new File("res/Woodland.jpg")); //이미지 리소스 로딩
			  }catch (IOException e) {
			   System.out.println("no image");
			   System.exit(1);
			  }

		
		this.requestFocus();
		setFocusable(true);  //키보드 포커스를 요청하고 패널이 포커스를 받을 수 있게 한다.
		super.repaint(); //이미지를 처음에 나타내기 위해서
	}
	
	public void paint(Graphics g){  //메뉴를 그려준다.
		
		super.paint(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this); //이미지를 불러온다.
		
		g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,110));
		g.setColor(color.RED);
		g.drawString("GO_SOILDER", 100, 100); //Go_SOILDER 글씨 나타낸다.
		g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,70));
		g.setColor(color.BLUE);
		g.drawString("START", 300, 400); //START 글씨 나타낸다.
		g.drawString("SCOREBOARD", 300, 470); //SCOREBoard 글씨 나타낸다.
		g.drawString("QUIT", 300, 540); //QUIT 글씨 나타낸다.
		
		
		switch(currentOption){  //메뉴를선택할때 파란글씨에서 노란색 글씨로 나타내기 위한 switch case문
		case 1:
			g.setColor(color.YELLOW);
			g.drawString("START", 300, 400);
			break;
		case 2:			
			g.setColor(color.YELLOW);
			g.drawString("SCOREBOARD", 300, 470);
			break;
		case 3:				
			g.setColor(color.YELLOW);
			g.drawString("QUIT", 300, 540);
			break;
			}
	}
	
	public void keyPressed(KeyEvent event) { //키보드 위 아래 버튼을 이용해서 메뉴 선택
		// TODO Auto-generated method stub
		
		if(event.getKeyCode() == KeyEvent.VK_DOWN){ //아래 버튼을 누를경우
			currentOption++;
			if(currentOption == 4)
				currentOption = 1; //QUIT에서 화살표 아래키를 누르면 START 버튼이 된다.
		repaint();	
		}
		if(event.getKeyCode() == KeyEvent.VK_UP){ //위 버튼을 누를경우
				currentOption--;
				if(currentOption == 0)
					currentOption = 3;  //START에서 화살표 위버튼을 누르면 QUIT 버튼이 된다.
				repaint();}
		if((event.getKeyCode() == KeyEvent.VK_SPACE)) { //스페이스 바를 눌러 메뉴를 선택할 경우
			selectOption(); //해당 메뉴를 선택
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void selectOption() { //메뉴 선택
		if(currentOption == 1) { //START 버튼을 선택했을 경우
			new GameBoard(); //GameBoard 객체 생성
			f.dispose(); //객체를 생성하면 창이 열리니까, 기존의 창을 숨김
		}

		if(currentOption == 2) {//SCOREBOARD 버튼을 선택했을 경우
			new Ranking(); //랭킹 객체 생성
			f.dispose(); //객체를 생성하면 창이 열리니까, 기존의 창을 숨김
		}
		
		if(currentOption == 3) { //QUIT 버튼을 선택했을 경우
			System.exit(0); //종료
		}
	}
	
	public static void main(String[] args) { //메인 함수
		new MainMenu();
	}

}
