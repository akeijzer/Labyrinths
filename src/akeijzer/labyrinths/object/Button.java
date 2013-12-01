package akeijzer.labyrinths.object;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.maths.Side;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Button extends GameRectCollidable
{
    public Paint paint;
    private boolean isPushed = false;
    private Side connectedSide;
    private Door door;

    public Button(int posX, int posY, Side connectedSide, Door door, World world)
    {
        super(posX, posY, (connectedSide == Side.LEFT || connectedSide == Side.RIGHT)? 40: 80, (connectedSide == Side.TOP || connectedSide == Side.BOTTOM)? 40: 80, world);
        this.connectedSide = connectedSide;
        this.door = door;
        paint = new Paint();
        paint.setColor(Color.RED);
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
