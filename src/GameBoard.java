import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameBoard extends JPanel implements KeyListener{ 
	ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //제네릭 클래스를 사용(ArrayList가 컬렉션 중 하나 )
	ArrayList<Missile> missiles = new ArrayList<Missile>(); //제네릭 클래스를 사용(ArrayList가 컬렉션 중 하나) 
	Enemy enemy;
	Ship ship;
	Missile missile;
	ScoreBoard scoreBoard;
	Color color;

	JFrame f;
	JTextField text;
	String name;
	JLabel label;
	JPanel information; //랭킹 입력 창
	JButton button;

	Image img;

	boolean on = true; //게임 실행 제어를 위해서



	public GameBoard(){  //생성자 

		f = new JFrame(); //프레임에 패널을 추가하고 설정
		information=new JPanel();
		button=new JButton("입력");
		text=new JTextField(10);
		label=new JLabel("이름을 입력하시오.");
		button.addActionListener(new ActionListener() { //무명 클래스
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == button)
					saveScore(); //버튼 클릭시 점수 저장
			}
		});

		text.addActionListener(new ActionListener() { //무명클래스
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == text)
					saveScore(); //엔터 입력시 점수저장
			}
		});
		information.add(label);
		information.add(text);
		information.add(button);

		f.setTitle("My Game");
		f.add(this);
		f.setSize(850, 650);
		f.setResizable(false); //크기 조정 불가능 하게 하기 위해서
		f.setVisible(true);
		this.addKeyListener(this);
		this.requestFocus();
		this.setFocusable(true); //키보드의 포커스를 주기 위해서

		try{
			img = ImageIO.read(new File("res/Woodland.jpg"));	//이미지 리소스 로딩
		}catch (IOException e) {
			System.out.println("no image");
			System.exit(1);
		}



		ship = new Ship(); //ship객체 생성
		scoreBoard = new ScoreBoard(ship); //scoreBorad 객체생성
		scoreBoard.resetGame(); //점수랑 시간 초기화

		for(int i=0;i<6;i++){
			makeEnemy();	// 초기 적 6개 생성
		}

		(new Thread(new MyThread())).start(); //기본 스레드를 시작한다.
		(new Thread(new createEnemy())).start(); //적 소환 스레드를 시작한다.

	}//생성자 종료

	// 미사일을 만들어 missiles에 추가한다
	public void makeMissile() {	
		missiles.add(new Missile(this, ship));
	}
	// 적을 랜덤한 위치에 만들어서 enemies에 추가한다

	public void makeEnemy() {	
		int ex, ey; //임의의 좌표
		do {
			ex = (int) (Math.random() * 580); //x의 임의 좌표
			ey = (int) (Math.random() * 500); //y의 임의 좌표
		}while(!((ex > 520 || ex < 20) && (ey > 450 || ey < 20))); //적당한 위치에 적이 생성되도록

		enemies.add(new Enemy(ex, ey, 50, 50, ship));
	}
	//한 쓰레드가 paint 메소드를 사용하면 동기화 해준다
	
	
	public synchronized void paint(Graphics g){  
		super.paint(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this); //이미지 출력
		setOpaque(false); //투명도 비활성화

		scoreBoard.draw(g); //스코어 보드를 그린다.
		ship.draw(g); //우주선을 그린다.

		try {
			for(Missile m : missiles){ //소환된 미사일 개수만큼 미사일을 그린다.
				m.draw(g);
			}
			for(Enemy e : enemies) { //소환된 적만큼 적을 그린다.
				e.draw(g);
			}
		} catch (Exception e) {

		}


		if(ship.CRASH){ //우주선이 적과 부딪힐 경우
			scoreBoard.life --; //목숨 개수 감소
			ship.CRASH = false; //다시 게임하기 위해서 false로 설정
			ship.x = 300; //죽었을 때 재소환 위치 설정
			ship.y = 300;

			if(scoreBoard.life == 0){ //목숨의 개수가 0개일 떄
				g.setColor(color.RED);
				g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,70)); // 빨간색 글씨로 GAME OVER를 나타낸다.
				g.drawString("GAME OVER", 100, 200);
				on = false; //실행 상태 정지
				try {
					this.finalize(); //현재 스레드 종료
				} catch (Throwable e) { //스레드 종료시 에러 검출
					e.printStackTrace();
				}
			}
		}
		if(scoreBoard.VICTORY){ //60초가 지나서 게임에서 이기면 VICTORY 글씨를 나타낸다.
			g.setColor(color.CYAN);
			g.drawString("VICTORY", 270, 300);
		}

	}
	
	//적 소환 스레드
	public class createEnemy implements Runnable { //Runnable 구현
		public void run() {
			while(on) { //게임 실행 상태일 때
				try {
					Thread.sleep(500); //0.5초마다 스레드 실행
					if(enemies.size() < 3 + (6 - (ScoreBoard.TIME / 10))) //10초가  지날 수록 적이 더 많이 생기게 한다. 
						makeEnemy(); 
					scoreBoard.LEVEL = 6 -scoreBoard.TIME / 10;	//시간에 따라 레벨 상승
					repaint(); //객체를 생성하고 바로 그려준다.
				} catch(InterruptedException e) {
				}
			}
			Score(); //점수 입력칸 불러오기
		}
	}

	//게임 진행 스레드

	public class MyThread implements Runnable{ //Runnable 구현
		public void run(){
			while(on){ //게임이 진행중일때 
				ship.update(); //배의 위치 받아옴
				int ms, es;  //ms 와 es는 객체가 사라지는지 확인하기 위해 설정한 변수

				//미사일과 적의 충돌을 체크하여 충돌시 미사일과, 적 제거
				ms = missiles.size(); //충돌 체크전 미사일 개수
				es = enemies.size(); //화면에 나타나 있는  적 개수
				for( int i = 0; i < missiles.size(); i++){ 
					for(int j = 0; j < enemies.size(); j++){
						if(missiles.size() != ms || enemies.size() != es) // 처음 크기와 다르면 for문을 빠져나온다
							break; //remove하면은 요소가 사라지므로 일부로 ms랑 es에 저장하고, 요소가 변경되므로 오류를 피하기 위해서

						missile = missiles.get(i); //몇번째 미사일인지 받아온다. 
						missile.update(); //그 미사일의 위치 갱신
						enemy = enemies.get(j); //확인 중인 적의 정보 받아온다.

						if(missile.collision(enemy)){	// 미사일과 받아온 정보의 적이 충돌하면
							ScoreBoard.SCORE += 50; //점수 50점 증가
							missiles.remove(i);	// 미사일을 제거
							enemies.remove(j); // 적을 제거
						}
					}
					if(missile.x<-10 || missile.x>630 || missile.y<-10 || missile.y>640) {
						missiles.remove(i); // 미사일이 화면 밖으로 나가면 제거한다
					}
				}
				//우주선과 적의 충돌 체크
				for(int i = 0; i < enemies.size(); i++) {
					enemies.get(i).update(); //업데이트 시킨 적의 정보를 가져옴
					if(enemies.get(i).collision()) { // 적과 우주선이 충돌하면
						ship.CRASH = true; //충돌값 true로 준다.
					}
				}

				scoreBoard.update(); // 점수판을 갱신한다

				if(scoreBoard.VICTORY) //승리하면 게임을 종료시킨다.
					on = false;

				repaint();
				try{
					Thread.sleep(10); //0.01초마다 스레드 실행 
				}catch(InterruptedException e){
				}
			}
		}
	}

	//점수 입력창 소환
	public void Score(){

		f.setLayout(null);
		f.add(information);
		information.setBounds(210,240,200,70);
		information.setVisible(true);
		f.setVisible(true);
		text.requestFocus(); //텍스트창에 포커스 준다,
	}
	
	//점수 입력 함수
	public void saveScore(){
		PrintWriter out=null; //입력 버퍼 초기화
		try{
			name = text.getText(); //텍스트 창에 입력한 정보 전달
			out=new PrintWriter(new FileWriter("res/score.txt",true)); //true를 설정함으로써, 정보를 이어서 기록한다.
			out.println(name); //이름 입력
			out.println(ScoreBoard.SCORE); //점수 입력
		}catch(IOException e){}
		finally{
			if(out!=null)
				out.close(); //파일 닫기
		}
		f.dispose(); //객체를 생성하면 창이 열리니까, 기존의 창을 숨김
		new MainMenu(); // 점수 등록 후 메인 화면으로 돌아감.
	}
 
      //키 지정
	
	public void keyPressed(KeyEvent e) {
		if(on) { //게임이 실행 중일 때
			ship.keyPressed(e); 
			if(ship.Present_Bullet<6 && e.getKeyCode() == KeyEvent.VK_SPACE){ //총알이 6발이 넘을 시 실행 불가
                                                                              //스페이스 바 눌렀을 시 실행
				ship.Present_Bullet++; //총알 발사 수 증가
				makeMissile();	// 미사일을 만든다
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				ship.Present_Bullet = 0;	// R키를 누르면 1초 뒤에 총알을 장전한다
				ship.RELOADING = true; //다시 재장전 할 수 있게 만들어 준다.
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		ship.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}


