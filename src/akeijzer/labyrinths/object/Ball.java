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
	public int density;
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
        nextPos = new Circle(posX, posY, radius);

        this.icon = BitmapFactory.decodeResource(view.getResources(), R.drawable.options);
        this.icon = Bitmap.createScaledBitmap(icon, radius * 2, radius * 2, false);
        density = view.getResources().getDisplayMetrics().densityDpi;
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
        velocityX += (Physics.calculateAcceleration(view.orientation[2]) * GameThread.SECONDS_PER_FRAME) * density;
        velocityY += (Physics.calculateAcceleration(-view.orientation[1]) * GameThread.SECONDS_PER_FRAME) * density;

        nextPosX = posX + (int) (velocityX * GameThread.SECONDS_PER_FRAME);
        nextPosY = posY + (int) (velocityY * GameThread.SECONDS_PER_FRAME);

        nextPos.center.x = nextPosX;
        nextPos.center.y = nextPosY;
        boolean collision = view.world.checkCollisions(view, this, nextPos);

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
        
        if (posX - radius <= 0)
        {
        	posX = 15 + radius;
        }
        if (posX + radius >= view.getWidth())
        {
        	posX = view.getWidth() - (15 + radius);
        }
        if (posY - radius <= 0)
        {
        	posY = 15 + radius;
        }
        if (posY + radius >= view.getHeight())
        {
        	posY = view.getHeight() - (15 + radius);
        }

        super.update();
    }
}
