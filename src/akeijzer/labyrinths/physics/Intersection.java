package akeijzer.labyrinths.physics;

import akeijzer.labyrinths.maths.Side;

public class Intersection
{
    public float cx, cy, time, nx, ny, ix, iy;
    public Side side;

    public Intersection(float x, float y, float time, float nx, float ny, float ix, float iy, Side side)
    {
        this.cx = x;
        this.cy = y;
        this.time = time;
        this.nx = nx;
        this.ny = ny;
        this.ix = ix;
        this.iy = iy;
        this.side = side;
    }
}
