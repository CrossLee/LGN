package com.withiter.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.withiter.util.DesUtils;

/**
 * Cross Lee
 * @param args
 */

public class Main extends JFrame {

	private static final long serialVersionUID = 3350060192892360223L;

	class Time {
		private String name;
		private String value;
		
		public Time(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String toString(){
			return name;
        }
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
	
	private JLabel demoLabel;
	private JLabel macLabel;
	private JTextArea macs;
	private JButton btGenerate;
	private JButton btClear;
	private JComboBox jcbTime;
	private List<Time> listTimes = new ArrayList<Time>();
	Main() {
		
		listTimes.add(new Time("1 month","1"));
		listTimes.add(new Time("6 months","6"));
		listTimes.add(new Time("1 year","12"));
		listTimes.add(new Time("2 years","24"));
		listTimes.add(new Time("3 years","36"));
		listTimes.add(new Time("4 years","48"));
		listTimes.add(new Time("5 years","60"));
		listTimes.add(new Time("6 years","72"));
		listTimes.add(new Time("7 years","84"));
		listTimes.add(new Time("8 years","96"));
		listTimes.add(new Time("9 years","108"));
		listTimes.add(new Time("10 years","120"));
		listTimes.add(new Time("50 years","600"));

		this.setTitle("License 生成器");
		demoLabel = new JLabel("请选择License时间");
		jcbTime = new JComboBox(listTimes.toArray());
		
		macLabel = new JLabel("请输入设备的MAC地址（格式：C8-2A-14-46-DE-BA），多个设备请以空格分开！");
		macs = new JTextArea();
		
		btGenerate = new JButton("生成");
		btClear = new JButton("清空");

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = (int) screenSize.getWidth();
		int y = (int) screenSize.getHeight();
		this.setBounds((x - 840) / 2, (y - 600) / 2, 840, 600);
		this.setResizable(false);
		this.setLayout(null);

		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		demoLabel.setBounds(30, 30, 200, 20);
		jcbTime.setBounds(250, 30, 200, 20);
		
		macLabel.setBounds(30, 60, 600, 20);
		macs.setBounds(30, 90, 750, 400);
		macs.setAutoscrolls(true);
		macs.setWrapStyleWord(true);
		macs.setLineWrap(true);
		
		btGenerate.setBounds(30, 500, 60, 20);
		btClear.setBounds(100, 500, 60, 20);

		btGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desKey = String.valueOf(System.currentTimeMillis());

				int index = jcbTime.getSelectedIndex();
				String timeSelected = listTimes.get(index).getValue();
				int length = timeSelected.length();
				String firstLine = length + "" + timeSelected + "" + desKey;
				System.out.println(firstLine);
					
				if (!macs.getText().trim().isEmpty()) {
					String[] cpuIds = macs.getText().trim().split(" ");
					String[] encry = new String[cpuIds.length];
					String str = null;
					String strEnc = null;
					try {
						DesUtils des = new DesUtils(desKey);
						for (int i = 0; i < cpuIds.length; i++) {
							str = cpuIds[i];
							strEnc = des.encrypt(str);
							encry[i] = strEnc;
						}

						JFileChooser c = new JFileChooser();
						c.setSelectedFile(new File("License.txt"));

						int rVal = c.showSaveDialog(c);
						if (rVal == 0) {
							String path = c.getSelectedFile().getAbsolutePath();
							FileWriter fw = new FileWriter(path);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(firstLine);
							bw.newLine();
							for (int i = 0; i < encry.length; i++) {
								bw.write(encry[i]);
								bw.newLine();
							}

							bw.flush();
							bw.close();
							JOptionPane.showMessageDialog(null, "License生成成功", "生成状态", JOptionPane.INFORMATION_MESSAGE);
						}
						 
					} catch (InvalidKeyException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "License生成失败\n"+e1.getMessage(), "生成状态", JOptionPane.ERROR_MESSAGE); 
					} catch (HeadlessException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "License生成失败\n"+e1.getMessage(), "生成状态", JOptionPane.ERROR_MESSAGE);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "License生成失败\n"+e1.getMessage(), "生成状态", JOptionPane.ERROR_MESSAGE);
					} catch (NoSuchPaddingException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "License生成失败\n"+e1.getMessage(), "生成状态", JOptionPane.ERROR_MESSAGE);
					} catch (IllegalBlockSizeException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "License生成失败\n"+e1.getMessage(), "生成状态", JOptionPane.ERROR_MESSAGE);
					} catch (BadPaddingException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "License生成失败\n"+e1.getMessage(), "生成状态", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "License生成失败\n"+e1.getMessage(), "生成状态", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				macs.setText("");
				macs.requestFocus();
			}
		});
		this.add(jcbTime);
		this.add(demoLabel);
		this.add(macLabel);
		this.add(macs);
		
		this.add(btGenerate);
		this.add(btClear);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}
