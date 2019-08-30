package cn.yq;

import javax.swing.JFrame;
//贪吃蛇程序入口
public class Snake {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		//x,y表示窗口在屏幕的位置，widt，heigh表示窗口大小
		frame.setBounds(400, 200, 900, 720);
		//设置不可调整大小
		frame.setResizable(false);
		//设置关闭的时候出现退出
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SnakePanel panel = new SnakePanel();
		//把JPanel(轻量级容器)是置于其JFrame(窗口)上
		frame.add(panel);
		//设置可见
		frame.setVisible(true);
	}
}
