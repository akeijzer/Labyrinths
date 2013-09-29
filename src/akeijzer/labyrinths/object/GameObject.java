package akeijzer.labyrinths.object;

import akeijzer.labyrinths.view.GameView;

public abstract class GameObject
{
    public int posX, posY;
    protected GameView view;

    public GameObject(int posX, int posY, GameView view)
    {
        this.posX = posX;
        this.posY = posY;
        this.view = view;
    }
}
