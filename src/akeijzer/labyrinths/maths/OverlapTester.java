package akeijzer.labyrinths.maths;

public class OverlapTester
{
    public static boolean overlapCircles(Circle c1, Circle c2)
    {
        float distance = c1.center.distSquared(c2.center);
        float radiusSum = c1.radius + c2.radius;
        return distance <= radiusSum * radiusSum;
    }

    public static boolean overlapRectangles(Rectangle r1, Rectangle r2)
    {
        if (r1.topLeft.x < r2.topLeft.x + r2.width && r1.topLeft.x + r1.width > r2.topLeft.x && r1.topLeft.y < r2.topLeft.y + r2.height
                && r1.topLeft.y + r1.height > r2.topLeft.y) return true;
        else return false;
    }

    public static boolean overlapCircleRectangle(Circle c, Rectangle r)
    {
        float closestX = c.center.x;
        float closestY = c.center.y;

        if (c.center.x < r.topLeft.x)
        {
            closestX = r.topLeft.x;
        }
        else if (c.center.x > r.topLeft.x + r.width)
        {
            closestX = r.topLeft.x + r.width;
        }

        if (c.center.y < r.topLeft.y)
        {
            closestY = r.topLeft.y;
        }
        else if (c.center.y > r.topLeft.y + r.height)
        {
            closestY = r.topLeft.y + r.height;
        }

        return c.center.distSquared(closestX, closestY) < c.radius * c.radius;
    }

    public static Side sidedOverlapCircleRectangle(Circle c, Rectangle r)
    {
        float closestX = c.center.x;
        float closestY = c.center.y;

        if (c.center.x < r.topLeft.x)
        {
            closestX = r.topLeft.x;
        }
        else if (c.center.x > r.topLeft.x + r.width)
        {
            closestX = r.topLeft.x + r.width;
        }

        if (c.center.y < r.topLeft.y)
        {
            closestY = r.topLeft.y;
        }
        else if (c.center.y > r.topLeft.y + r.height)
        {
            closestY = r.topLeft.y + r.height;
        }

        if (c.center.distSquared(closestX, closestY) < c.radius * c.radius)
        {
            if (closestY == c.center.y && closestX == r.topLeft.x)
            {
                return Side.LEFT;
            }
            else if (closestY == c.center.y && closestX == r.topLeft.x + r.width)
            {
                return Side.RIGHT;
            }
            else if (closestX == c.center.x && closestY == r.topLeft.y)
            {
                return Side.TOP;
            }
            else if (closestX == c.center.x && closestY == r.topLeft.y + r.height)
            {
                return Side.BOTTOM;
            }
        }

        return null;
    }

    public static boolean pointInCircle(Circle c, Vector2 p)
    {
        return c.center.distSquared(p) < c.radius * c.radius;
    }

    public static boolean pointInCircle(Circle c, float x, float y)
    {
        return c.center.distSquared(x, y) < c.radius * c.radius;
    }

    public static boolean pointInRectangle(Rectangle r, Vector2 p)
    {
        return r.topLeft.x <= p.x && r.topLeft.x + r.width >= p.x && r.topLeft.y <= p.y && r.topLeft.y + r.height >= p.y;
    }

    public static boolean pointInRectangle(Rectangle r, float x, float y)
    {
        return r.topLeft.x <= x && r.topLeft.x + r.width >= x && r.topLeft.y <= y && r.topLeft.y + r.height >= y;
    }
}
