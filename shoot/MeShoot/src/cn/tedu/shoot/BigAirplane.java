package MeShoot.src.cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
/** 大敌�?: 是飞行物，也是敌人能得分 */
public class BigAirplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images; //图片数组
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("bigplane"+i+".png");
		}
	}
	
	private int speed;	//移动速度
	/** 构�?�方�? */
	public BigAirplane(){
		super(69,99);
		speed = 2;
	}
	
	/** 大敌机移�? */
	public void step(){
		y+=speed; //y+(向下)
	}
	
	int deadIndex = 1; //死了的下�?
	/** 重写getImage()获取图片 */
	public BufferedImage getImage(){ //10毫秒走一�?
		if(isLife()){ //若活�?�?
			return images[0]; //返回�?1张图�?
		}else if(isDead()){ //若死了呢
			BufferedImage img = images[deadIndex++]; //从第2张图片开�?
			if(deadIndex==images.length){ //当下标为数组的长�?
				state = REMOVE; //则修改当前状态为可以删除�?
			}
			return img;
		}
		return null;
	}
	
	/** 重写outOfBounds()判断是否越界 */
	public boolean outOfBounds(){
		return this.y>=World.HEIGHT; //大敌机的y>=窗口的高，即为越界了
	}
	
	/** 重写getScore()得分 */
	public int getScore(){
		return 3; //打掉�?个大敌机�?3�?
	}
	
}











