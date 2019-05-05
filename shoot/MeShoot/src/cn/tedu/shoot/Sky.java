package MeShoot.src.cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** 天空: 是飞行物 */
public class Sky extends FlyingObject {
	private static BufferedImage image; //图片
	static{
		image = loadImage("background.png");
	}
	
	private int speed;	//移动速度
	private int y1;		//y1坐标(图片轮换)
	/** 构�?�方�? */
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
		speed = 1;
		y1 = -height; //y1:负的窗口的高
	}
	
	/** 天空移动 */
	public void step(){
		y+=speed;  //y+(向下)
		y1+=speed; //y1+(向下)
		if(y>=this.height){ //若y>=天空的高(到下面了)
			y=-this.height; //则设置y为负的高(回最上面)
		}
		if(y1>=this.height){ //若y1>=天空的高(到下面了)
			y1=-this.height; //则设置y1为负的高(回最上面)
		}
	}
	
	/** 重写getImage()获取图片 */
	public BufferedImage getImage(){
		return image;
	}
	
	/** 画对�? g:画笔 */
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);  //画天�?1
		g.drawImage(getImage(),x,y1,null); //画天�?2
	}
	
	/** 重写outOfBounds()判断是否越界 */
	public boolean outOfBounds(){
		return false; //永不越界
	}
	
}











