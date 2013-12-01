package akeijzer.labyrinths.game;

import java.util.ArrayList;
import java.util.List;

import akeijzer.labyrinths.lib.Resources;
import akeijzer.labyrinths.maths.OverlapTester;
import akeijzer.labyrinths.screen.Button;
import akeijzer.labyrinths.screen.DoneButton;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class EndScreen implements OnTouchListener
{
    private int width, height;
    
    private List<Button> buttons = new ArrayList<Button>();
    private Bitmap background;
    private String time;
    private Paint textPaint;
    
    public EndScreen(GameView view, String time)
    {
        this.width = view.getWidth();
        this.height = view.getHeight();
        this.time = time;
        buttons.add(new DoneButton(width/2, height - height/4, width/2, height/4, view));
        this.background = Bitmap.createScaledBitmap(Resources.floor, view.getWidth(), view.getHeight(), false);
        textPaint = new Paint();
        textPaint.setTextSize(100);
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextAlign(Align.CENTER);
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
        
        canvas.drawText(time, width/2, height/4, textPaint);
        
        for (Button button : buttons)
        {
            button.render(canvas);
        }
    }

}
