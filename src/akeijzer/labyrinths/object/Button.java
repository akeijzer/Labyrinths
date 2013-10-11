package akeijzer.labyrinths.object;

import akeijzer.labyrinths.maths.Side;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Button extends GameRectCollidable
{
    public Paint paint;
    private boolean isPushed = false;
    private Side connectedSide;
    private Door door;

    public Button(int posX, int posY, Side connectedSide, Door door, GameView view)
    {
        super(posX, posY, 80, 80, view);
        this.connectedSide = connectedSide;
        this.door = door;
        paint = new Paint();
        paint.setColor(Color.YELLOW);
    }

    @Override
    public void onCollide()
    {
        if (!isPushed)
        {
            if (connectedSide == Side.LEFT)
            {
                posX = posX - sizeX / 4;
                sizeX = sizeX / 2;
            }
            else if (connectedSide == Side.RIGHT)
            {
                posX = posX - sizeX / 4;
                sizeX = sizeX / 2;
            }
            else if (connectedSide == Side.BOTTOM)
            {
                posY = posY + sizeY / 4;
                sizeY = sizeY / 2;
            }
            else if (connectedSide == Side.TOP)
            {
                posY = posY - sizeY / 4;
                sizeY = sizeY / 2;
            }
            updateBounds();
            door.kill();
            isPushed = true;
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawRect(posX - sizeX / 2, posY + sizeY / 2, posX + sizeX / 2, posY - sizeY / 2, paint);
    }

    @Override
    public void update()
    {
        super.update();
    }
}
