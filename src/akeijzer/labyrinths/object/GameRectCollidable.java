package akeijzer.labyrinths.object;

import akeijzer.labyrinths.game.World;

/**
 * A rectangular object, will be collided with
 * 
 * @author Alexander
 *
 */
public abstract class GameRectCollidable extends GameRect
{
    public GameRectCollidable(int posX, int posY, int sizeX, int sizeY, World world)
    {
        super(posX, posY, sizeX, sizeY, world);
    }
    
    /**
     * Called when collided with
     */
    public abstract void onCollide();

}
