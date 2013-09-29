package akeijzer.labyrinths.maths;

public class Rectangle
{
    public final Vector2 topLeft;
    public Vector2 pos;
    public float width, height;

    public Rectangle(float x, float y, float width, float height)
    {
        this.topLeft = new Vector2(x - width / 2, y - height / 2);
        this.pos = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }
}
