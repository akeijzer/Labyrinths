package akeijzer.labyrinths.game.level;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.maths.Side;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.Button;
import akeijzer.labyrinths.object.Door;
import akeijzer.labyrinths.object.EndPoint;
import akeijzer.labyrinths.object.Wall;
import akeijzer.labyrinths.object.upgrade.UpgradeDecreaseSize;
import akeijzer.labyrinths.object.upgrade.UpgradeIncreaseSize;

public class Level1 extends Level
{
    public Level1(World world)
    {
        super(world);
    }

    @Override
    void loadLevel()
    {
        world.balls.add(new Ball(200, 200, 40, 0.2F, view));
        world.balls.add(new Ball(200, 400, 40, 0.002F, view));

        /*
         * Screen Walls
         */
        world.collidables.add(new Wall(width / 2, 5, width, 10, view));
        world.collidables.add(new Wall(width / 2, height - 5, width, 10, view));
        world.collidables.add(new Wall(5, height / 2, 10, height, view));
        world.collidables.add(new Wall(width - 5, height / 2, 10, height, view));

        world.collidables.add(new Wall(width / 2 - 10, height / 2 + 100, 50, height - 200, view));

        Door door = new Door(width / 2 - 10, 100, 50, 200, view);
        world.collidables.add(door);
        world.collidables.add(new Button(100, height - 50, Side.BOTTOM, door, view));

        world.endPoints.add(new EndPoint(width - 100, height - 100, 200, 200, view));

        world.upgrades.add(new UpgradeIncreaseSize(500, 500, view));
        world.upgrades.add(new UpgradeDecreaseSize(500, 1000, view));
    }
}
