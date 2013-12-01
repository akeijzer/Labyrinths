package akeijzer.labyrinths.object;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.maths.Rectangle;

/**
 * Most basic rectangular object, will not be collided with
 * 
 * @author Alexander
 *
 */
public abstract class GameRect extends GameObject
{
    public int sizeX, sizeY;
    public Rectangle bounds;

    public GameRect(int posX, int posY, int sizeX, int sizeY, World world)
    {
        super(posX, posY, world);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        bounds = new Rectangle(posX, posY, sizeX, sizeY);
    }
    
    /**
     * Updates the collision detection bounds
     */
    public void updateBounds()
    {
        bounds.pos.x = posX;
        bounds.pos.y = posY;
        bounds.width = sizeX;
        bounds.height = sizeY;
    }

}
