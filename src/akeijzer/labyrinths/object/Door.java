package akeijzer.labyrinths.object;

import akeijzer.labyrinths.view.GameView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Door extends GameRectCollidable
{
    public Paint paint;

    public Door(int posX, int posY, int sizeX, int sizeY, GameView view)
    {
        super(posX, posY, sizeX, sizeY, view);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void onCollide()
    {
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawRect(posX - sizeX / 2, posY + sizeY / 2, posX + sizeX / 2, posY - sizeY / 2, paint);
    }

}
