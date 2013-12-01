package akeijzer.labyrinths.object;

import akeijzer.labyrinths.game.World;
import android.graphics.Canvas;

/**
 * Most basic in-game object, will not be collided with
 * 
 * @author Alexander
 *
 */
public abstract class GameObject
{
    public int posX, posY;
    protected World world;
    private boolean kill = false;

    public GameObject(int posX, int posY, World world)
    {
        this.posX = posX;
        this.posY = posY;
        this.world = world;
    }
    
    /**
     * Gets called (almost) every tick, used to draw the object on the canvas
     */
    public abstract void draw(Canvas canvas);
    
    /**
     * Gets called every tick, used to update position
     */
    public void update()
    {
        if (kill)
        {
            world.iObjects.remove();
        }
    }
    
    /**
     * Removes the object at next update
     */
    public void kill()
    {
        kill = true;
    }
}
