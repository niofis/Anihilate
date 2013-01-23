package nsfjk.Annihilate;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BulletManager implements GameElement {

	public static final int MAX_BULLETS_PLAYER = 512;
	public static final int MAX_BULLETS_ENEMY = 1024;
	
	public static final int BULLET_PLAYER = 1;
	public static final int BULLET_ENEMY = 2;

	private int nums_player = 0;

	class Bullet {
		public float posX;
		public float posY;
		public int type;
		public float dx = 0;
		public float dy = 0;
		public boolean enabled = false;
	}

	public Bullet[] bullets_player;
	public Bullet[] bullets_enemy;
	
	public BulletManager() {
		bullets_player = new Bullet[MAX_BULLETS_PLAYER];
		for (int x = 0; x < MAX_BULLETS_PLAYER; ++x) {
			bullets_player[x] = new Bullet();
		}
		
		bullets_enemy = new Bullet[MAX_BULLETS_ENEMY];
		for (int x = 0; x < MAX_BULLETS_ENEMY; ++x) {
			bullets_enemy[x] = new Bullet();
		}
	}
	
	public void draw(Canvas canvas) {
		for (int x = 0; x < MAX_BULLETS_PLAYER; ++x) {
			Bullet b = bullets_player[x];
			if(b.enabled){
				canvas.drawCircle(b.posX, b.posY, 3, Globals.forePaint);
			}
		}
		for (int x = 0; x < MAX_BULLETS_ENEMY; ++x) {
			Bullet b = bullets_enemy[x];
			if(b.enabled){
				canvas.drawRect(b.posX-3, b.posY-3, b.posX+3, b.posY+3, Globals.forePaint);
			}
		}
	}

	public void addBulletPlayer(float posX, float posY, float dx, float dy) {
		for (int x = 0; x < MAX_BULLETS_PLAYER; ++x) {
			Bullet b = bullets_player[x];
			if (!b.enabled) {
				b.posX = posX;
				b.posY = posY;
				b.dx = dx;
				b.dy = dy;
				b.enabled=true;
				break;
			}
		}
	}
	
	public void addBulletEnemy(float posX, float posY, float dx, float dy) {
		for (int x = 0; x < MAX_BULLETS_ENEMY; ++x) {
			Bullet b = bullets_enemy[x];
			if (!b.enabled) {
				b.posX = posX;
				b.posY = posY;
				b.dx = dx;
				b.dy = dy;
				b.enabled=true;
				break;
			}
		}
	}
	
	public void tick(int elapsed){
		for (int x = 0; x < MAX_BULLETS_PLAYER; ++x) {
			Bullet b = bullets_player[x];
			if(b.enabled){
				b.posX+=b.dx;
				b.posY+=b.dy;
				
				if(b.posX<0)
					b.enabled=false;
				else if(b.posX>Globals.ScreenWidth)
					b.enabled=false;
				else if(b.posY<0)
					b.enabled=false;
				else if(b.posY>Globals.ScreenHeight)
					b.enabled=false;
			}
		}
		
		for (int x = 0; x < MAX_BULLETS_ENEMY; ++x) {
			Bullet b = bullets_enemy[x];
			if(b.enabled){
				b.posX+=b.dx;
				b.posY+=b.dy;
				
				if(b.posX<0)
					b.enabled=false;
				else if(b.posX>Globals.ScreenWidth)
					b.enabled=false;
				else if(b.posY<0)
					b.enabled=false;
				else if(b.posY>Globals.ScreenHeight)
					b.enabled=false;
			}
		}
	}
}
