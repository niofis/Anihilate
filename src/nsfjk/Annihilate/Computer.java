package nsfjk.Annihilate;

import java.util.Random;

import nsfjk.Annihilate.BulletManager.Bullet;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Computer implements GameElement {
	public class Enemy{
		public float posX;
		public float posY;
		public float dx;
		public float dy;
		public float life=10;
		public boolean enabled=false;
		public int acc_time=0;
		
		public void shoot(BulletManager bullets){
			bullets.addBulletEnemy(posX, posY, dx*2, 0);
		}
	}
	
	public static final int MAX_ENEMIES = 100;
	
	private BulletManager bullets;
	public Enemy[] enemies;
	
	Random random;

	public Computer(BulletManager bullets){
		this.bullets=bullets;
		enemies=new Enemy[MAX_ENEMIES];
		for(int x=0;x<MAX_ENEMIES;++x){
			enemies[x]=new Enemy();
		}
		random=new Random();
	}

	
	public void draw(Canvas canvas){
		for(int x=0;x<MAX_ENEMIES;++x){
			Enemy e = enemies[x];
			if(e.enabled){
				canvas.drawRect(e.posX-10, e.posY-10, e.posX+10, e.posY+10, Globals.forePaint);
			}
		}
	}
	
	public void addEnemy(){
		for(int x=0;x<MAX_ENEMIES;++x){
			Enemy e = enemies[x];
			if(!e.enabled){
				e.enabled=true;
				e.posY=random.nextInt(Globals.ScreenHeight);
				e.posX=Globals.ScreenWidth;
				e.dy=0;
				e.dx=-3;
				break;
			}
		}
	}
	
	public void tick(int elapsed){
		for (int x = 0; x < MAX_ENEMIES; ++x) {
			Enemy e = enemies[x];
			if(e.enabled){
				e.posX+=e.dx;
				e.posY+=e.dy;
				
				e.acc_time+=elapsed;
				if(e.acc_time>50){
					e.shoot(bullets);
					e.acc_time=0;
				}
				
				if(e.posX<0)
					e.enabled=false;
				else if(e.posX>Globals.ScreenWidth)
					e.enabled=false;
				else if(e.posY<0)
					e.enabled=false;
				else if(e.posY>Globals.ScreenHeight)
					e.enabled=false;


			}
		}
	}

}
