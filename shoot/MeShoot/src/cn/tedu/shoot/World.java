package MeShoot.src.cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

//整个世界
public class World extends JPanel {
	public static final int WIDTH = 400;  //窗口的宽
	public static final int HEIGHT = 700; //窗口的高
	
	public static final int START = 0;     //启动状�??
	public static final int RUNNING = 1;   //运行状�??
	public static final int PAUSE = 2;     //暂停状�??
	public static final int GAME_OVER = 3; //游戏结束状�??
	private int state = START; //当前状�??(默认为启动状�?)
	
	private static BufferedImage start;    //启动�?
	private static BufferedImage pause;    //暂停�?
	private static BufferedImage gameover; //游戏结束�?
	static{
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");		
	}
	
	private Sky sky = new Sky(); //天空
	private Hero hero = new Hero(); //英雄�?
	private FlyingObject[] enemies = {}; //敌人数组(小敌�?+大敌�?+小蜜�?)
	private Bullet[] bullets = {}; //子弹数组
	
	/** 创建敌人(小敌�?+大敌�?+小蜜�?)对象 */
	public FlyingObject nextOne(){
		Random rand = new Random(); //创建随机数对�?
		int type = rand.nextInt(20); //0�?19之内的随机数
		if(type<7){
			return new Airplane();
		}else if(type<14){
			return new BigAirplane();
		}else{
			return new Bee();
		}
	}
	
	int enterIndex = 0; //敌人入场计数
	/** 敌人(小敌�?+大敌�?+小蜜�?)入场 */
	public void enterAction(){ //10毫秒走一�?
		enterIndex++; //�?10毫秒�?1
		if(enterIndex%40==0){ //�?400(10*40)毫秒走一�?
			FlyingObject obj = nextOne(); //获取敌人对象
			enemies = Arrays.copyOf(enemies,enemies.length+1); //扩容
			enemies[enemies.length-1] = obj; //将敌人对象赋值给enemies的最后一个元�?
		}
	}
	
	/** 飞行物移�? */
	public void stepAction(){ //10毫秒走一�?
		sky.step(); //天空移动
		for(int i=0;i<enemies.length;i++){ //遍历�?有敌�?
			enemies[i].step(); //敌人移动
		}
		for(int i=0;i<bullets.length;i++){ //遍历
			bullets[i].step(); //子弹移动
		}
	}
	
