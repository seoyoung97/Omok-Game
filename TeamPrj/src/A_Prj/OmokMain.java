package A_Prj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

//220, 179, 92 오목판 색상

@SuppressWarnings("serial")
public class OmokMain extends JFrame {
	MyThread t = new MyThread();
	Omok omok[][];
	ImageIcon img = new ImageIcon("img/main_g.png"); // 오목판 이미지
	ImageIcon logo = new ImageIcon("img/main_bg.png");
	ImageIcon player1; // 1번 플레이어
	ImageIcon player2; // 2번 플레이어
	ImageIcon player3; // 3번 플레이어
	ImageIcon beforeTurn = null; // 이전 차례
	JPanel pnNorth = new JPanel();
	JLabel lbMin = new JLabel("0");
	JLabel lb = new JLabel(":");
	JLabel lbSec = new JLabel("0");
	JLabel lb2 = new JLabel("        ");
	String turnTime = "30";
	JLabel lbIn30 = new JLabel(turnTime);
	// MyThread30 in30Thread = new MyThread30();
	int trickCnt = 10;
	boolean endChk = true;

	public OmokMain(ArrayList<JToggleButton> al) {
		// 선택한 순서대로 플레이어에 할당
		player1 = (ImageIcon) al.get(0).getIcon();
		player2 = (ImageIcon) al.get(1).getIcon();
		player3 = (ImageIcon) al.get(2).getIcon();

		setTitle("과일 오목게임");
		setBounds(0, 0, 800, 790);
		Container c = getContentPane();
		c.setLayout(null);
		pnNorth.add(lbMin);
		pnNorth.add(lb);
		pnNorth.add(lbSec);
		pnNorth.add(lb2);
		// pnNorth.add(lbIn30);
		pnNorth.setBounds(650, 400, 150, 100);

		pnNorth.setBackground(new Color(193, 213, 179)); // 시간 중간
		c.add(pnNorth);

		/*
		 * JLabel logoIcon = new JLabel(); logoIcon.setBounds(0, 0, 150, 740); //1
		 * logoIcon.setIcon(logo); pnNorth.add(logoIcon); c.add(pnNorth);
		 */

		// 시간
		JPanel pnLogo = new JPanel();
		pnLogo.setBounds(650, 0, 150, 400);
		pnLogo.setBackground(new Color(193, 213, 179));
		pnLogo.setLayout(null);

		ImageIcon icon = new ImageIcon("img/main_text2.png");
		JLabel text = new JLabel(icon);
		text.setBounds(640, 0, 150, 400);
		c.add(text);

		JLabel logoIcon2 = new JLabel();
		logoIcon2.setBounds(0, 0, 150, 600);
		logoIcon2.setIcon(logo);
		pnLogo.add(logoIcon2);
		c.add(pnLogo);

		JPanel pnPlayer = new JPanel();
		pnPlayer.setBounds(650, 500, 150, 290);
		// pnPlayer.setBackground(new Color(146, 208, 80));
		pnPlayer.setBackground(new Color(193, 213, 179));

		/*
		 * JLabel logoIcon3 = new JLabel(); logoIcon3.setBounds(0, 0, 150, 600);
		 * logoIcon3.setIcon(logo); pnPlayer.add(logoIcon3); c.add(pnPlayer);
		 */

		pnPlayer.add(lbIn30);
		pnPlayer.setLayout(null);
		lbIn30.setBounds(100, 0, 25, 25);
		JLabel pName;
		JLabel pStone;
		for (int i = 0; i < al.size(); i++) {
			pName = new JLabel();
			pName.setText("Player" + (i + 1));
			pName.setBounds(0, 50 * i, 50, 25);
			pName.setForeground(Color.black);
			pName.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			pStone = new JLabel();
			ImageIcon pIcon = (ImageIcon) al.get(i).getIcon();
			pStone.setIcon(pIcon);
			pStone.setBounds(50, 50 * i, 25, 25);
			pnPlayer.add(pStone);
			pnPlayer.add(pName);
		}
		JButton reMain = new JButton("다시하기");
		// reMain.setForeground(new Color(146, 208, 80));
		reMain.setBackground(new Color(146, 208, 80));
		reMain.setForeground(new Color(255, 255, 255));
		reMain.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		reMain.setBounds(15, 150, 100, 50);
		pnPlayer.add(reMain);

		reMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new OmokSt();
				dispose();
			}
		});
		c.add(pnPlayer);
		JPanel pnGame = new JPanel(new GridLayout(26, 26));
		pnGame.setBounds(0, 0, 650, 780);
		c.add(pnGame);
		omok = new Omok[26][];

		ArrayList<Integer> trickX = new ArrayList<Integer>(); // 함정 x 좌표
		ArrayList<Integer> trickY = new ArrayList<Integer>(); // 함정 y 좌표

		// 함정 x 좌표
		while (!(trickX.size() == trickCnt)) {
			int a = (int) (Math.random() * 25);
			System.out.println("trickX : " + a);
			if (!trickX.contains(a)) {
				trickX.add(a);
			}
		}
		// 함정 y 좌표
		while (!(trickY.size() == trickCnt)) {
			int a = (int) (Math.random() * 25);
			System.out.println("trickY : " + a);
			if (!trickY.contains(a)) {
				trickY.add(a);
			}
		}

		myActionListener goAction = new myActionListener();
		for (int i = 0; i < 26; i++) {
			omok[i] = new Omok[26];
			for (int j = 0; j < 26; j++) {
				img = new ImageIcon("img/main_g.png");
				boolean trick = false;
				for (int x = 0; x < trickCnt; x++) {
					if (i == trickX.get(x) && j == trickY.get(x)) {
						img = new ImageIcon("img/stone.png");
						trick = true;
					}
				}
				omok[i][j] = new Omok(i, j, img);
				pnGame.add(omok[i][j]);
				// 함정이 없는 좌표만 이벤트리스너 추가
				if (!trick) {
					omok[i][j].addActionListener(goAction);
				}
				omok[i][j].setBorderPainted(false);
			}
		}
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		t.start();
		// in30Thread.start();
	}

	class MyThread extends Thread {
		public void run() {
			while (true) {
				try {
					lbIn30.setText(turnTime);
					Thread.sleep(1000);
					int t = Integer.parseInt(turnTime);
					t -= 1;
					turnTime = Integer.toString(t);
					if (t <= 10) {
						lbIn30.setForeground(Color.red);
					}
					if (t == 0) {
						lbIn30.setForeground(Color.black);
						turnTime = "30";
						if (beforeTurn == null || beforeTurn == player3) {
							beforeTurn = player1;
							lbIn30.setLocation(100, 50);
						} else if (beforeTurn == player1) {
							beforeTurn = player2;
							lbIn30.setLocation(100, 100);
						} else {
							beforeTurn = player3;
							lbIn30.setLocation(100, 0);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int totalSec = Integer.parseInt(lbMin.getText()) * 60 + Integer.parseInt(lbSec.getText()) + 1;
				int min = totalSec / 60;
				int sec = totalSec % 60;
				lbMin.setText("" + min);
				lbSec.setText("" + sec);
				lbIn30.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				lbMin.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				lbSec.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				pnNorth.repaint();
			}
		}
	}

	/*
	 * class MyThread30 extends Thread { int sec; public void run() { while (true) {
	 * try { Thread.sleep(1000); } catch (InterruptedException e) {
	 * e.printStackTrace(); } sec +=1; lbIn30.setText(""+sec); if (sec==30) {
	 * if(beforeTurn == null || beforeTurn == player3) { beforeTurn = player1; }else
	 * if(beforeTurn == player1) { beforeTurn = player2; }else { beforeTurn =
	 * player3; } sec = 0; } pnNorth.repaint(); } } }
	 */

	class myActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (endChk) {
				lbIn30.setForeground(Color.black);
				turnTime = "30";
				lbIn30.setText(turnTime);
				Omok wi = (Omok) e.getSource();
				if (beforeTurn == null || beforeTurn == player3) {
					lbIn30.setLocation(100, 50);
					wi.setIcon(player1);
					beforeTurn = player1;
					wi.state = "1";
				} else if (beforeTurn == player1) {
					lbIn30.setLocation(100, 100);
					wi.setIcon(player2);
					beforeTurn = player2;
					wi.state = "2";
				} else {
					lbIn30.setLocation(100, 0);
					wi.setIcon(player3);
					beforeTurn = player3;
					wi.state = "3";
				}
				checkWin(wi);
				wi.removeActionListener(this);
			}
		}
	}

	public void checkWin(Omok e) {
		int ch_x = e.x;
		int ch_y = e.y;
		int count = 0;
		while (ch_y >= 0 && omok[ch_x][ch_y].state.equals(e.state)) {
			ch_y -= 1;
		}
		ch_y += 1;
		while (ch_y < 26 && omok[ch_x][ch_y].state.equals(e.state)) {
			ch_y += 1;
			count++;
		}
		if (count >= 5) {
			endChk = winPrint(e.state);
		}

		ch_x = e.x;
		ch_y = e.y;
		count = 0;

		while (ch_x >= 0 && omok[ch_x][ch_y].state.equals(e.state)) {
			ch_x -= 1;
		}
		ch_x += 1;
		while (ch_x < 26 && omok[ch_x][ch_y].state.equals(e.state)) {
			ch_x += 1;
			count++;
		}
		if (count >= 5) {
			endChk = winPrint(e.state);
		}

		ch_x = e.x;
		ch_y = e.y;
		count = 0;

		while (ch_x >= 0 && ch_y >= 0 && omok[ch_x][ch_y].state.equals(e.state)) {
			ch_x -= 1;
			ch_y -= 1;
		}
		ch_x += 1;
		ch_y += 1;
		while (ch_x < 26 && ch_y < 26 && omok[ch_x][ch_y].state.equals(e.state)) {
			ch_x += 1;
			ch_y += 1;
			count++;
		}
		if (count >= 5) {
			endChk = winPrint(e.state);
		}

		ch_x = e.x;
		ch_y = e.y;
		count = 0;

		while (ch_x >= 0 && ch_y < 26 && omok[ch_x][ch_y].state.equals(e.state)) {
			ch_x -= 1;
			ch_y += 1;
		}
		ch_x += 1;
		ch_y -= 1;
		while (ch_x < 26 && ch_y >= 0 && omok[ch_x][ch_y].state.equals(e.state)) {
			ch_x += 1;
			ch_y -= 1;
			count++;
		}
		if (count >= 5) {
			endChk = winPrint(e.state);
		}
	}

	// 우승 시 창 띄우기
	@SuppressWarnings("deprecation")
	public boolean winPrint(String s) {
		t.stop();
		if (s.equals("1")) {
			JOptionPane.showMessageDialog(null, "Player1이 이겼습니다.", "축하합니다.", JOptionPane.QUESTION_MESSAGE);
		} else if (s.equals("2")) {
			JOptionPane.showMessageDialog(null, "Player2가 이겼습니다.", "축하합니다.", JOptionPane.QUESTION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Player3이 이겼습니다.", "축하합니다.", JOptionPane.QUESTION_MESSAGE);
		}
		return false;
	}

	public static void main(String[] args) {
		// new OmokMain();
	}
}

@SuppressWarnings("serial")
class Omok extends JButton {
	int x;
	int y;
	String state;

	public Omok(int x, int y, ImageIcon image) {
		super(image);
		this.x = x;
		this.y = y;
		state = "N";
		setBackground(new Color(193, 213, 179));
		setBorderPainted(false);
	}
}