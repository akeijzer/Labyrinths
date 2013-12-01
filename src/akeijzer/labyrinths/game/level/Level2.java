package akeijzer.labyrinths.game.level;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.maths.Side;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.Button;
import akeijzer.labyrinths.object.DeathPoint;
import akeijzer.labyrinths.object.Door;
import akeijzer.labyrinths.object.EndPoint;
import akeijzer.labyrinths.object.Wall;
import akeijzer.labyrinths.object.upgrade.UpgradeDecreaseSize;
import akeijzer.labyrinths.object.upgrade.UpgradeIncreaseSize;

public class Level2
{
    public static void loadLevel(World world)
    {
        int width = world.view.getWidth();
        int height = world.view.getHeight();
        
        world.balls.add(new Ball(200, 400, 40, 0.002F, world));

        world.objects.add(new Wall(width / 2 - 10, height / 2 + 100, 50, height - 200, world));

        Door door = new Door(width / 2 - 10, 100, 50, 200, world);
        world.objects.add(door);
        world.objects.add(new Button(100, height - 30, Side.BOTTOM, door, world));

        world.objects.add(new EndPoint(width - 100, height - 100, 200, 200, world));

        world.objects.add(new UpgradeIncreaseSize(500, 500, world));
        world.objects.add(new UpgradeDecreaseSize(500, 1000, world));

        world.objects.add(new DeathPoint(width/2 - 80, 400, 90, 400, world));
        

        /*
         * Screen Walls
         */
        world.objects.add(new Wall(width / 2, 5, width, 10, world));
        world.objects.add(new Wall(width / 2, height - 5, width, 10, world));
        world.objects.add(new Wall(5, height / 2, 10, height, world));
        world.objects.add(new Wall(width - 5, height / 2, 10, height, world));
    }
}
