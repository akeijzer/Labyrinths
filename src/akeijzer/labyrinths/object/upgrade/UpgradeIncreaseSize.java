package akeijzer.labyrinths.object.upgrade;

import akeijzer.labyrinths.R;
import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.object.Ball;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

public class UpgradeIncreaseSize extends Upgrade
{
    private Bitmap icon;
    private Paint paint;

    public UpgradeIncreaseSize(int posX, int posY, World world)
    {
        super(posX, posY, world);
        icon = BitmapFactory.decodeResource(world.view.getResources(), R.drawable.increase_size);
        icon = Bitmap.createScaledBitmap(icon, sizeX, sizeY, false);
        paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY));
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(icon, posX - sizeX / 2, posY - sizeY / 2, paint);
    }

    @Override
    public void onCollision(Ball ball)
    {
        ball.setRadius(1.5);
    }
}
