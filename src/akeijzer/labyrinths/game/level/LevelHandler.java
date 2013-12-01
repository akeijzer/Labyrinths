package akeijzer.labyrinths.game.level;

import akeijzer.labyrinths.game.GameState;
import akeijzer.labyrinths.game.World;

public class LevelHandler
{
    private static World world;
    
    /**
     * The currently loaded level number
     */
    private static int levelNumber;
    
    public static void init(World theWorld)
    {
        world = theWorld;
        levelNumber = 1;
        LoadCurrentLevel();
    }

    private static void LoadCurrentLevel()
    {
        world.clearWorld();
        switch (levelNumber)
        {
        case 1:
            Level1.loadLevel(world);
            break;
        case 2:
            Level2.loadLevel(world);
            break;
        default:
            world.view.setGameState(GameState.ENDSCREEN);
        }
    }

    /**
     * Loads the next level
     */
    public static void loadNextLevel()
    {
        levelNumber++;
        LoadCurrentLevel();
    }
    
    /**
     * Reloads the current level
     */
    public static void reloadLevel()
    {
        LoadCurrentLevel();
    }
}
