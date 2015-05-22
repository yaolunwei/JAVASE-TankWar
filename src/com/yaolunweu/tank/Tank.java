package com.yaolunweu.tank;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * Tank类 
 * 对坦克的描述：
 * @author 小妖
 *
 */
public class Tank {
	/*
	 * 定义了坦克的大小，移动速度。
	 * OldX ，OldY 存储了坦克移动上一步的位置。
	 */
	private static final int XSPEED = 2;
	private static final int YSPEED = 2;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;
	private int oldX, oldY;
	
	
	private boolean bU = false, bR = false, bD = false, bL = false;
	private TankWay  way = TankWay.STOP;
	private TankWay paoTongWay = TankWay.R; //坦克根据炮筒的方向发子弹。
	private static Random r = new Random(); // 随机数。
	private int setp = r.nextInt(20)+5; // 定义了坏坦克移动的步数。
	private boolean live = true; 	
	private boolean good ;
	int x, y;
	TankWar tw;
	/**
	 * 请参照API文档。
	 * 载入图片并以定义HashMap，以键值画图片。 
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImages = null;
	private static HashMap<TankWay, Image> imgs = new HashMap<TankWay,Image>();
	
	
	
	/**
	 * 启动时静态代码执行将图片载入内存中。
	 */
	static {
		tankImages = new Image[] {
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
		tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")),
		};
		mapImgs(imgs);
	}
	
