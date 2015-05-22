package com.yaolunweu.tank;
import java.awt.*;
import java.util.Random;

/**
 * 这是血块类，尚需改进，
 * 血块显示时间不能很好的控制。
 * @author xiaoyao
 *
 */
public class Blood {
	/**
	 * 这是随机数
	 */
	private static Random r = new Random();
	int tmp;
	private int x ;		// 血块在窗口中显示的位置
	private int y ;	
	private final int w = 20, h = 20; 	//血块的高和宽。
	TankWar tw; 	//主窗口的引用。
	private boolean live = false; //血块的生命值。
	/**
	 *Blood血块构造函数。 
	 * @param tw
	 */
	Blood(TankWar tw) {
		this.tw = tw;
	}
	/**
	 * 画血块方法。
	 * @param g
	 */
	public void drawBlood(Graphics g){
		tmp = r.nextInt(100); 
		if(this.getLive()){
			if(tmp > 6)
				this.setLive(false);
			return ;
		}
		if(!this.getLive()){
			if(tmp > 98){
				this.setLive(true);
				xy();
				Color c = g.getColor();
				g.setColor(Color.RED);
				g.fillRect(x, y, w, h);
				g.setColor(c);
			}
			return ;
		}
	}

	public boolean getLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * Blood血块：
	 * Rectangle 指定坐标空间中的一个区域，通过坐标空间中 Rectangle 对象左上方的点 (x,y)、宽度和高度可以定义这个区域。
	 * @return
	 */
	public Rectangle getRec(){
		return new Rectangle(x,y,w,h);
		
	}
	/**
	 * x的值随机抽取大于零，小于主窗口的宽度，
	 * y的值随机抽取大于零，小于主窗口的高度。
	 */
	public void xy(){
		this.x = r.nextInt(TankWar.GAME_WIDTH);
		this.y = r.nextInt(TankWar.GAME_HEIGHT);
	}
	


}
