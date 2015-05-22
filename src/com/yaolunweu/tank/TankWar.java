package com.yaolunweu.tank;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * 这个类的作用是坦克的主程序。
 * 主要加入javaDoc ，纯净版请参照上以一版本。
 * @author xiaoyao
 *
 */

public class TankWar extends Frame {
	/**
	 * 屏幕宽度。
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * 屏幕高度
	 */
	public static final  int GAME_HEIGHT = 600;
	/**
	 * new 一辆好的坦克（主坦克）出来。
	 */
	Tank myTank = new Tank(50, 500, true, TankWay.STOP, this);
	/**
	 * 为好坦克加入生命值，尚需改进，应该定义在Tank类中。
	 */
	Live l1 = new Live(100, myTank,this);
	/**
	 * 指定位置大小画第一堵墙，
	 */
	Wall w1 = new Wall(400,100,20,400,this);
	/**
	 * 指定位置大小画第二堵墙。
	 */
	Wall w2 = new Wall(200,300,400,20,this);
	/**
	 * 画血块。
	 */
	Blood b = new Blood(this);
	/**
	 * 装载子弹的集合。
	 */
	ArrayList<Missile> missiles = new ArrayList<Missile>();
	/**
	 * 爆炸集合。用于同时产生多个爆炸。
	 */
	ArrayList<TankDrea> tankDreas = new ArrayList<TankDrea>();
	/**
	 * 坏坦克集合。
	 */
	ArrayList<Tank> raTanks = new ArrayList<Tank>();
	/**
	 * 静态的随机数。暂时没有用到。
	 */
	static Random r = new Random();
	/**
	 * 定义双缓冲，使用后图片不会抖动。
	 * 详情参照API文档。
	 */
	
	Image offScreenImage = null;
	/**
	 * 未用到变量。
	 */
	private static final long serialVersionUID = 8257776081794640863L;
	/**
	 * 加载背景图片。
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image backgroundImage = tk.getImage(TankWar.class.getClassLoader().getResource("images/tankBackground1.gif"));
	/**
	 * 主函数：画出主窗体。
	 * @param args
	 */
	public static void main(String[] args) {
		new TankWar().DrawTankWar();
	}
	/**
	 * 在主窗体中画出一系列需要的东西，
	 * 使用线程不间断的重画。
	 */
	public void paint(Graphics g){
		g.setColor(Color.red);
		/**
		 * 在窗口左上显示的文字。 
		 */
		g.drawString("正在飞的子弹："+ missiles.size(), 10, 50);
		g.drawString("爆炸剩余数量："+ tankDreas.size(), 10,70);
		g.drawString("敌坦克数量："+ raTanks.size(), 10,90);
		g.drawString("随机数："+ b.tmp, 10,110);
		
		g.drawImage(backgroundImage, 0 , 0 , null); //加入背景图片，尚需改进（重画时都会加入图片，影响速度。）
		
		/**
		 * 读取raTanks容器中的坏坦克
		 * 并与墙 and raTanks容器中的每一辆坦克进行碰撞检测。
		 */
		for(int i=0;i<raTanks.size();i++){ 
				Tank t = raTanks.get(i);
				t.TankWithWall(w1);
				t.TankWithWall(w2);
				t.tankWithTank(raTanks);
				t.drawTank(g); 
		}
		/**
		 * 读取raTanks容器中的每一发子弹
		 * 并与墙 and 子弹 and 好坦克  raTanks容器中的每一辆坦克进行碰撞检测。
		 * 详情参照missile 中的函数；
		 */
		for(int i=0;i<missiles.size();i++){
			Missile m = missiles.get(i);
			m.hitTanks(raTanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.mToM(missiles);
			m.drawMissile(g);
			//b.drawBlood(g);
		}
		/**
		 * 画出爆炸。
		 */
		for(int j=0;j<tankDreas.size();j++){
			TankDrea td =  tankDreas.get(j);
			td.drawTankDrea(g);
		}
		/**
		 * 画坦克，血块 ，号坦克生命。
		 */
		myTank.drawTank(g);
		myTank.tankWithTank(raTanks);
		myTank.eatBlood(b);
		w1.drawWall(g);
		w2.drawWall(g);
		l1.drawLive(g);
		if(raTanks.size() <= 0) tanks("reTankCount");
	} 
	/**
	 * 双缓冲技术，还不明白。
	 */
	public void update(Graphics g){
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.black);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	/**
	 * 窗口显示。
	 * 并加入事件监听  按下按键 释放按键 关闭窗口 事件。
	 */
	
	public void DrawTankWar(){
		tanks("initTankCount");
		this.setTitle(" 坦克大战 ");
		this.setLocation(200, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.black);	
		this.setResizable(false);
		this.addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
			
		});
		
		
		this.addKeyListener(new KeyAdapter(){
		
			public void keyReleased(KeyEvent e) {
				myTank.outKey(e.getKeyCode());
			}

			public void keyPressed(KeyEvent e){
				myTank.pressedKey(e.getKeyCode());
			}
		});
		
		setVisible(true);
	
		new Thread(new PaintTread()).start();  //主窗口载入后线程开始。
	
	}
	/**
	 * 画坏坦克。
	 * @param str	调用配置文件tank.properties 设置坦克数量。
	 */
	public void tanks(String str){
		for(int i=0; i<PropertyMgr.pros(str); i++){
			raTanks.add(new Tank(r.nextInt(750),r.nextInt(550),false,TankWay.D,this));
		}
	}
	
	/**
	 * 加入内部线程类不间断的重画 paint()方法。 
	 * repaint();  方法间接调用 paint() 方法。
	 * @author 小妖
	 *
	 */
	private class PaintTread implements Runnable{

		public void run() {
			while(true){
				repaint(); 
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
	}

}