package nsfjk.Annihilate;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

public class HUD {
	private Player player;
	
	public HUD(Player player){
		this.player=player;
	}

	
	public void draw(Canvas canvas){
		canvas.drawRect(0, 0, player.shell, 20, Globals.forePaint);
		for(int x=0;x<player.bombs;++x){
			canvas.drawCircle(110+(x*20), 10, 10, Globals.forePaint);
			canvas.drawCircle(110+(x*20), 10, 5, Globals.backPaint);
		}
		for(int x=1;x<=player.lives;++x){
			canvas.drawCircle(x*20, Globals.ScreenHeight-10, 10, Globals.forePaint);
		}
	}
}
