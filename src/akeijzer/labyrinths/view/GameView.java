package akeijzer.labyrinths.view;

import java.util.Iterator;

import akeijzer.labyrinths.Game;
import akeijzer.labyrinths.game.GameThread;
import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.EndPoint;
import akeijzer.labyrinths.object.Wall;
import akeijzer.labyrinths.object.upgrade.Upgrade;
import akeijzer.labyrinths.physics.CollisionEffects;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Callback
{
    private Game game;
    public World world;
    private SurfaceHolder holder;
    private GameThread thread;

    public float orientation[] = new float[3];

    public GameView(Context context)
    {
        super(context);
        game = (Game) context;
        holder = getHolder();
        holder.addCallback(this);
        

        thread = new GameThread(holder, this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
    	this.world = new World(this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.d("GameView", "Surface is being destroyed");
        thread.setRunning(false);
        while (true)
        {
            try
            {
                thread.join();
                break;
            }
            catch (InterruptedException e)
            {
            }
        }
        Log.d("GameView", "Surface is destroyed");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    public void render(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        for (Wall wall : world.walls)
        {
            wall.draw(canvas);
        }

        for (Ball ball : world.balls)
        {
            ball.draw(canvas);
        }
        for (EndPoint endPoint : world.endPoints)
        {
        	endPoint.draw(canvas);
        }
        for (Upgrade upgrade : world.upgrades)
        {
        	upgrade.draw(canvas);
        }
    }

    public void update()
    {
        orientation = game.orientation.getOrientation();
        
		Iterator<Ball> iBA = world.balls.iterator();
		while (iBA.hasNext())
		{
			Ball balls = iBA.next();
        	balls.update();
        }
		Iterator<EndPoint> iEP = world.endPoints.iterator();
		while (iEP.hasNext())
		{
			EndPoint endPoint = iEP.next();
        	endPoint.update();
        }
		Iterator<Upgrade> iUP = world.upgrades.iterator();
		while (iUP.hasNext())
		{
			Upgrade upgrade = iUP.next();
        	if (upgrade.update())
        		iUP.remove();
        }
        for (Ball ball : world.balls)
        {
            for (Ball ball2 : world.balls)
            {
                if (ball2 != ball)
                {
                    CollisionEffects.circleEffect(ball, ball2);
                }
            }
        }
    }

}
