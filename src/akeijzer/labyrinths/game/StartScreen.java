package akeijzer.labyrinths.game;

import java.util.ArrayList;
import java.util.List;

import akeijzer.labyrinths.lib.Resources;
import akeijzer.labyrinths.maths.OverlapTester;
import akeijzer.labyrinths.screen.Button;
import akeijzer.labyrinths.screen.StartButton;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class StartScreen implements OnTouchListener
{
    private int width, height;
    
    private List<Button> buttons = new ArrayList<Button>();
    private Bitmap background;
    
    public StartScreen(GameView view)
    {
        this.width = view.getWidth();
        this.height = view.getHeight();
        buttons.add(new StartButton(width/2, height/4, width/2, height/4, view));
        this.background = Bitmap.createScaledBitmap(Resources.floor, view.getWidth(), view.getHeight(), false);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        for (Button button : buttons)
        {
            if (OverlapTester.pointInRectangle(button, x, y))
            {
                button.doAction();
            }
        }
        return false;
    }
    
    public void render(Canvas canvas)
    {
        canvas.drawBitmap(background, 0, 0, null);
        
        for (Button button : buttons)
        {
            button.render(canvas);
        }
    }

}
