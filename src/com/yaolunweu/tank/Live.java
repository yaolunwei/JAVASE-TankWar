package com.yaolunweu.tank;
import java.awt.*;
/**
 * 玩家坦克的生命值类。
 * @author xiaoyao
 *
 */
public class Live {
	
	private static final int WIDTH = 100;
	private static final int HEIGTH = 5;
	/**
	 * live变量时坦克的生命值，在构造函数中初始化。
	 */
	int live; 
	/**
	 * 主战坦克的引用。
	 * 对Tank类中的成员变量和方法的访问。
	 */
	Tank t;
	/**
	 * 主窗口主函数引用，对TankWar类中的属性和方法的访问。
	 */
	TankWar tw;
	/**
	 * 构造函数。
	 * 初始化生命值	
	 * @param live
	 * @param t 	对Tank类的引用参数。
	 * @param tw
	 */
	public Live(int live, Tank t, TankWar tw) {
		this.live = live;
		this.t = t;
		this.tw = tw;
	}
	/**
	 * 画出血条的生命值。
	 * @param g  得到画笔。
	 */
	public void drawLive(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.red);
		if(t.isLive()){
			g.drawRect(t.x-20, t.y-20, WIDTH, HEIGTH);
			if(live < 40){
				g.setColor(Color.magenta);
				g.drawString(" 你快挂了！按Q键发射超级子弹！",t.x-50, t.y-30);
			}
			else g.setColor(Color.red);
			g.fillRect(t.x-20, t.y-20, live, HEIGTH);
		} 
		else return;
		g.setColor(c);
		
	}
}
