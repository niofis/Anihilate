package nsfjk.Annihilate;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class AnnihilateView extends android.view.SurfaceView implements
		SurfaceHolder.Callback  {

	class AnnihilateThread extends Thread {

		private SurfaceHolder mSurfaceHolder;
		private Context mContext;
		private long mLastTime;
		private boolean mRun;
		private Random mRandom;
		
		Rect wholeScreen;
		Paint backPaint;
		Paint forePaint;
		
		private Player player;
		private BulletManager bullets;
		private Computer cpu;
		private HUD hud;
		
		public AnnihilateThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {

		}

		public AnnihilateThread(SurfaceHolder holder, Context context) {
			// get handles to some important objects
			mSurfaceHolder = holder;
			mContext = context;
		}

		public void doStart() {
			synchronized (mSurfaceHolder) {
				mLastTime = System.currentTimeMillis();
				mRun = true;

				mRandom = new Random();
				
				backPaint = new Paint();
				forePaint = new Paint();
				
				backPaint.setARGB(255,0,0,0);
				backPaint.setStyle(Style.FILL_AND_STROKE);
				
				forePaint.setARGB(255,255,255,255);
				forePaint.setStyle(Style.FILL_AND_STROKE);
				
				
				bullets=new BulletManager();
				player=new Player(bullets);
				cpu=new Computer(bullets);
				hud=new HUD(player);
			}
		}

		public void pause() {
			mRun=false;
		}

		public void unpause() {

		}

		public synchronized void restoreState(Bundle savedState) {

		}

		@Override
		public void run() {
			int elapsed;
			while (mRun) {
				elapsed=(int)(System.currentTimeMillis()-mLastTime);
				
				Canvas c = null;
				tick(elapsed);
				player.tick(elapsed);
				bullets.tick(elapsed);
				cpu.tick(elapsed);
				try {
					c = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						doDraw(c);
					}
				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
				mLastTime = System.currentTimeMillis();
				try {
					AnnihilateThread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

		public Bundle saveState(Bundle map) {
			return null;

		}

		public void setSurfaceSize(int width, int height) {
			// synchronized to make sure these all change atomically
			synchronized (mSurfaceHolder) {
				wholeScreen = new Rect(0, 0, width, width);
				
				player.setPosition(20, height/2);
				
				Globals.ScreenWidth=width;
				Globals.ScreenHeight=height;
				
				}
		}
		
		private void tick(int elapsed){
			if(mRandom.nextInt(200)<10){
				cpu.addEnemy();
			}
			
			for(int x=0;x<cpu.enemies.length;++x){
				Computer.Enemy e=cpu.enemies[x];
				if(e.enabled){
					for(int y=0;y<bullets.bullets_player.length;++y){
						BulletManager.Bullet b=bullets.bullets_player[y];
						if(b.enabled){
							if(b.posX>(e.posX-10) && b.posX<(e.posX+10) && b.posY>(e.posY-10) && b.posY<(e.posY+10))
							{
								e.enabled=false;
								b.enabled=false;
							}
								
						}
					}
				}
			}
			
			for(int y=0;y<bullets.bullets_enemy.length;++y){
				BulletManager.Bullet b=bullets.bullets_enemy[y];
				if(b.enabled){
					if(b.posX>(player.posX-8) && b.posX<(player.posX+8) && b.posY>(player.posY-8) && b.posY<(player.posY+8))
					{
						player.shell-=10;
						b.enabled=false;
					}
				}
			}
			
			if(player.shell<=0 && player.lives>0){
				player.lives--;
				player.shell=100;
			}
			else if(player.lives<=0)
			{
				//game over
			}
		}

		private void doDraw(Canvas canvas) {

			canvas.drawRect(wholeScreen, backPaint);
			player.draw(canvas);
			bullets.draw(canvas);
			cpu.draw(canvas);
			hud.draw(canvas);
		}

		public void setRunning(boolean b) {
			mRun = b;

		}
		
		public void movePlayer(float dx, float dy){
			player.posX+=dx;
			player.posY+=dy;
			
			if(player.posX<0)
				player.posX=0;
			else if(player.posX>Globals.ScreenWidth)
				player.posX=Globals.ScreenWidth;
			
			if(player.posY<0)
				player.posY=0;
			else if(player.posY>Globals.ScreenHeight)
				player.posY=Globals.ScreenHeight;

			
		}

	}

	private AnnihilateThread thread;

	public AnnihilateView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		// create thread only; it's started in surfaceCreated()
		thread = new AnnihilateThread(holder, context);

		setFocusable(true); // make sure we get key events
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);

	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	public AnnihilateThread getThread() {

		return thread;
	}
	
 

}
