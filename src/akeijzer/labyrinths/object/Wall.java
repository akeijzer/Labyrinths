package akeijzer.labyrinths.object;

import akeijzer.labyrinths.view.GameView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Wall extends GameRectCollidable
{
    public Paint paint;

    public Wall(int posX, int posY, int sizeX, int sizeY, GameView view)
    {
        super(posX, posY, sizeX, sizeY, view);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawRect(posX - sizeX / 2, posY + sizeY / 2, posX + sizeX / 2, posY - sizeY / 2, paint);
    }

    @Override
    public void onCollide() {}

    @Override
    public void update()
    {
        super.update();
    }

}