	int shootIndex = 0; //发射子弹计数
	/** 子弹入场(英雄机发射子�?) */
	public void shootAction(){ //10毫秒走一�?
		shootIndex++; //�?10毫秒�?1
		if(shootIndex%30==0){ //�?300(10*30)毫秒走一�?
			Bullet[] bs = hero.shoot(); //获取英雄机发射的子弹对象
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length); //扩容(bs有几个就扩大几个容量)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length); //数组的追�?
		}
	}
	
	/** 删除越界的飞行物(敌人和子�?) */
	public void outOfBoundsAction(){ //10毫秒走一�?
		int index = 0; //1)不越界敌人数组下�?  2)不越界敌人个�?
		FlyingObject[] enemyLives = new FlyingObject[enemies.length]; //不越界敌人数�?
		for(int i=0;i<enemies.length;i++){ //遍历�?有敌�?
			FlyingObject f = enemies[i]; //获取每一个敌�?
			if(!f.outOfBounds() && !f.isRemove()){ //不越�?
				enemyLives[index] = f; //将不越界敌人对象添加到不越界敌人数组�?
				index++; //1)不越界敌人数组下标增�? 2)不越界敌人个数增�?
			}
		}
		enemies = Arrays.copyOf(enemyLives,index); //将不越界敌人数组复制到enemies中，index有几个，则enemies的长度就为几
		
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length]; //不越界子弹数�?
		for(int i=0;i<bullets.length;i++){ //遍历�?有子�?
			Bullet b = bullets[i]; //获取每一个子�?
			if(!b.outOfBounds() && !b.isRemove()){ //不越�?
				bulletLives[index] = b; //将不越界子弹对象添加到不越界子弹数组�?
				index++; //1)不越界子弹数组下标增�? 2)不越界子弹个数增�?
			}
		}
		bullets = Arrays.copyOf(bulletLives,index); //将不越界子弹数组复制到bullets中，index有几个，则bullets的长度就为几
		
	}
	
	int score = 0; //玩家得分
	/** 子弹与敌人的碰撞 */
	public void bulletBangAction(){ //10毫秒走一�?
		for(int i=0;i<bullets.length;i++){ //遍历�?有子�?
			Bullet b = bullets[i]; //获取每一个子�?
			for(int j=0;j<enemies.length;j++){ //遍历�?有敌�?
				FlyingObject f = enemies[j]; //获取每一个敌�?
				if(b.isLife() && f.isLife() && f.hit(b)){ //撞上�?
					b.goDead(); //子弹去死
					f.goDead(); //敌人去死
					if(f instanceof Enemy){ //若被撞对象是敌人
						Enemy e = (Enemy)f; //将被撞对象强转为敌人
						score += e.getScore(); //玩家得分
					}
					if(f instanceof Award){ //若被撞对象是奖励
						Award a = (Award)f; //将被撞对象强转为奖励
						int type = a.getType(); //获取奖励类型
						switch(type){ //根据奖励的不同让英雄机获取不同的奖励
						case Award.DOUBLE_FIRE: //若奖励类型为火力
							hero.addDoubleFire(); //英雄机增火力
							break;
						case Award.LIFE: //若奖励类型为�?
							hero.addLife(); //英雄机增�?
							break;
						}
					}
				}
			}
			
		}
	}
	
	/** 英雄机与敌人的碰�? */
	public void heroBangAction(){ //10毫秒走一�?
		for(int i=0;i<enemies.length;i++){ //遍历�?有敌�?
			FlyingObject f = enemies[i]; //获取每一个敌�?
			if(f.isLife() && f.hit(hero)){ //撞上�?
				f.goDead(); //敌人去死
				hero.subtractLife(); //英雄机减�?
				hero.clearDoubleFire(); //英雄机清空火力�??
			}
		}
	}
	
	/** �?测游戏结�? */
	public void checkGameOverAction(){ //10毫秒走一�?
		if(hero.getLife()<=0){ //游戏结束�?
			state=GAME_OVER;   //修改当前状�?�为游戏结束状�??
		}
	}
	
	/** 程序启动执行 */
	public void action(){
		//创建侦听器对�?
		MouseAdapter l = new MouseAdapter(){
			/** 重写mouseMoved()鼠标移动事件 */
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){ //运行状�?�时执行
					int x = e.getX(); //获取鼠标的x坐标
					int y = e.getY(); //获取鼠标的y坐标
					hero.moveTo(x, y); //英雄机随�?鼠标移动
				}
			}
			/** 重写mouseClicked()鼠标点击事件 */
			public void mouseClicked(MouseEvent e){
				switch(state){ //根据当前状�?�做不同处理
				case START:        //启动状�?�时
					state=RUNNING; //修改为运行状�?
					break;
				case GAME_OVER:  //游戏结束状�?�时
					score = 0;   //清理现场
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state=START; //修改为启动状�?
					break;
				}
			}
			/** 重写mouseExited()鼠标移出事件 */
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){ //运行状�?�时
					state=PAUSE;    //修改为暂停状�?
				}
			}
			/** 重写mouseEntered()鼠标移入事件 */
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){  //暂停状�?�时
					state=RUNNING; //修改为运行状�?
				}
			}
		};
		this.addMouseListener(l); //处理鼠标操作事件
		this.addMouseMotionListener(l); //处理鼠标滑动事件
		
		Timer timer = new Timer(); //创建定时器对�?
		timer.schedule(new TimerTask(){
			public void run(){ //�?10个毫秒走�?�?--定时干的那个�?
				if(state==RUNNING){ //运行状�?�时执行
					enterAction(); //敌人(小敌�?+大敌�?+小蜜�?)入场
					stepAction();  //飞行物移�?
					shootAction(); //子弹入场(英雄机发射子�?)
					outOfBoundsAction(); //删除越界的飞行物
					bulletBangAction(); //子弹与敌人的碰撞
					heroBangAction();   //英雄机与敌人的碰�?
					checkGameOverAction(); //�?测游戏结�?
				}
				repaint();     //重画(重新调用paint()方法)
			}
		},10,10); //定时计划
	}
	
	/** 重写paint()�? */
	public void paint(Graphics g){ //10毫秒走一�?
		sky.paintObject(g);  //画天空对�?
		hero.paintObject(g); //画英雄机对象
		for(int i=0;i<enemies.length;i++){ //遍历�?有敌�?
			enemies[i].paintObject(g); //画敌人对�?
		}
		for(int i=0;i<bullets.length;i++){ //遍历子弹对象
			bullets[i].paintObject(g); //画子弹对�?
		}
		g.drawString("SCORE:"+score,10,25); //画分
		g.drawString("LIFE:"+hero.getLife(),10,45); //画命
		
		switch(state){ //不同状�?�下画不同的�?
		case START: //启动状�?�时画启动图
			g.drawImage(start,0,0,null);
			break;
		case PAUSE: //暂停状�?�时画暂停图
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER: //游戏结束状�?�时画游戏结束图
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame(); //创建�?个窗口对�?
		World world = new World(); //创建�?个面板对�?
		frame.add(world); //将面板添加到窗口�?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置关闭窗口时�??出程�?
		frame.setSize(WIDTH,HEIGHT); //设置窗口的大�?
		frame.setLocationRelativeTo(null); //设置窗口居中显示 
		frame.setVisible(true); //1)设置窗口可见  2)尽快调用paint()
		
		world.action(); //启动程序的执�?
	}
	
}
















