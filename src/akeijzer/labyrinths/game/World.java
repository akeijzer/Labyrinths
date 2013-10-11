package akeijzer.labyrinths.game;

import java.util.ArrayList;

import akeijzer.labyrinths.game.level.LevelHandler;
import akeijzer.labyrinths.maths.Circle;
import akeijzer.labyrinths.maths.Vector2;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.object.GameObject;
import akeijzer.labyrinths.object.GameRectCollidable;
import akeijzer.labyrinths.physics.CollisionEffects;
import akeijzer.labyrinths.physics.Intersection;
import akeijzer.labyrinths.view.GameView;

public class World
{
    public LevelHandler levelHandler;
    public ArrayList<Ball> balls;
    public ArrayList<GameObject> objects;
    public GameView view;

    public World(GameView view)
    {
        this.view = view;
        balls = new ArrayList<Ball>();
        objects = new ArrayList<GameObject>();
        this.levelHandler = new LevelHandler(this);
    }

    public void clearWorld()
    {
        view.stopIteration = true;
        balls.clear();
        objects.clear();
    }

    public boolean checkCollisions(GameView gameView, Ball ball, Circle nextPos)
    {
        boolean collision = false;
        for (GameObject object : objects)
        {
            if (object instanceof GameRectCollidable)
            {
                GameRectCollidable collidable = (GameRectCollidable) object;
                Intersection inter = CollisionEffects.handleIntersection(collidable.bounds, new Vector2(ball.posX, ball.posY), nextPos.center, ball.radius);
                if (inter != null)
                {
                    float remainingTime = 1.0f - inter.time;
                    float dx = nextPos.center.x - ball.posX;
                    float dy = nextPos.center.y - ball.posY;
                    float dot = dx * inter.nx + dy * inter.ny;
                    float ndx = dx - 2 * dot * inter.nx;
                    float ndy = dy - 2 * dot * inter.ny;
                    float newx = inter.cx + ndx * remainingTime;
                    float newy = inter.cy + ndy * remainingTime;
    
                    nextPos.center.x = (int) newx;
                    nextPos.center.y = (int) newy;
    
                    ball.velocityX = ndx * GameThread.MAX_FPS * 0.9F;
                    ball.velocityY = ndy * GameThread.MAX_FPS * 0.9F;
                    collision = true;
                    collidable.onCollide();
                    ball.onCollide();
                }
            }
        }
        if (collision) return true;
        else return false;
    }
}
