package akeijzer.labyrinths.game;

import akeijzer.labyrinths.view.GameView;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
	private final static int 	MAX_FPS = 30;	
	private final static int	MAX_FRAME_SKIPS = 5;	
	private final static int	FRAME_PERIOD = 1000 / MAX_FPS;	

	private SurfaceHolder surfaceHolder;
	private GameView gameView;

	private boolean running;
	
	public GameThread(SurfaceHolder surfaceHolder, GameView gameView)
	{
		super();
		this.surfaceHolder = surfaceHolder;
		this.gameView = gameView;
	}
	
	@Override
	public void run()
	{
		Canvas canvas;

		long beginTime;
		long timeDiff;
		int sleepTime;
		int framesSkipped;

		sleepTime = 0;
		
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				
				if (canvas != null)
				{
					synchronized (surfaceHolder)
					{
						beginTime = System.currentTimeMillis();
						framesSkipped = 0;
						this.gameView.update();
						this.gameView.render(canvas);				
						timeDiff = System.currentTimeMillis() - beginTime;
						sleepTime = (int)(FRAME_PERIOD - timeDiff);
						
						if (sleepTime > 0) {
							try
							{
								Thread.sleep(sleepTime);	
							}
							catch (InterruptedException e) {}
						}
						
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
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	
	public void setRunning(boolean running)
	{
		this.running = running;
	}
}
