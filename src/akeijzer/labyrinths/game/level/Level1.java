package akeijzer.labyrinths.game.level;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.EndPoint;
import akeijzer.labyrinths.object.Wall;

public class Level1
{
    public static void loadLevel(World world)
    {
        int width = world.view.getWidth();
        int height = world.view.getHeight();
        
        world.balls.add(new Ball(20 + 15, 20 + 15, 40, 0.002F, world));
        
        world.objects.add(new Wall(125 + 10, height / 2 - 50, 50, height - 120, world));
        world.objects.add(new Wall(width/2, height - 125 - 10, width - 2*135 - 50, 50, world));
        world.objects.add(new Wall(width - (125 + 10), height / 2, 50, height - 220, world));
        
        world.objects.add(new EndPoint(width/2, height - 210, width - 160*2, 100, world));
        
        /*
         * Screen Walls
         */
        world.objects.add(new Wall(width / 2, 5, width, 10, world));
        world.objects.add(new Wall(width / 2, height - 5, width, 10, world));
        world.objects.add(new Wall(5, height / 2, 10, height, world));
        world.objects.add(new Wall(width - 5, height / 2, 10, height, world));
    }
}
