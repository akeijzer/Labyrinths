package akeijzer.labyrinths.game.level;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.Wall;

public class Level2 extends Level
{

    public Level2(World world)
    {
        super(world);
    }

    @Override
    void loadLevel()
    {
        for (int i = 0; i < 10; i++)
        {
            world.balls.add(new Ball(45 + (i * 80), 5, 40, 0.2F, view));
        }
        for (int i = 0; i < 10; i++)
        {
            world.balls.add(new Ball(45 + (i * 80), 85, 40, 0.2F, view));
        }
        for (int i = 0; i < 10; i++)
        {
            world.balls.add(new Ball(45 + (i * 80), 165, 40, 0.2F, view));
        }

        /*
         * Screen Walls
         */
        world.objects.add(new Wall(width / 2, 5, width, 10, view));
        world.objects.add(new Wall(width / 2, height - 5, width, 10, view));
        world.objects.add(new Wall(5, height / 2, 10, height, view));
        world.objects.add(new Wall(width - 5, height / 2, 10, height, view));
    }

}
