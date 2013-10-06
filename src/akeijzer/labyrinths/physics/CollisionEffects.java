package akeijzer.labyrinths.physics;

import akeijzer.labyrinths.maths.Rectangle;
import akeijzer.labyrinths.maths.Vector2;
import akeijzer.labyrinths.object.Ball;

public class CollisionEffects
{
    // ref:
    // http://gamedev.stackexchange.com/questions/20516/ball-collisions-sticking-together
    public static void circleEffect(Ball a, Ball b)
    {
        double xDist, yDist;
        xDist = a.posX - b.posX;
        yDist = a.posY - b.posY;
        double distSquared = xDist * xDist + yDist * yDist;
        // Check the squared distances instead of the the distances, same
        // result, but avoids a square root.
        if (distSquared <= (a.radius + b.radius) * (a.radius + b.radius))
        {
            double speedXocity = b.velocityX - a.velocityX;
            double speedYocity = b.velocityY - a.velocityY;
            double dotProduct = xDist * speedXocity + yDist * speedYocity;
            // Neat vector maths, used for checking if the objects moves towards
            // one another.
            if (dotProduct > 0)
            {
                double collisionScale = dotProduct / distSquared;
                double xCollision = xDist * collisionScale;
                double yCollision = yDist * collisionScale;
                // The Collision vector is the speed difference projected on the
                // Dist vector,
                // thus it is the component of the speed difference needed for
                // the collision.
                double combinedMass = a.mass + b.mass;
                double collisionWeightA = 2 * b.mass / combinedMass;
                double collisionWeightB = 2 * a.mass / combinedMass;
                a.velocityX += (collisionWeightA * xCollision);
                a.velocityY += (collisionWeightA * yCollision);
                b.velocityX -= (collisionWeightB * xCollision);
                b.velocityY -= (collisionWeightB * yCollision);
            }
        }
    }

