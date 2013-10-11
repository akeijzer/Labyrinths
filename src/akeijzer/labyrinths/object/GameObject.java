package akeijzer.labyrinths.object;

import akeijzer.labyrinths.view.GameView;
import android.graphics.Canvas;

public abstract class GameObject
{
    public int posX, posY;
    protected GameView view;
    private boolean kill = false;

    public GameObject(int posX, int posY, GameView view)
    {
        this.posX = posX;
        this.posY = posY;
        this.view = view;
    }

    public abstract void draw(Canvas canvas);

    public void update()
    {
        if (kill)
        {
            view.iObjects.remove();
        }
    }
    
    public void kill()
    {
        kill = true;
    }
}
