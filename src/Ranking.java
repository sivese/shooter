import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.print.attribute.IntegerSyntax;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ranking extends JPanel implements KeyListener{

	JFrame f;
	Image img; //이미지를 불러오기 위해서
	Image img2;
	Color color;
	String name;
	Scanner input; //파일을 여는 객체
	String names[] = new String[100]; //랭킹에서 이름 저장 배열
	int scores[] = new int[100]; //랭킹에서 점수 저장 배열
	String string_temp; //점수 임시 범퍼
	int scorecount = 0; //사람 수 세기위해서.. 처음에 0으로 초기화
	int temp; //점수 임시버퍼

	public Ranking(){
		super();

		f = new JFrame(); //프레임에 패널을 추가하고 설정

		f.setTitle("My Game");
		f.add(this);
		f.setSize(850, 650);
		f.setResizable(false);
		f.setVisible(true);
		this.addKeyListener(this);



		try{
			img = ImageIO.read(new File("res/Woodland.jpg"));		//이미지 리소스 로딩
		}catch (IOException e) {
			System.out.println("no image");
			System.exit(1);
		}
		try{
			img2 = ImageIO.read(new File("res/Ranking.png"));		//이미지 리소스 로딩
		}catch (IOException e) {
			System.out.println("no image");
			System.exit(1);
		}

		this.requestFocus(); //키보드 포커스를 요청하고 패널이 포커스를 받을 수 있게 한다.
		setFocusable(true);
		super.repaint(); //이미지를 처음에 로드한 다음에 그려주기 위해서

		try {
			input = new Scanner(Paths.get("res/score.txt"));//SCANNER객체를 이용하여 파일을 가져옴. 파일의 경로는 Paths클래스를 이용하여 지정
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			while(input.hasNext()){ 
				names[scorecount] = input.nextLine();//이름의 한줄을 읽는다.
				string_temp = input.nextLine(); //점수를 한줄 읽는다.
				scores[scorecount] = Integer.parseInt(string_temp); //점수가 문자열이여서 정수형으로 변환
				scorecount++;
			}
		}catch(Exception e){
			e.toString();
		}

		//점수 정렬(버블정렬)
		for(int j=0; j<scorecount;j++){

			for(int i=0; i<scorecount-j; i++){

				if(scores[i] < scores[i+1]){
					temp = scores[i];
					string_temp = names[i];
					scores[i] = scores[i+1];
					names[i] = names[i+1];
					scores[i+1] = temp;
					names[i+1] = string_temp;
				}

			}
		}

	}

	@Override	
	public void paint(Graphics g){  //한 쓰레드가 paint 메소드를 사용하면 동기화 해준다.

		super.paint(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this); //가져온 이미지 그려줌.
		g.setColor(color.BLACK);
		g.fillRect(30, 30, 400, 580);			

		g.drawImage(img2, 300, 0, 700, 913, this); //두번째 가져온 이미지 그려줌.

		//점수 불러온 것을 화면에 출력
		for(int i=0; i<scorecount; i++){
			g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,60));
			g.setColor(color.RED);
			g.drawString(String.valueOf(i+1), 100, 100*(i+1)); //숫자를 문자열로 바꿔서 그려준다. 

			g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,40));
			g.setColor(color.YELLOW);
			g.drawString(names[i], 150, 100*(i+1)); //이름을 출력한다.
			
			g.setColor(color.GREEN.darker());
			g.drawString(String.valueOf(scores[i]), 300, 100*(i+1)); //숫자를 출력한다.
			
			repaint();
		}

		g.setFont(new Font("Serif",Font.BOLD|Font.ITALIC,50)); 
		g.setColor(color.YELLOW);
		g.drawString("Press SPACE", 500, 540); //안내문 출력
		g.drawString("to get BACK", 500, 600);

	}


	public void keyPressed(KeyEvent event) { 
		// TODO Auto-generated method stub
		if((event.getKeyCode() == KeyEvent.VK_SPACE)) { //스페이스 바를 누르면 메인 메뉴로 돌아간다.
			new MainMenu(); //객체 재생성
			f.dispose(); //객체를 생성하면 창이 열리니까, 기존의 창을 숨김.
		}
	}


	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
