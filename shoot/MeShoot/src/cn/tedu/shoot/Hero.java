package MeShoot.src.cn.tedu.shoot;
import java.awt.image.BufferedImage;

/** è±éæ?: æ¯é£è¡ç© */
public class Hero extends FlyingObject {
	private static BufferedImage[] images; //å¾çæ°ç»
	static{
		images = new BufferedImage[6];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("hero"+i+".png");
		}
	}
	
	private int life; 		//å?
	private int doubleFire; //ç«åå?
	/** æé? æ¹æ³? */
	public Hero(){
		super(97,124,140,400);
		this.life = 3; //é»è®¤3æ¡å½
		this.doubleFire = 0; //åå?ç«å?
	}
	
	/** è±éæºéç?é¼ æ ç§»å¨ x,y:é¼ æ çxåy */
	public void moveTo(int x,int y){
		this.x = x-this.width/2;  //è±éæºçx=é¼ æ çx-1/2è±éæºçå®?
		this.y = y-this.height/2; //è±éæºçy=é¼ æ çy-1/2è±éæºçé«?
	}
	
	/** è±éæºç§»å? */
	public void step(){
		
	}
	
	int index = 0; //æ´»ççä¸æ ?
	int deadIndex = 2; //æ­»äºçä¸æ ?
	/** éågetImage()è·åå¾ç */
	public BufferedImage getImage(){ //10æ¯«ç§èµ°ä¸æ¬?
		if(isLife()){ //è¥æ´»ç?å?
			return images[index++%2]; //2å¼ å¾çåæ?
			/*
			 *               index=0
			 * 10M images[0] index=1
			 * 20M images[1] index=2
			 * 30M images[0] index=3
			 * 40M images[1] index=4
			 */
		}else if(isDead()){ //è¥æ­»äºå¢
			BufferedImage img = images[deadIndex++]; //ä¸æ ä»?2å¼?å§?
			if(deadIndex==images.length){ //å½ä¸æ ä¸ºæ°ç»çé¿åº?
				state = REMOVE; //åä¿®æ¹å½åç¶æä¸ºå¯ä»¥å é¤ç?
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
	
	/** è±éæºåå°å­å¼?(åå»ºå­å¼¹å¯¹è±¡) */
	public Bullet[] shoot(){
		int xStep = this.width/4; //1/4è±éæºçå®? 
		int yStep = 20; //åºå®ç?20
		if(doubleFire>0){ //å?
			Bullet[] bs = new Bullet[2]; //2åå­å¼?
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep); //x:è±éæºçx+1/4è±éæºçå®? y:è±éæºçy-åºå®ç?20
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep); //x:è±éæºçx+3/4è±éæºçå®? y:è±éæºçy-åºå®ç?20
			doubleFire-=2; //åå°ä¸?æ¬¡ååç«åï¼åç«åå?¼å2
			return bs;
		}else{ //å?
			Bullet[] bs = new Bullet[1]; //1åå­å¼?
			bs[0] = new Bullet(this.x+2*xStep,this.y-yStep); //x:è±éæºçx+2/4è±éæºçå®? y:è±éæºçy-åºå®ç?20
			return bs;
		}
	}
	
	/** éåoutOfBounds()å¤æ­æ¯å¦è¶ç */
	public boolean outOfBounds(){
		return false; //æ°¸ä¸è¶ç
	}
	
	/** è±éæºå¢å? */
	public void addLife(){
		life++; //å½æ°å¢?1
	}
	
	/** è·åè±éæºçå? */
	public int getLife(){
		return life; //è¿åå½æ°
	}
	
	/** è±éæºåå? */
	public void subtractLife(){
		life--; //å½æ°å?1
	}
	
	/** æ¸ç©ºè±éæºçç«åå? */
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	
	/** è±éæºå¢ç«å */
	public void addDoubleFire(){
		doubleFire+=40; //ç«åå¼å¢40
	}
	
}















