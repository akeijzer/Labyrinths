package akeijzer.labyrinths.game.level;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.view.GameView;

public abstract class Level
{
    protected World world;
    protected GameView view;

    public int width;
    public int height;

    public Level(World world)
    {
        this.world = world;
        this.view = world.view;
        this.width = view.getWidth();
        this.height = view.getHeight();
    }

    abstract void loadLevel();
}
