package akeijzer.labyrinths.screen;

import akeijzer.labyrinths.game.GameState;
import akeijzer.labyrinths.lib.Resources;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class DoneButton extends Button
{
    private Bitmap icon;
    
    public DoneButton(int posX, int posY, int sizeX, int sizeY, GameView view)
    {
        super(posX, posY, sizeX, sizeY, view);
        this.icon = Bitmap.createScaledBitmap(Resources.done, sizeX, sizeY, false);
    }

    @Override
    public void doAction()
    {
        view.setGameState(GameState.STARTSCREEN);
    }

    @Override
    public void render(Canvas canvas)
    {
        canvas.drawBitmap(icon, pos.x - width/2, pos.y - height/2, null);
    }
    
    

}
