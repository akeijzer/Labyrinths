package akeijzer.labyrinths.object;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.lib.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.ThumbnailUtils;

public class Wall extends GameRectCollidable
{
    public Paint paint;
    private Bitmap icon;

    public Wall(int posX, int posY, int sizeX, int sizeY, World world)
    {
        super(posX, posY, sizeX, sizeY, world);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        this.icon = ThumbnailUtils.extractThumbnail(Resources.wall, sizeX, sizeY);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(icon, posX - sizeX / 2, posY - sizeY / 2, null);
    }

    @Override
    public void onCollide()
    {
    }

    @Override
    public void update()
    {
        super.update();
    }

}
