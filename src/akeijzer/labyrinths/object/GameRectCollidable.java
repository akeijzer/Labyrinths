package akeijzer.labyrinths.object;

import akeijzer.labyrinths.view.GameView;

public abstract class GameRectCollidable extends GameRect
{
    public GameRectCollidable(int posX, int posY, int sizeX, int sizeY, GameView view)
    {
        super(posX, posY, sizeX, sizeY, view);
    }

    public abstract void onCollide();

}
