package akeijzer.labyrinths.object;

import akeijzer.labyrinths.game.GameThread;
import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.lib.Resources;
import akeijzer.labyrinths.maths.Circle;
import akeijzer.labyrinths.physics.Physics;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball
{
    public int density;
    private Bitmap icon;
    private Paint paint;
    public float mass;
    private int radius;
    public Circle bounds;
    public float velocityX;
    public float velocityY;
    public int posX, posY;
    private World world;
    private boolean kill = false;
    
    public Circle nextPos;

    public Ball(int posX, int posY, int radius, float mass, World world)
    {
        this.posX = posX;
        this.posY = posY;
        this.world = world;
        this.mass = mass;
        this.radius = radius;
        bounds = new Circle(posX, posY, radius);
        nextPos = new Circle(posX, posY, radius);

        this.icon = Bitmap.createScaledBitmap(Resources.ball, radius * 2, radius * 2, false);
        density = world.view.getResources().getDisplayMetrics().densityDpi * 4;
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(icon, posX - icon.getWidth()/2, posY -icon.getHeight()/2, null);
        //canvas.drawCircle(posX, posY, radius, paint);
    }

    public void update()
    {
        // If ball was giving the kill command remove it
        if (kill)
        {
            world.iBalls.remove();
        }
        
        //Update velocity from orientation of the device
        velocityX += (Physics.calculateAcceleration(world.orientation[2]) * GameThread.SECONDS_PER_FRAME) * density;
        velocityY += (Physics.calculateAcceleration(-world.orientation[1]) * GameThread.SECONDS_PER_FRAME) * density;
        
        //Set the next position of the ball to the nextPos Circle
        nextPos.center.x = posX + (int) (velocityX * GameThread.SECONDS_PER_FRAME);
        nextPos.center.y = posY + (int) (velocityY * GameThread.SECONDS_PER_FRAME);
        
        //Keep checking for collisions until there's no collision anymore or the max checks is reached
        int checks = 0;
        while (world.checkCollisions(this, nextPos) && checks < 4)
        {
            checks++;
            System.out.println("Checks: " + checks);
        }
        
        //Set the position to the new position
        posX = (int) nextPos.center.x;
        posY = (int) nextPos.center.y;
        
        //If the ball manages to get of the screen, bring it back and set velocity for that dimension to 0
        if (posX - radius <= 0)
        {
            posX = 15 + radius;
            velocityX = 0;
        }
        if (posX + radius >= world.view.getWidth())
        {
            posX = world.view.getWidth() - (15 + radius);
            velocityX = 0;
        }
        if (posY - radius <= 0)
        {
            posY = 15 + radius;
            velocityY = 0;
        }
        if (posY + radius >= world.view.getHeight())
        {
            posY = world.view.getHeight() - (15 + radius);
            velocityY = 0;
        }
        
        //Update the bounds of the ball to the new position
        bounds.center.set(posX, posY);
        bounds.radius = radius;
    }
    
    public void setRadius(double scale)
    {
        this.radius *= scale;
        this.icon = Bitmap.createScaledBitmap(Resources.ball, radius * 2, radius * 2, false);
    }
    
    public int getRadius()
    {
        return radius;
    }

    /**
     * Actions when colliding
     */
    public void onCollide()
    {
        playSound(getSoundPitch() + 0.5F);
    }
    
    /**
     * Gets the pitch of the collision sound(higher when moving faster)
     * @return pitch
     */
    public float getSoundPitch()
    {
        return world.view.playSounds ? (Math.abs(velocityX) + Math.abs(velocityY)) / (density * 25) : 0;
    }
    
    /**
     * Plays collision sound
     * @param pitch
     */
    public void playSound(float pitch)
    {
        world.view.playSound(world.view.tickSoundId, pitch);
    }
    
    /**
     * Removes the ball at next update
     */
    public void kill()
    {
        kill = true;
    }
}
