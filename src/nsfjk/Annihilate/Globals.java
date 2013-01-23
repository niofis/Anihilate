package nsfjk.Annihilate;

import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Globals {
	public static int ScreenWidth=1;
	public static int ScreenHeight=1;
	public static Paint whitePaint;
	public static Paint blackPaint;
	public static Paint forePaint;
	public static Paint backPaint;
	
	public static void init(){
		whitePaint=new Paint();
		blackPaint=new Paint();
		
		blackPaint.setARGB(255,0,0,0);
		blackPaint.setStyle(Style.FILL_AND_STROKE);
		
		whitePaint.setARGB(255,255,255,255);
		whitePaint.setStyle(Style.FILL_AND_STROKE);
		
		backPaint=blackPaint;
		forePaint=whitePaint;
	}
}