    // ref
    // http://stackoverflow.com/questions/18704999/how-to-fix-circle-and-rectangle-overlap-in-collision-response
    public static Intersection handleIntersection(Rectangle rectangle, Vector2 start, Vector2 end, float radius)
    {
        final float L = rectangle.pos.x - (rectangle.width / 2);
        final float T = rectangle.pos.y - (rectangle.height / 2);
        final float R = rectangle.pos.x + (rectangle.width / 2);
        final float B = rectangle.pos.y + (rectangle.height / 2);

        // If the bounding box around the start and end points (+radius on all
        // sides) does not intersect with the rectangle, definitely not an
        // intersection
        if ((Math.max(start.x, end.x) + radius < L) || (Math.min(start.x, end.x) - radius > R) || (Math.max(start.y, end.y) + radius < T)
                || (Math.min(start.y, end.y) - radius > B))
        {
            return null;
        }

        final float dx = end.x - start.x;
        final float dy = end.y - start.y;
        final float invdx = (dx == 0.0f ? 0.0f : 1.0f / dx);
        final float invdy = (dy == 0.0f ? 0.0f : 1.0f / dy);
        float cornerX = Float.MAX_VALUE;
        float cornerY = Float.MAX_VALUE;

        // Calculate intersection times with each side's plane
        // Check each side's quadrant for single-side intersection
        // Calculate Corner

        /** Left Side **/
        // Does the circle go from the left side to the right side of the
        // rectangle's left?
        if (start.x - radius < L && end.x + radius > L)
        {
            float ltime = ((L - radius) - start.x) * invdx;
            if (ltime >= 0.0f && ltime <= 1.0f)
            {
                float ly = dy * ltime + start.y;
                // Does the collisions point lie on the left side?
                if (ly >= T && ly <= B)
                {
                    return new Intersection(dx * ltime + start.x, ly, ltime, -1, 0, L, ly);
                }
            }
            cornerX = L;
        }

        /** Right Side **/
        // Does the circle go from the right side to the left side of the
        // rectangle's right?
        if (start.x + radius > R && end.x - radius < R)
        {
            float rtime = (start.x - (R + radius)) * -invdx;
            if (rtime >= 0.0f && rtime <= 1.0f)
            {
                float ry = dy * rtime + start.y;
                // Does the collisions point lie on the right side?
                if (ry >= T && ry <= B)
                {
                    return new Intersection(dx * rtime + start.x, ry, rtime, 1, 0, R, ry);
                }
            }
            cornerX = R;
        }

        /** Top Side **/
        // Does the circle go from the top side to the bottom side of the
        // rectangle's top?
        if (start.y - radius < T && end.y + radius > T)
        {
            float ttime = ((T - radius) - start.y) * invdy;
            if (ttime >= 0.0f && ttime <= 1.0f)
            {
                float tx = dx * ttime + start.x;
                // Does the collisions point lie on the top side?
                if (tx >= L && tx <= R)
                {
                    return new Intersection(tx, dy * ttime + start.y, ttime, 0, -1, tx, T);
                }
            }
            cornerY = T;
        }

        /** Bottom Side **/
        // Does the circle go from the bottom side to the top side of the
        // rectangle's bottom?
        if (start.y + radius > B && end.y - radius < B)
        {
            float btime = (start.y - (B + radius)) * -invdy;
            if (btime >= 0.0f && btime <= 1.0f)
            {
                float bx = dx * btime + start.x;
                // Does the collisions point lie on the bottom side?
                if (bx >= L && bx <= R)
                {
                    return new Intersection(bx, dy * btime + start.y, btime, 0, 1, bx, B);
                }
            }
            cornerY = B;
        }

        // No intersection at all!
        if (cornerX == Float.MAX_VALUE && cornerY == Float.MAX_VALUE)
        {
            return null;
        }

        // Account for the times where we don't pass over a side but we do hit
        // it's corner
        if (cornerX != Float.MAX_VALUE && cornerY == Float.MAX_VALUE)
        {
            cornerY = (dy > 0.0f ? B : T);
        }
        if (cornerY != Float.MAX_VALUE && cornerX == Float.MAX_VALUE)
        {
            cornerX = (dx > 0.0f ? R : L);
        }

        /*
         * Solve the triangle between the start, corner, and intersection point.
         */

        double inverseRadius = 1.0 / radius;
        double lineLength = Math.sqrt(dx * dx + dy * dy);
        double cornerdx = cornerX - start.x;
        double cornerdy = cornerY - start.y;
        double cornerDistance = Math.sqrt(cornerdx * cornerdx + cornerdy * cornerdy);
        double innerAngle = Math.acos((cornerdx * dx + cornerdy * dy) / (lineLength * cornerDistance));

        // If the circle is too close, no intersection.
        if (cornerDistance < radius)
        {
            return null;
        }

        // If inner angle is zero, it's going to hit the corner straight on.
        if (innerAngle == 0.0f)
        {
            float time = (float) ((cornerDistance - radius) / lineLength);

            // If time is outside the boundaries, return null. This algorithm
            // can
            // return a negative time which indicates a previous intersection,
            // and
            // can also return a time > 1.0f which can predict a corner
            // intersection.
            if (time > 1.0f || time < 0.0f)
            {
                return null;
            }

            float ix = time * dx + start.x;
            float iy = time * dy + start.y;
            float nx = (float) (cornerdx / cornerDistance);
            float ny = (float) (cornerdy / cornerDistance);

            return new Intersection(ix, iy, time, nx, ny, cornerX, cornerY);
        }

        double innerAngleSin = Math.sin(innerAngle);
        double angle1Sin = innerAngleSin * cornerDistance * inverseRadius;

        // The angle is too large, there cannot be an intersection
        if (Math.abs(angle1Sin) > 1.0f)
        {
            return null;
        }

        double angle1 = Math.PI - Math.asin(angle1Sin);
        double angle2 = Math.PI - innerAngle - angle1;
        double intersectionDistance = radius * Math.sin(angle2) / innerAngleSin;

        // Solve for time
        float time = (float) (intersectionDistance / lineLength);

        // If time is outside the boundaries, return null. This algorithm can
        // return a negative time which indicates a previous intersection, and
        // can also return a time > 1.0f which can predict a corner
        // intersection.
        if (time > 1.0f || time < 0.0f)
        {
            return null;
        }

        // Solve the intersection and normal
        float ix = time * dx + start.x;
        float iy = time * dy + start.y;
        float nx = (float) ((ix - cornerX) * inverseRadius);
        float ny = (float) ((iy - cornerY) * inverseRadius);

        return new Intersection(ix, iy, time, nx, ny, cornerX, cornerY);
    }
}
