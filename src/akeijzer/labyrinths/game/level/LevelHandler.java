package akeijzer.labyrinths.game.level;

import akeijzer.labyrinths.game.World;

public class LevelHandler
{
	private World world;
	private Level currentLevel;
	private int levelNumber;
	
	public LevelHandler(World world)
	{
		this.world = world;
		levelNumber = 2;
		LoadCurrentLevel();
	}
	
	private void LoadCurrentLevel()
	{
		switch(levelNumber)
		{
		case 1:
			currentLevel = new Level1(world);
			break;
		case 2:
			currentLevel = new Level2(world);
			break;
		}
		world.clearWorld();
		currentLevel.loadLevel();
	}
	
	public void loadNextLevel()
	{
		levelNumber++;
		LoadCurrentLevel();
	}
}
