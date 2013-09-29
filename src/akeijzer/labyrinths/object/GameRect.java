package akeijzer.labyrinths.object;

import akeijzer.labyrinths.maths.Rectangle;
import akeijzer.labyrinths.view.GameView;

public class GameRect extends GameObject
{
    public int sizeX, sizeY;
    public Rectangle bounds;

    public GameRect(int posX, int posY, int sizeX, int sizeY, GameView view)
    {
        super(posX, posY, view);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        bounds = new Rectangle(posX, posY, sizeX, sizeY);
    }

}
