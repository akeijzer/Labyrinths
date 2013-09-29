package akeijzer.labyrinths.physics;

public class Physics
{
    public static final float GAVITATION_CONSTANT = 9.81F;
    public static final float ACCELERATION_CONSTANT = 0.714F;

    public static float calculateAcceleration(float orientation)
    {
        return (ACCELERATION_CONSTANT * GAVITATION_CONSTANT * (float) Math.sin(orientation));
    }
}