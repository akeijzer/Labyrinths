package akeijzer.labyrinths.object.upgrade;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.maths.OverlapTester;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.GameRect;

public abstract class Upgrade extends GameRect
{
    public Upgrade(int posX, int posY, World world)
    {
        super(posX, posY, 80, 80, world);
    }
    
    public void update()
    {
        for (Ball ball : world.balls)
        {
            //If colliding with ball
            if (OverlapTester.overlapCircleRectangle(ball.bounds, bounds))
            {
                //Collision effect
                onCollision(ball);
                //Remove upgrade from world
                world.iObjects.remove();
            }
        }
    }
    
    public abstract void onCollision(Ball ball);
}
