package com.yaolunweu.tank;
import java.awt.*;
import java.util.*;
/**
 * 子弹类
 * @author 小妖
 */
public class Missile {
	/**
	 * 子弹的速度。
	 * 高度和宽度，应当改为图片的高度和宽度。
	 */
	private static final int XSPEED = 2;
	private static final int YSPEED = 2;
	static final int WIDTH = 12;
	static final int HEIGHT = 12;
	/**
	 * 发射子弹的位置。
	 */
	int x,y;
	/**
	 * 定义TankWar类的引用，
	 * 对TankWar类成员与方法的访问。
	 */
	TankWar tw;
	/**
	 * 在构造函数中初始化好"蛋"还是坏"蛋"。
	 * 好的true，坏的false，在子弹与子弹和坦克碰撞的时候判断子弹是否消失。
	 */
	boolean good ;
	/**
	 * 对泛型TankWar的引用，
	 * 八个方向。
	 */
	TankWay way;
	
	/**
	 *子弹的生死控制
	 */
	boolean live = true;
	
	/**
	 * 以下三行代码是从硬盘上读取图片。
	 * 当new missile是就初始化以下代码，
	 * 将图片的缩影加载到内存中，只需初始化一次就可以了，
	 * 所以定义为static 静态的。
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit(); 
	/* 	此类是所有 Abstract Window Toolkit 实际实现的抽象超类。
	 * Toolkit 的子类被用于将各种组件绑定到特定本机工具包实现。 
	 *许多 GUI 操作可以异步执行。这意味着如果设置某一组件的状态，
	 *随后立刻查询该状态，则返回的值可能并没有反映所请求的更改。
	 *这包括但不局限于以下操作：请参照API文档。
	 */
	private static Image[] missileImages = null; //定义图片数组。
	private static Map<String, Image> imgs = new HashMap<String,Image>();
	//定义Map容器以键值方式读取数组中的图片。
	
	/**
	 * new missile 类时被调到内存中初始化，且只执行一次。
	 */
	static {
		missileImages = new Image[] {
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif")),
		};
		imgs.put("L", missileImages[0]);
		imgs.put("LU", missileImages[1]);
		imgs.put("U", missileImages[2]);
		imgs.put("RU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("D", missileImages[6]);
		imgs.put("LD", missileImages[7]);
	}	
	/**
	 * 子弹构造函数：
	 * @param x		初始化x的值为坦克的x值，随坦克的位置改变而改变。
	 * @param y		初始化y的值为坦克的y值。
	 * @param way	初始化way的值，得到八个方向。
	 */
	public Missile(int x, int y, TankWay way) {
		this.x = x;
		this.y = y;
		this.way = way;
	}
	/**
	 * 	构造函数：
	 * @param x	
	 * @param y
	 * @param good	初始化子弹是好"蛋"还是坏"蛋"，true为好蛋，false为坏蛋。
	 * @param way
	 * @param tw	TankWar类型，TankWar引用。
	 */
	public Missile(int x, int y, boolean good,TankWay way, TankWar tw){
		this(x,y,way);
		this.good = good;
		this.tw = tw;
	}
	
	

	/**
	 * 画子弹。
	 * 判断子弹是否死了 ？死就别画了，并从TankWar类中missiles 容器中移除，
	 * 活着 接续话。
	 * @param g 画笔。
	 */
	public void drawMissile(Graphics g){
		if(!live) {		
			tw.missiles.remove(this);
			return;
		}
		
		
		switch(way){
		case L : g.drawImage(imgs.get("L"), x, y, null); break;
		case LU : g.drawImage(imgs.get("LU"), x, y, null);break;
		case U : g.drawImage(imgs.get("U"), x, y, null);break;
		case RU : g.drawImage(imgs.get("RU"), x, y, null);break;
		case R : g.drawImage(imgs.get("R"), x, y, null);break;
		case RD : g.drawImage(imgs.get("RD"), x, y, null);break;
		case D : g.drawImage(imgs.get("D"), x, y, null);break;
		case LD : g.drawImage(imgs.get("LD"), x, y, null);break;
		}
		move();
	}
	/**
	 * 发出子弹后子弹的并按 Tank打出子弹的方向移动。
	 */
	private void move(){
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
		
		}
		if( x<0 || y<0 || x>TankWar.GAME_WIDTH || y>TankWar.GAME_HEIGHT){	//只要子弹超出主窗口的大小子弹死忙。
			live = false;
		}
	}
	/**
	 * 得到子弹的生死状态：
	 * @return	true 生 \n, false 死。
	 */
	public boolean isLive(){
		return live;
	}
	/**
	 * Rectangle 指定坐标空间中的一个区域，
	 * 通过坐标空间中 Rectangle 对象左上方的点 (x,y)、
	 * 宽度和高度可以定义这个区域。
	 * 详情请参照API文档。
	 * @return	返回子弹的坐标区域。
	 */
	public Rectangle getRec(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
		
	}
	/**
	 * 子弹与坦克碰撞时：
	 * if( this.live && this.getRec().intersects(t.getRec()) && t.isLive() && this.good != t.isGood())
	 * 判断子弹是否活着  and 子弹与坦克是否相遇 and 不是同一种子弹（好蛋或者坏蛋） 才会打到坦克然后
	 * new 爆炸类(TankDrea) 加入到TankWar tankDreasr容器中并画出来。
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){
		if( this.live && this.getRec().intersects(t.getRec()) && t.isLive() && this.good != t.isGood()){
			TankDrea td = new TankDrea(x,y,tw);
			if(t.isGood()){
				tw.l1.live -= 10; 	// 主坦克被打到一次减10滴血。
				tw.missiles.remove(this);  	//子弹消失。
				if(tw.l1.live <= 0) { 	//判断血少与等于零时，产生爆炸，设置主坦克生命为false。
					tw.tankDreas.add(td);
					t.setLive(false);
				}
			}	
			else {					//非主战坦克 被子弹打到一次就死亡。
				t.setLive(false);
				tw.tankDreas.add(td);
				tw.missiles.remove(this);
			}
			
			return true;
			
		}
		return false;	
	}
	/**
	 * 子弹跟raTanks容器中的每一辆坦克碰撞检测。 
	 * @param raTanks
	 * @return
	 */
	public boolean hitTanks(ArrayList<Tank> raTanks) {
		for(int i=0;i<raTanks.size();i++){
			if(hitTank(raTanks.get(i))) 
				return true;
		} 
		return false;
	}
	/**
	 * 子弹与墙碰撞。
	 * @param 子弹与那个墙碰撞。
 	 */
	public void hitWall(Wall w){
			if(this.isLive() && this.getRec().intersects(w.getRec())) {
				tw.missiles.remove(this);
		}
		
		
	}
	/**
	 * 子弹与子弹碰撞检测。
	 * @param ms	改子弹与所有的子弹检测。
	 */
	public void mToM(ArrayList<Missile> ms){
		for(int i=0; i<ms.size(); i++ ){
			Missile m = ms.get(i);
			if(this.isLive() && this.good != m.good && m.isLive() && this.getRec().intersects(m.getRec())) {
				ms.remove(m);
				ms.remove(this);
			}
		}
	}

}
