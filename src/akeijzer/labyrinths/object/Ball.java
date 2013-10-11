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

public class Ball
{
    public int density;
    private Bitmap icon;
    private Paint paint;
    public float mass;
    public int radius;
    public Circle bounds;
    public float velocityX;
    public float velocityY;
    public int posX, posY;
    private GameView view;
    private boolean kill = false;

    public int nextPosX;
    public int nextPosY;
    public Circle nextPos;

    public Ball(int posX, int posY, int radius, float mass, GameView view)
    {
        this.posX = posX;
        this.posY = posY;
        this.view = view;
        this.mass = mass;
        this.radius = radius;
        bounds = new Circle(posX, posY, (float) radius);
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

    public void update()
    {
        if (kill)
        {
            view.iBalls.remove();
        }
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
            velocityX = 0;
        }
        if (posX + radius >= view.getWidth())
        {
            posX = view.getWidth() - (15 + radius);
            velocityX = 0;
        }
        if (posY - radius <= 0)
        {
            posY = 15 + radius;
            velocityY = 0;
        }
        if (posY + radius >= view.getHeight())
        {
            posY = view.getHeight() - (15 + radius);
            velocityY = 0;
        }

        bounds.center.set(posX, posY);
        bounds.radius = radius;
    }

    public void onCollide()
    {
        playSound(getSoundPitch() + 0.5F);
    }

    public float getSoundPitch()
    {
        return view.playSounds ? (Math.abs(velocityX) + Math.abs(velocityY)) / (density * 25) : 0;
    }

    public void playSound(float pitch)
    {
        view.playSound(view.tickSoundId, pitch);
    }

    public void kill()
    {
        kill = true;
    }
}
