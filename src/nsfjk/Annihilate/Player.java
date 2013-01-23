package nsfjk.Annihilate;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Player implements GameElement {
	
	public float posX;
	public float posY;
	private BulletManager bullets;
	private int acc_time=0;
	
	public int lives=3;
	public int bombs=3;
	public float shell=100.0f;
	
	public Player(BulletManager bullets){
		this.bullets=bullets;
	}
	
	public void setPosition(float x, float y)
	{
		posX=x;
		posY=y;
	}
	
	public void draw(Canvas canvas){
		canvas.drawCircle(posX, posY, 20.0f, Globals.forePaint);
	}
	
	public void shoot(BulletManager bullets){
		bullets.addBulletPlayer(posX+10, posY, 15, 0);
	}
	public void tick(int elapsed){
		acc_time+=elapsed;
		if(acc_time>5){
			shoot(bullets);
			acc_time=0;
		}
	}

}
