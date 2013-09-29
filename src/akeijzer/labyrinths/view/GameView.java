package akeijzer.labyrinths.view;

import java.util.ArrayList;

import akeijzer.labyrinths.Game;
import akeijzer.labyrinths.game.GameThread;
import akeijzer.labyrinths.maths.Circle;
import akeijzer.labyrinths.maths.Vector2;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.Wall;
import akeijzer.labyrinths.physics.CollisionEffects;
import akeijzer.labyrinths.physics.Intersection;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Callback
{
    private Game game;
    private SurfaceHolder holder;
    private GameThread thread;

    public float orientation[] = new float[3];

    private ArrayList<Ball> balls;
    public ArrayList<Wall> walls;

    public GameView(Context context)
    {
        super(context);
        game = (Game) context;
        holder = getHolder();
        holder.addCallback(this);

        thread = new GameThread(holder, this);
        walls = new ArrayList<Wall>();
        balls = new ArrayList<Ball>();
        balls.add(new Ball(200, 200, 40, 0.2F, this));
        balls.add(new Ball(200, 400, 40, 0.002F, this));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread.setRunning(true);
        thread.start();

        walls.add(new Wall(300, 800, 300, 50, this));
        walls.add(new Wall(300, 950, 50, 300, this));

        walls.add(new Wall(getWidth() / 2 - 15, getHeight() / 2 - 50, 50, getHeight() - 100, this));

        walls.add(new Wall(getWidth() / 2, 5, getWidth(), 10, this));
        walls.add(new Wall(getWidth() / 2, getHeight() - 5, getWidth(), 10, this));

        walls.add(new Wall(5, getHeight() / 2, 10, getHeight(), this));
        walls.add(new Wall(getWidth() - 5, getHeight() / 2, 10, getHeight(), this));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.d("GameView", "Surface is being destroyed");
        thread.setRunning(false);
        while (true)
        {
            try
            {
                thread.join();
                break;
            }
            catch (InterruptedException e)
            {
            }
        }
        Log.d("GameView", "Surface is destroyed");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    public void render(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        for (Wall wall : walls)
        {
            wall.draw(canvas);
        }

        for (Ball ball : balls)
        {
            ball.draw(canvas);
        }
    }

    public void update()
    {
        orientation = game.orientation.getOrientation();
        for (Ball ball : balls)
        {
            ball.update();
        }
        for (Ball ball : balls)
        {
            for (Ball ball2 : balls)
            {
                if (ball2 != ball)
                {
                    CollisionEffects.circleEffect(ball, ball2);
                }
            }
        }
    }

    public boolean checkCollisions(Ball ball, Circle nextPos)
    {
        boolean collision = false;
        for (Wall wall : walls)
        {
            Intersection inter = CollisionEffects.handleIntersection(wall.bounds, new Vector2(ball.posX, ball.posY), nextPos.center, ball.radius);
            if (inter != null)
            {
                float remainingTime = 1.0f - inter.time;
                float dx = nextPos.center.x - ball.posX;
                float dy = nextPos.center.y - ball.posY;
                float dot = dx * inter.nx + dy * inter.ny;
                float ndx = dx - 2 * dot * inter.nx;
                float ndy = dy - 2 * dot * inter.ny;
                float newx = inter.cx + ndx * remainingTime;
                float newy = inter.cy + ndy * remainingTime;

                nextPos.center.x = (int) newx;
                nextPos.center.y = (int) newy;

                ball.velocityX = ndx * GameThread.MAX_FPS;
                ball.velocityY = ndy * GameThread.MAX_FPS;
                collision = true;
            }
        }
        if (collision) return true;
        else return false;
    }

}
