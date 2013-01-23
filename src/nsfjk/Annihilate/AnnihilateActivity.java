package nsfjk.Annihilate;

import nsfjk.Annihilate.AnnihilateView.AnnihilateThread;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class AnnihilateActivity extends Activity implements OnClickListener {
	private AnnihilateView mAnnihilateView;
	private AnnihilateThread mAnnihilateThread;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Globals.init();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		
		setContentView(R.layout.main);
		
		
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		mAnnihilateView = (AnnihilateView) findViewById(R.id.annihilate);
		mAnnihilateThread = mAnnihilateView.getThread();
		gestureDetector = new GestureDetector(new MyGestureDetector(mAnnihilateThread));
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};
		
		mAnnihilateView.setOnClickListener(AnnihilateActivity.this);  
		mAnnihilateView.setOnTouchListener(gestureListener); 
		
		mAnnihilateThread.doStart();
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        mAnnihilateThread.saveState(outState);
    }
	
	@Override
    protected void onPause() {
        super.onPause();
        mAnnihilateThread.pause(); // pause game when Activity pauses
    }

	public void onClick(View view) {

	}

	class MyGestureDetector extends SimpleOnGestureListener {
		private AnnihilateThread mAnnihilateThread;
		
		public MyGestureDetector(AnnihilateThread thread){
			mAnnihilateThread=thread;
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					//Toast.makeText(SelectFilterActivity.this, "Left Swipe",
					//		Toast.LENGTH_SHORT).show();
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					//Toast.makeText(SelectFilterActivity.this, "Right Swipe",
					//		Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
			//mAnnihilateThread.setPosition(e2.getX(),e2.getY());
			mAnnihilateThread.movePlayer(-distanceX*2f, -distanceY*2f);
			return false;
		}
	

	}

}
