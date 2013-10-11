package akeijzer.labyrinths.physics;

public class Physics
{
    public static final float GAVITATION_CONSTANT = 9.81F;
    public static final float ACCELERATION_CONSTANT = 0.714F;
    public static final float AIR_DESITY = 1.293F;
    public static final float DRAG_COEFFICIENT = 0.47F;

    public static float calculateAcceleration(float orientation)
    {
        return (ACCELERATION_CONSTANT * GAVITATION_CONSTANT * (float) Math.sin(orientation));
    }

    public static float calculateFriction(float velocity, float radius)
    {

        return (float) (0.5 * AIR_DESITY * velocity * velocity * Math.PI * radius * radius * DRAG_COEFFICIENT);
    }
}