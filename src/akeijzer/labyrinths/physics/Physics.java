package akeijzer.labyrinths.physics;


public class Physics
{
	public static float calculateAcceleration(float mass, float orientation, float frictionCoefficent)
	{
		float gravityForce = mass * 9.81F;
		float forwardForce = (float) (gravityForce * Math.sin(orientation));
		float normalForce = (float) (gravityForce * Math.cos(orientation));
		float frictionForce = normalForce * frictionCoefficent;
		float acceleration = (forwardForce)/mass;
		return acceleration;
	}
}