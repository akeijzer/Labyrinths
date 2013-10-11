package akeijzer.labyrinths.view;

import java.util.Iterator;

import akeijzer.labyrinths.Game;
import akeijzer.labyrinths.R;
import akeijzer.labyrinths.game.GameThread;
import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.GameObject;
import akeijzer.labyrinths.physics.CollisionEffects;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Callback
{
    private Game game;
    public World world;
    private SoundPool soundPool;
    public boolean playSounds = true;
    private SurfaceHolder holder;
    private GameThread thread;
    public Iterator<GameObject> iObjects;
    public Iterator<Ball> iBalls;
    public boolean stopIteration = false;

    public float orientation[] = new float[3];

    public GameView(Context context)
    {
        super(context);
        game = (Game) context;
        holder = getHolder();
        holder.addCallback(this);

        thread = new GameThread(holder, this);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        initSounds();
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

        for (GameObject gameObject : world.objects)
        {
            gameObject.draw(canvas);
        }

        for (Ball ball : world.balls)
        {
            ball.draw(canvas);
        }
    }

    public void update()
    {
        orientation = game.orientation.getOrientation();

        iBalls = world.balls.iterator();
        while (iBalls.hasNext())
        {
            Ball ball = iBalls.next();
            ball.update();
        }
        iBalls = null;

        iObjects = world.objects.iterator();
        while (!stopIteration && iObjects.hasNext())
        {
            GameObject gameObject = iObjects.next();
            gameObject.update();
        }
        stopIteration = false;
        iObjects = null;
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

        if (world.balls.isEmpty())
        {
            world.levelHandler.loadNextLevel();
        }
    }

    public int tickSoundId;

    public void initSounds()
    {
        tickSoundId = soundPool.load(game, R.raw.tick, 1);
    }

    public void playSound(int soundId, float rate)
    {
        if (playSounds) soundPool.play(soundId, 1.0F, 1.0F, 1, 0, rate);
    }

}
