package MeShoot.src.cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
/** 小蜜�?: 是飞行物 */
public class Bee extends FlyingObject implements Award {
	private static BufferedImage[] images; //图片数组
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("bee"+i+".png");
		}
	}
	
	private int xSpeed;    //x坐标移动速度
	private int ySpeed;	   //y坐标移动速度
	private int awardType; //奖励类型(0�?1)
	/** 构�?�方�? */
	public Bee(){
		super(60,50);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2); //0�?1之内的随机数
	}
	
	/** 小蜜蜂移�? */
	public void step(){
		x+=xSpeed; //x+(向左或向�?)
		y+=ySpeed; //y+(向下)
		if(x<=0 || x>=World.WIDTH-this.width){ //若x<=0或�?�x>=(窗口�?-蜜蜂�?)，说明到两边了，则修改x移动的方�?
			xSpeed*=-1; //正变负，负变�?
		}
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
		return this.y>=World.HEIGHT; //小蜜蜂的y>=窗口的高，即为越界了
	}
	
	/** 重写getType()获取奖励类型 */
	public int getType(){
		return awardType; //返回奖励类型(0�?1)
	}
	
}












