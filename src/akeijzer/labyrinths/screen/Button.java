package akeijzer.labyrinths.screen;

import akeijzer.labyrinths.maths.Rectangle;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class Button extends Rectangle
{
    protected GameView view;
    private Paint paint;
    
    public Button(int posX, int posY, int sizeX, int sizeY, GameView view)
    {
        super(posX, posY, sizeX, sizeY);
        this.view = view;
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }
    
    public abstract void doAction();
    
    public abstract void render(Canvas canvas);
}
