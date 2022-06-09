package A_Prj;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class OmokSt extends JFrame {

	ImageIcon img1 = new ImageIcon("img/apple.png");
	ImageIcon img2 = new ImageIcon("img/blueberry.png");
	ImageIcon img3 = new ImageIcon("img/orange.png");
	ImageIcon img4 = new ImageIcon("img/watermelon.png");

	ArrayList<JToggleButton> select = new ArrayList<JToggleButton>(); // 바둑돌 선택 시 담는 list
	int cnt = 0; // 3 이 되면 바둑돌 추가로 선택 불가
	JToggleButton[] btnArr;

	public OmokSt() {

		JFrame frm = new JFrame("과일 오목게임");
		ImageIcon imgBg = new ImageIcon("img/start_bg.png");
		JLabel lbImage1 = new JLabel(imgBg);

		ImageIcon icon = new ImageIcon("img/text1.png"); 
		JLabel la = new JLabel(icon);
		la.setBounds(0, 50, 800, 150);
		frm.add(la);
		
		ImageIcon icon2 = new ImageIcon("img/text2.png"); 
		JLabel la2 = new JLabel(icon2);
		la2.setBounds(10, 200, 780, 100);
		frm.add(la2);

		// 과일 버튼
		JToggleButton sb1 = new JToggleButton(img1);
		sb1.setBounds(120, 300, 100, 100);
		sb1.setBackground(new Color(238, 238, 238));
		sb1.addActionListener(new SelectChk());
		frm.add(sb1);

		JToggleButton sb2 = new JToggleButton(img2);
		sb2.setBounds(270, 300, 100, 100);
		sb2.setBackground(new Color(238, 238, 238));
		sb2.addActionListener(new SelectChk());
		frm.add(sb2);

		JToggleButton sb3 = new JToggleButton(img3);
		sb3.setBounds(420, 300, 100, 100);
		sb3.setBackground(new Color(238, 238, 238));
		sb3.addActionListener(new SelectChk());
		frm.add(sb3);

		JToggleButton sb4 = new JToggleButton(img4);
		sb4.setBounds(570, 300, 100, 100);
		sb4.setBackground(new Color(238, 238, 238));
		sb4.addActionListener(new SelectChk());
		frm.add(sb4);

		btnArr = new JToggleButton[] { sb1, sb2, sb3, sb4 };

		// 게임시작 버튼
		JButton btn1 = new JButton("게임시작");
		btn1.setBackground(new Color(146,208,80));
		btn1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btn1.setForeground(new Color(255, 255, 255));
		btn1.setBounds(250, 500, 100, 50);
		btn1.setBorder(null);
		frm.add(btn1);
		
		// 게임종료 버튼
		JButton btn2 = new JButton("게임종료");
		btn2.setBackground(new Color(146,208,80));
		btn2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btn2.setForeground(new Color(255, 255, 255));
		btn2.setBounds(450, 500, 100, 50);
		btn2.setBorder(null);
		frm.add(btn2);

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 바둑돌 3개 안고를시 에러가 나는걸 방지
				if (select.size() == 3) {
					new OmokMain(select);
					frm.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "바둑돌 3개를 골라주세요.", "에러", JOptionPane.QUESTION_MESSAGE);
				}
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		frm.add(lbImage1);
		frm.setVisible(true);
		frm.setBounds(0, 0, 800, 790);
		frm.setLocationRelativeTo(null);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	JToggleButton noClick;

	// 선택한 돌 list
	class SelectChk implements ActionListener {
		//JToggleButton noClick;

		@Override
		public void actionPerformed(ActionEvent e) {
			// 클릭한 바둑돌이 선택되있는지 확인하여 있을 경우 선택해제, 없을 경우 선택 으로 구분
			if (select.contains((JToggleButton) e.getSource())) {
				select.remove((JToggleButton) e.getSource());
				cnt -= 1;
			} else {
				select.add((JToggleButton) e.getSource());
				cnt += 1;
			}
			if (cnt == 3) {
				for (JToggleButton jtb : btnArr) {
					if (!select.contains(jtb)) {
						noClick = jtb;
						jtb.setEnabled(false);
					}
				}
			}
			
			if(noClick != null && cnt < 3) {
				noClick.setEnabled(true);
			}
		}

	}

	public static void main(String[] args) {
		new OmokSt();
	}
}
