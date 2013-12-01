package akeijzer.labyrinths.view;

import akeijzer.labyrinths.Game;
import akeijzer.labyrinths.R;
import akeijzer.labyrinths.game.EndScreen;
import akeijzer.labyrinths.game.GameState;
import akeijzer.labyrinths.game.GameThread;
import akeijzer.labyrinths.game.StartScreen;
import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.lib.Resources;
import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Callback
{
    public Game game;
    public World world;
    public StartScreen startScreen;
    public EndScreen endScreen;
    private SoundPool soundPool;
    public boolean playSounds = false;
    private SurfaceHolder holder;
    public GameThread thread;
    private GameState gameState;
    private boolean paused = false;

    public GameView(Context context)
    {
        super(context);
        game = (Game) context;
        holder = getHolder();
        holder.addCallback(this);

        // Init Main Thread and Sounds
        thread = new GameThread(holder, this);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        initSounds();
        Resources.init(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        setGameState(GameState.STARTSCREEN);
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

    /**
     * Redirects render order to active gameState
     * 
     * @param canvas
     */
    public void render(Canvas canvas)
    {
        if (!paused)
        {
            switch (gameState)
            {
            case STARTSCREEN:
                startScreen.render(canvas);
                break;
            case INGAME:
                world.render(canvas);
                break;
            case ENDSCREEN:
                endScreen.render(canvas);
                break;
            }
        }
    }

    /**
     * Redirects update order to active gameState
     */
    public void update()
    {
        if (!paused)
        {
            switch (gameState)
            {
            case STARTSCREEN:
                
                break;
            case INGAME:
                world.update();
                break;
            case ENDSCREEN:
                
                break;
            }
        }
    }
    
    public void setGameState(GameState gameState)
    {
        switch (gameState)
        {
        case STARTSCREEN:
            this.startScreen = new StartScreen(this);
            setOnTouchListener(startScreen);
            break;
        case INGAME:
            this.world = new World(this);
            break;
        case ENDSCREEN:
            endScreen = new EndScreen(this, world.getTime());
            setOnTouchListener(endScreen);
            break;
        }
        this.gameState = gameState;
    }
    
    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }

    public int tickSoundId;

    /**
     * Initialises sounds
     */
    public void initSounds()
    {
        tickSoundId = soundPool.load(game, R.raw.tick, 1);
    }

    /**
     * Plays sounds from soundId and rate
     * 
     * @param soundId
     * @param rate
     */
    public void playSound(int soundId, float rate)
    {
        if (playSounds) soundPool.play(soundId, 1.0F, 1.0F, 1, 0, rate);
    }

}
