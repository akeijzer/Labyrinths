package akeijzer.labyrinths.game;

import akeijzer.labyrinths.view.GameView;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
    public final static int MAX_FPS = 30;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    public final static float SECONDS_PER_FRAME = 1 / (float) MAX_FPS;

    private SurfaceHolder surfaceHolder;
    private GameView gameView;

    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    /**
     * Main game loop
     */
    @Override
    public void run()
    {
        Canvas canvas;

        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;

        sleepTime = 0;
        
        //Repeat while running
        while (running)
        {
            canvas = null;
            try
            {
                // Make canvas writable
                canvas = this.surfaceHolder.lockCanvas();

                if (canvas != null)
                {
                    synchronized (surfaceHolder)
                    {
                        // Time this update and rendering started
                        beginTime = System.currentTimeMillis();
                        framesSkipped = 0;

                        // Update game object
                        this.gameView.update();

                        // Render game objects
                        this.gameView.render(canvas);

                        timeDiff = System.currentTimeMillis() - beginTime;
                        sleepTime = (int) (FRAME_PERIOD - timeDiff);

                        // Sleep time left before updating/rendering next frame
                        if (sleepTime > 0)
                        {
                            try
                            {
                                Thread.sleep(sleepTime);
                            }
                            catch (InterruptedException e)
                            {
                            }
                        }

                        // Updating and rendering took longer than the frame
                        // period! Only update next frame to catch up
                        while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS)
                        {
                            this.gameView.update();
                            sleepTime += FRAME_PERIOD;
                            framesSkipped++;
                            Log.d("Game Thread", "Skipped frame! Deveice runs slowly!");
                        }
                    }
                }
            }
            finally
            {
                if (canvas != null)
                {
                    // Write changes to canvas
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    /**
     * Sets gameThread running or disabled
     * 
     * @param running
     */
    public void setRunning(boolean running)
    {
        this.running = running;
    }
}
