package MeShoot.src.cn.tedu.shoot;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/** 飞行�? */
public abstract class FlyingObject {
	public static final int LIFE = 0;   //活着�?
	public static final int DEAD = 1;   //死了�?
	public static final int REMOVE = 2; //可以删除�?
	protected int state = LIFE; //当前状�??(默认为活�?�?)
	
	protected int width; 		//�?
	protected int height; 	    //�?
	protected int x; 			//x坐标
	protected int y; 			//y坐标
	
	//默认构�??(Sky,Hero,Bullet)
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	//专门给敌人留�?(Airplane、BigAirplane、Bee)
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random(); //随机数对�?
		x = rand.nextInt(World.WIDTH-this.width); //x:0�?(窗口�?-小敌机宽)之内的随机数
		y = -this.height; //y:负的小敌机的�?
	}
	
	/** 加载/读取图片 */
	public static BufferedImage loadImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/** 飞行物移�? */
	public abstract void step();
	
	/** 获取图片 */
	public abstract BufferedImage getImage();
	
	/** 画对�? g:画笔 */
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null); //画对�?
	}
	
	/** 判断是否是活�?�? */
	public boolean isLife(){
		return state==LIFE;
	}
	/** 判断是否是死了的 */
	public boolean isDead(){
		return state==DEAD;
	}
	/** 判断是否是可以删除的 */
	public boolean isRemove(){
		return state==REMOVE;
	}
	
	/** 判断飞行物是否越�? */
	public abstract boolean outOfBounds();
	
	/** �?测敌人与子弹和英雄机的碰�? this:敌人 other:子弹和英雄机 */
	public boolean hit(FlyingObject other){
		int x1 = this.x-other.width;  //x1:敌人的x-子弹的宽
		int x2 = this.x+this.width;   //x2:敌人的x+敌人的宽
		int y1 = this.y-other.height; //y1:敌人的y-子弹的高
		int y2 = this.y+this.height;  //y2:敌人的y+敌人的高
		int x = other.x; //x:子弹的x
		int y = other.y; //y:子弹的y
		
		return x>=x1 && x<=x2
			   &&
			   y>=y1 && y<=y2; //x在x1与x2之间，并且，y在y1与y2之间，即为撞上了
	}
	
	/** 飞行物去�? */
	public void goDead(){
		state = DEAD;
	}
}














