package akeijzer.labyrinths.object.upgrade;

import akeijzer.labyrinths.object.GameRect;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Canvas;

public abstract class Upgrade extends GameRect
{

	public Upgrade(int posX, int posY, GameView view)
	{
		super(posX, posY, 80, 80, view);
	}

	public abstract boolean update();
	
	public abstract void draw(Canvas canvas);
	
}
