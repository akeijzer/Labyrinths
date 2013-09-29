package akeijzer.labyrinths.object;

import akeijzer.labyrinths.R;
import akeijzer.labyrinths.game.GameThread;
import akeijzer.labyrinths.maths.Circle;
import akeijzer.labyrinths.physics.Physics;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball extends GameCircle
{
    public Bitmap icon;
    public Paint paint;
    public float mass;
    public float velocityX;
    public float velocityY;

    public int nextPosX;
    public int nextPosY;
    public Circle nextPos;

    public Ball(int posX, int posY, int radius, float mass, GameView view)
    {
        super(posX, posY, radius, view);
        this.mass = mass;
        posX = posY = 200;
        nextPos = new Circle(posX, posY, radius);

        this.icon = BitmapFactory.decodeResource(view.getResources(), R.drawable.options);
        this.icon = Bitmap.createScaledBitmap(icon, radius * 2, radius * 2, false);
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    public void draw(Canvas canvas)
    {
        // canvas.drawBitmap(icon, posX - icon.getWidth()/2, posY -
        // icon.getHeight()/2, null);
        canvas.drawCircle(posX, posY, radius, paint);
    }

    @Override
    public void update()
    {
        velocityX += (Physics.calculateAcceleration(view.orientation[2]) * GameThread.SECONDS_PER_FRAME) * 126;
        velocityY += (Physics.calculateAcceleration(-view.orientation[1]) * GameThread.SECONDS_PER_FRAME) * 126;

        nextPosX = posX + (int) (velocityX * GameThread.SECONDS_PER_FRAME);
        nextPosY = posY + (int) (velocityY * GameThread.SECONDS_PER_FRAME);

        nextPos.center.x = nextPosX;
        nextPos.center.y = nextPosY;
        boolean collision = view.checkCollisions(this, nextPos);

        if (!collision)
        {
            posX += (int) (velocityX * GameThread.SECONDS_PER_FRAME);
            posY += (int) (velocityY * GameThread.SECONDS_PER_FRAME);
        }
        else
        {
            posX = (int) nextPos.center.x;
            posY = (int) nextPos.center.y;
        }

        super.update();
    }
}
