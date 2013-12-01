package akeijzer.labyrinths.lib;

import akeijzer.labyrinths.R;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Resources
{
    public static Bitmap ball;
    public static Bitmap wall;
    public static Bitmap floor;
    public static Bitmap pit;
    public static Bitmap start;
    public static Bitmap done;
    
    public static void init(GameView view)
    {
        ball = BitmapFactory.decodeResource(view.getResources(), R.drawable.ball);
        wall = BitmapFactory.decodeResource(view.getResources(), R.drawable.iron);
        floor = BitmapFactory.decodeResource(view.getResources(), R.drawable.floor);
        pit = BitmapFactory.decodeResource(view.getResources(), R.drawable.pit);
        start = BitmapFactory.decodeResource(view.getResources(), R.drawable.start);
        done = BitmapFactory.decodeResource(view.getResources(), R.drawable.done);
    }
}
