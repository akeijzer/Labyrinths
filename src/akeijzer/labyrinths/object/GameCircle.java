package akeijzer.labyrinths.object;

import akeijzer.labyrinths.maths.Circle;
import akeijzer.labyrinths.view.GameView;

public abstract class GameCircle extends GameObject
{
    public int radius;
    public Circle bounds;

    public GameCircle(int posX, int posY, int radius, GameView view)
    {
        super(posX, posY, view);
        this.radius = radius;
        bounds = new Circle(posX, posY, (float) radius);
    }

}
