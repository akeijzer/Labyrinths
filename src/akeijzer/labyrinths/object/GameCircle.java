package akeijzer.labyrinths.object;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.maths.Circle;

/**
 * Most basic circular object, will not be collided with
 * 
 * @author Alexander
 *
 */
public abstract class GameCircle extends GameObject
{
    public int radius;
    public Circle bounds;

    public GameCircle(int posX, int posY, int radius, World world)
    {
        super(posX, posY, world);
        this.radius = radius;
        bounds = new Circle(posX, posY, (float) radius);
    }

}
