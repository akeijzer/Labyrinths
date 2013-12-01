package akeijzer.labyrinths.maths;

/**
 * Used for collision detection
 * 
 * @author Alexander Keijzer
 *
 */
public class Circle
{
    public final Vector2 center = new Vector2();
    public float radius;

    public Circle(float x, float y, float radius)
    {
        this.center.set(x, y);
        this.radius = radius;
    }
}
