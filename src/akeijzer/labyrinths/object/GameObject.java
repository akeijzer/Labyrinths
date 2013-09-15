package akeijzer.labyrinths.object;

import akeijzer.labyrinths.view.GameView;

public abstract class GameObject
{
	public int sizeX, sizeY, posX, posY;
	protected GameView view;
	
	public GameObject(int sizeX, int sizeY, GameView view)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.view = view;
	}
}
