package akeijzer.labyrinths.view;

import akeijzer.labyrinths.Game;
import akeijzer.labyrinths.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable
{
	Game game;
	SurfaceHolder holder;
	Thread thread;
	boolean isRunning = false;
	
	Bitmap optionIcon;

	public GameView(Context context) 
	{
		super(context);
		game = (Game) context;
		holder = getHolder();
		
		optionIcon = BitmapFactory.decodeResource(getResources(), R.drawable.options);
	}
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
	}

	@Override
	public void run() 
	{
		while(isRunning)
		{
			if (!holder.getSurface().isValid())
				continue;
			
			Canvas canvas = holder.lockCanvas();
			
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(optionIcon, game.x, game.y, null);
			
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause()
	{
		isRunning = false;
		while (true)
		{
			try
			{
				thread.join();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		thread = null;
	}

	public void resume()
	{
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
}
