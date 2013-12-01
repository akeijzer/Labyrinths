package akeijzer.labyrinths.game;

import java.util.ArrayList;
import java.util.Iterator;

import akeijzer.labyrinths.game.level.LevelHandler;
import akeijzer.labyrinths.lib.Resources;
import akeijzer.labyrinths.maths.Circle;
import akeijzer.labyrinths.maths.Side;
import akeijzer.labyrinths.maths.Vector2;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.GameObject;
import akeijzer.labyrinths.object.GameRectCollidable;
import akeijzer.labyrinths.physics.CollisionEffects;
import akeijzer.labyrinths.physics.Intersection;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;

public class World
{
    public ArrayList<Ball> balls;
    public ArrayList<GameObject> objects;
    public GameView view;
    private Bitmap background;
    
    public Iterator<GameObject> iObjects;
    public Iterator<Ball> iBalls;
    
    public long timeInMilliseconds = 0L;
    private long startTimeInMilliseconds = 0L;
    private Paint textPaint;
    
    /**
     * Set true to stop iterating through game objects
     */
    public boolean stopIteration = false;

    public float orientation[] = new float[3];

    public World(GameView view)
    {
        this.view = view;
        balls = new ArrayList<Ball>();
        objects = new ArrayList<GameObject>();
        LevelHandler.init(this);
        this.background = Bitmap.createScaledBitmap(Resources.floor, view.getWidth(), view.getHeight(), false);
        startTimeInMilliseconds = SystemClock.uptimeMillis();
        textPaint = new Paint();
        textPaint.setTextSize(30);
        textPaint.setColor(Color.WHITE);
    }

    /**
     * Passes on render order to game objects
     * 
     * @param canvas
     */
    public void render(Canvas canvas)
    {
        //Draw background
        canvas.drawBitmap(background, 0, 0, null);
        
        //Draw all game objects
        for (GameObject gameObject : objects)
        {
            gameObject.draw(canvas);
        }

        //Draw all balls
        for (Ball ball : balls)
        {
            ball.draw(canvas);
        }
        canvas.drawText(getTime(), 10, 40, textPaint);
    }

    /**
     * Passes on update order to game objects
     */
    public void update()
    {
        //Updates the current device orientation
        orientation = view.game.orientation.getOrientation();
        
        //Iterates through all balls and updates them
        iBalls = balls.iterator();
        while (iBalls.hasNext())
        {
            Ball ball = iBalls.next();
            ball.update();
        }
        iBalls = null;

        //Iterates through all objects and updates them
        iObjects = objects.iterator();
        while (!stopIteration && iObjects.hasNext())
        {
            GameObject gameObject = iObjects.next();
            gameObject.update();
        }
        stopIteration = false;
        iObjects = null;
        
        //Update collisions between balls
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
        
        //If all balls reached the end points, load next level
        if (balls.isEmpty())
        {
            LevelHandler.loadNextLevel();
        }
    }
    
    /**
     * Removes all objects from the World
     */
    public void clearWorld()
    {
        stopIteration = true;
        balls.clear();
        objects.clear();
    }
    
    /**
     * Checks if the balls next position collides with collidable game objects and updates ball position if collided
     * @param ball
     * @param nextPos
     * @return collision
     */
    public boolean checkCollisions(Ball ball, Circle nextPos)
    {
        boolean collision = false;
        //Iterates through all objects
        for (GameObject object : objects)
        {
            // Check if object is collidable
            if (object instanceof GameRectCollidable)
            {
                GameRectCollidable collidable = (GameRectCollidable) object;
                
                //Check for collision
                Intersection inter = CollisionEffects.handleIntersection(collidable.bounds, new Vector2(ball.posX, ball.posY), nextPos.center, ball.getRadius());
                if (inter != null)
                {
                    //Calculate new positions and new velocities
                    float remainingTime = 1.0f - inter.time;
                    float dx = nextPos.center.x - ball.posX;
                    float dy = nextPos.center.y - ball.posY;
                    float dot = dx * inter.nx + dy * inter.ny;
                    float ndx = dx - 2 * dot * inter.nx;
                    float ndy = dy - 2 * dot * inter.ny;
                    float newx = inter.cx + ndx * remainingTime;
                    float newy = inter.cy + ndy * remainingTime;
                    
                    //Set new positions
                    ball.posX = (int) inter.cx;
                    ball.posY = (int) inter.cy;
                    nextPos.center.set(newx, newy);

                    //Set new velocity
                    ball.velocityX = ndx * GameThread.MAX_FPS;
                    ball.velocityY = ndy * GameThread.MAX_FPS;
                    
                    //Remove energy from the dimension the collision happened
                    if (inter.side == Side.LEFT || inter.side == Side.RIGHT)
                    {
                        ball.velocityX *= 0.8F;
                    }
                    else if (inter.side == Side.TOP || inter.side == Side.BOTTOM)
                    {
                        ball.velocityY *= 0.8F;
                    }
                    else
                    {
                        ball.velocityX *= 0.8F;
                        ball.velocityY *= 0.8F;
                    }
                    collision = true;
                    
                    //Tells collided objects they have collided
                    collidable.onCollide();
                    ball.onCollide();
                }
            }
        }
        return collision;
    }
    
    public String getTime()
    {
        timeInMilliseconds = SystemClock.uptimeMillis() - startTimeInMilliseconds;

        int secs = (int) (timeInMilliseconds / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (timeInMilliseconds % 1000);

        return mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds);
    }
}
