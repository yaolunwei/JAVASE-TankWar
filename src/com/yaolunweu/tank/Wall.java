package com.yaolunweu.tank;
import java.awt.*;
/**
 * 墙类，子弹不能穿过。
 * @author 小妖
 *
 */
public class Wall {
	/**
	 * 定义了墙的位置和高度和宽度。
	 */
	int x, y, w, h;
	TankWar tw;
	/**
	 * 构造函数: 对画墙的一系列参数引用。
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param tw
	 */
	public Wall(int x, int y, int w, int h, TankWar tw) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tw = tw;
	}
	/**
	 * 画墙。
	 * @param g
	 */
	public void drawWall(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.gray);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	/**
	 * 墙所在位置，所占的区域，用于碰撞检测。
	 * @return
	 */
	public Rectangle getRec(){
		return new Rectangle(x, y, w, h);
		
	}


	
	
}
