package MeShoot.src.cn.tedu.shoot;
import java.awt.image.BufferedImage;

/** 英雄�?: 是飞行物 */
public class Hero extends FlyingObject {
	private static BufferedImage[] images; //图片数组
	static{
		images = new BufferedImage[6];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("hero"+i+".png");
		}
	}
	
	private int life; 		//�?
	private int doubleFire; //火力�?
	/** 构�?�方�? */
	public Hero(){
		super(97,124,140,400);
		this.life = 3; //默认3条命
		this.doubleFire = 0; //单�?�火�?
	}
	
	/** 英雄机随�?鼠标移动 x,y:鼠标的x和y */
	public void moveTo(int x,int y){
		this.x = x-this.width/2;  //英雄机的x=鼠标的x-1/2英雄机的�?
		this.y = y-this.height/2; //英雄机的y=鼠标的y-1/2英雄机的�?
	}
	
	/** 英雄机移�? */
	public void step(){
		
	}
	
	int index = 0; //活着的下�?
	int deadIndex = 2; //死了的下�?
	/** 重写getImage()获取图片 */
	public BufferedImage getImage(){ //10毫秒走一�?
		if(isLife()){ //若活�?�?
			return images[index++%2]; //2张图片切�?
			/*
			 *               index=0
			 * 10M images[0] index=1
			 * 20M images[1] index=2
			 * 30M images[0] index=3
			 * 40M images[1] index=4
			 */
		}else if(isDead()){ //若死了呢
			BufferedImage img = images[deadIndex++]; //下标�?2�?�?
			if(deadIndex==images.length){ //当下标为数组的长�?
				state = REMOVE; //则修改当前状态为可以删除�?
			}
			return img;
			/*
			 *               deadIndex=2 
			 * 10M images[2] deadIndex=3
			 * 20M images[3] deadIndex=4
			 * 30M images[4] deadIndex=5
			 * 40M images[5] deadIndex=6 REMOVE
			 *     
			 */
		}
		return null;
	}
	
	/** 英雄机发射子�?(创建子弹对象) */
	public Bullet[] shoot(){
		int xStep = this.width/4; //1/4英雄机的�? 
		int yStep = 20; //固定�?20
		if(doubleFire>0){ //�?
			Bullet[] bs = new Bullet[2]; //2发子�?
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep); //x:英雄机的x+1/4英雄机的�? y:英雄机的y-固定�?20
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep); //x:英雄机的x+3/4英雄机的�? y:英雄机的y-固定�?20
			doubleFire-=2; //发射�?次双倍火力，则火力�?�减2
			return bs;
		}else{ //�?
			Bullet[] bs = new Bullet[1]; //1发子�?
			bs[0] = new Bullet(this.x+2*xStep,this.y-yStep); //x:英雄机的x+2/4英雄机的�? y:英雄机的y-固定�?20
			return bs;
		}
	}
	
	/** 重写outOfBounds()判断是否越界 */
	public boolean outOfBounds(){
		return false; //永不越界
	}
	
	/** 英雄机增�? */
	public void addLife(){
		life++; //命数�?1
	}
	
	/** 获取英雄机的�? */
	public int getLife(){
		return life; //返回命数
	}
	
	/** 英雄机减�? */
	public void subtractLife(){
		life--; //命数�?1
	}
	
	/** 清空英雄机的火力�? */
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	
	/** 英雄机增火力 */
	public void addDoubleFire(){
		doubleFire+=40; //火力值增40
	}
	
}















