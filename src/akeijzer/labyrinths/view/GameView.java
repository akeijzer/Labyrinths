package akeijzer.labyrinths.view;

import akeijzer.labyrinths.Game;
import akeijzer.labyrinths.game.GameThread;
import akeijzer.labyrinths.object.Ball;
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
	private SurfaceHolder holder;
	private GameThread thread;
	
	public float orientation[] = new float[3];
	
	private Ball ball;

	public GameView(Context context) 
	{
		super(context);
		game = (Game) context;
		holder = getHolder();
		holder.addCallback(this);
		
		thread = new GameThread(holder, this);
		
		ball = new Ball(20, 20, this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Log.d("GameView", "Surface is being destroyed");
		thread.setRunning(false);
		while (true) {
			try {
				thread.join();
				break;
			} catch (InterruptedException e) {
			}
		}
		Log.d("GameView", "Surface is destroyed");
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}
	
	public void render(Canvas canvas)
	{
		canvas.drawColor(Color.WHITE);
		ball.draw(canvas);
	}
	
	public void update()
	{
		orientation = game.orientation.getOrientation();
		ball.update();
	}
	
}
