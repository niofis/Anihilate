package nsfjk.Annihilate;
import android.graphics.Canvas;


public interface GameElement {
	public void draw(Canvas canvas);
	public void tick(int elapsed);
}