	public static void mapImgs(HashMap<TankWay,Image> ms){
		ms.put(TankWay.L, tankImages[0]);
		ms.put(TankWay.LU, tankImages[1]);
		ms.put(TankWay.U, tankImages[2]);
		ms.put(TankWay.RU, tankImages[3]);
		ms.put(TankWay.R, tankImages[4]);
		ms.put(TankWay.RD, tankImages[5]);
		ms.put(TankWay.D, tankImages[6]);
		ms.put(TankWay.LD, tankImages[7]);
	} 
	/**
	 * 构造函数
	 * @param x 	坦克位置x。
	 * @param y		坦克位置y。
	 * @param good	坦克是好还是坏。
	 */
	public Tank(int x, int y,boolean good){
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	/**
	 * 构造函数
	 * @param x
	 * @param y
	 * @param good
	 * @param way	参数TankWay类型，得到方向。
	 * @param tw	对TankWar 的引用。
	 */
	public Tank(int x,int y,boolean good,TankWay way,TankWar tw){
		this(x, y, good);
		this.way = way;
		this.tw = tw;
	}
	/**
	 * 根据坦克的方向画坦克。
	 * 如果坦克死了就别画。
	 * @param g
	 */
	public void drawTank(Graphics g){
		if(!live){
			if(!good){
				tw.raTanks.remove(this);
			}
			return;
		}
		
		switch(paoTongWay){
		case L:
			g.drawImage(imgs.get(TankWay.L), x , y , null);
			break ;
		case LU:
			g.drawImage(imgs.get(TankWay.LU), x , y , null);
			break ;
		case U:
			g.drawImage(imgs.get(TankWay.U), x , y , null);
			break ;
		case RU:
			g.drawImage(imgs.get(TankWay.RU), x , y , null);
			break ;
		case R:
			g.drawImage(imgs.get(TankWay.R), x , y , null);
			break ;		
		case RD:
			g.drawImage(imgs.get(TankWay.RD), x , y , null);
			break ;
		case D:
			g.drawImage(imgs.get(TankWay.D), x , y , null);
			break ;
		case LD:
			g.drawImage(imgs.get(TankWay.LD), x , y , null);
			break ;
			
		}
			move();
	}
	/**
	 * 坦克的移动方法。
	 * 并检测坦克位置是否超出主窗体 。
	 * 判断 是坏坦克 就随机改变way方向并移动 随机发子弹。 
	 */
	public void move(){
		this.oldX = x;
		this.oldY = y;
		switch(way){
		case  L:
			x -= XSPEED;
			break;
		case  LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case  U:
			y -= YSPEED;
			break;
		case  RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case  R:
			x += XSPEED;
			break;
		case  RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case  D:
			y += YSPEED;
			break;
		case  LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case  STOP:
			break;
		}
	
		if(this.way != TankWay.STOP) this.paoTongWay = this.way;
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankWar.GAME_WIDTH) x = TankWar.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankWar.GAME_HEIGHT) y = TankWar.GAME_HEIGHT - Tank.HEIGHT; 
		if(!good){
			TankWay[] tws = TankWay.values();
			if(setp == 0){
				setp = r.nextInt(20)+5;
				int ra = r.nextInt(tws.length);
				way = tws[ra];
			}
			setp --;
			
			if(r.nextInt(40) > 38) this.fire();
		
		}
	}
	/**
	 * 按下按键时并判断处理。
	 * @param key 	接受按键值。
	 */
	public void pressedKey(int key){
		
		switch(key) {
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_A :
			fire();
			break;
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		}
		keyConnect();
		
	}
	/**
	 * 根据按下的按键判断坦克的方向。
	 */
	public void keyConnect(){
		if(bL && !bU && !bR && !bD) way = TankWay.L; 
		else if(bL && bU && !bR && !bD) way = TankWay.LU;
		else if(!bL && bU && !bR && !bD) way = TankWay.U;
		else if(!bL && bU && bR && !bD) way = TankWay.RU;
		else if(!bL && !bU && bR && !bD) way =TankWay.R;
		else if(!bL && !bU && bR && bD) way = TankWay.RD;
		else if(!bL && !bU && !bR && bD) way = TankWay.D;
		else if(bL && !bU && !bR && bD) way = TankWay.LD;
		else if(!bL && !bU && !bR && !bD) way = TankWay.STOP;
		
		
		
	}
	/**
	 * 释放按键时所作的处理。
	 * 按 f2 主坦克复活。
	 * 按Q发射super子弹。
	 * @param key
	 */
	public void outKey(int key){
		switch(key) {
		case KeyEvent.VK_F2 :
			if(!tw.myTank.live)
				tw.myTank = new Tank(50, 500, true, TankWay.STOP, tw);
			 	tw.l1 = new Live(100, tw.myTank,tw);
			break;
		case KeyEvent.VK_Q :
			superFire();
			break;
		case KeyEvent.VK_LEFT :
			bL = false;
			break;
		case KeyEvent.VK_UP :
			bU = false;
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		}
		keyConnect();
		
	}
	/**
	 * 发射子弹。
	 * 首先判断子弹是否生，在把子弹加入到 missiles容器中并画出来。
	 * @return
	 */
	public Missile fire(){
		if(!live) return null;
		int x = this.x +Tank.WIDTH/2-Missile.WIDTH/2;
		int y = this.y +Tank.HEIGHT/2+5-Missile.HEIGHT/2;
		Missile m =  new Missile(x,y,good,paoTongWay,this.tw);
		tw.missiles.add(m);
		return m;
	}
	/**
	 *  与superFire() 方法连用。
	 * @param tws	参数	Tank类。
	 * @return
	 */
	public Missile fire(TankWay tws){
		if(!live) return null;
		int x = this.x +Tank.WIDTH/2-Missile.WIDTH/2;
		int y = this.y +Tank.HEIGHT/2-Missile.HEIGHT/2;
		Missile m =  new Missile(x,y,good,tws,this.tw);
		tw.missiles.add(m);
		return m;
		
	}
	/**
	 * 发射super炮弹 （向八个方向发射子弹）。
	 * 调用fire（TankWay，tws）方法配合。 
	 */
	public void superFire(){
		TankWay[] tws = TankWay.values();
		for(int i=0; i<8; i++){
			fire(tws[i]);
		}
	}
	/**
	 * 返回上一个位置。
	 * 坦克遇到墙和其它坦克时调用。
	 */
	public void stay(){
		this.x = oldX;
		this.y = oldY;
	}
	/**
	 * 得到坦克所在位置所占的区域。用于碰撞检测。
	 * @return
	 */
	public Rectangle getRec(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
		
	}
	/**
	 * 坦克与墙碰撞检测。
	 * @param w  参数Wall。
	 */
	public void TankWithWall(Wall w){
			if( this.live && this.getRec().intersects(w.getRec())){
				stay();
			}
	}
	/**
	 * 坦克与坦克碰撞检测。
	 * 碰到调用stay（）；方法坦克回到上一步位置。
	 * @param tanks
	 */
	public void tankWithTank(ArrayList<Tank> tanks){
		for(int i=0; i<tanks.size(); i++){
			Tank t = tanks.get(i);
			if(this != t){
				if( this.live && t.isLive() && this.getRec().intersects(t.getRec())){
					this.stay();
					t.stay();
				}
			}
		}
	}
	
	/**
	 * 坦克与血块碰撞检测。
	 * @param b
	 * @return
	 */
	
	public boolean eatBlood(Blood b){
		if( b.getLive() && this.isLive() && this.getRec().intersects(b.getRec())){
			tw.l1.live = 100;
			b.setLive(false);
			return true;
			}
		else return false;
	}
	/**
	 * 得到坦克生命状态。	
	 * @return
	 */
	public boolean isLive(){
		return live;
	}
	/**
	 * 设置坦克生命。
	 * @param live
	 */
	public void setLive(boolean live){
		this.live = live;
	}
	/**
	 * 返回坦克的类型（好，坏）。
	 * @return
	 */
	public boolean isGood(){
		return good;
	}
	

		
}
