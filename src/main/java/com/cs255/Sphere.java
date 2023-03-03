package com.cs255;

public class Sphere {
    private Vector origin;
    private Vector d;
    private Vector centre;
    private double radius;
    private Vector p;
    private double t;
    private double a, b, c;
    private Vector v;
    private Vector light;
    private int redCol = 255, blueCol = 255, greenCol = 255;

    public Sphere(Vector origin, Vector d, Vector centre, double radius, Vector light) {
        this.origin = origin;
        this.d = d;
        this.centre = centre;
        this.radius = radius;
        this.light = light;
    }

    public double getX() {
        return centre.getX();
    }

    public void setX(double x) {
        centre.setX(x);
    }

    public double getY() {
        return centre.getY();
    }

    public void setY(double y) {
        centre.setY(y);
    }

    public double getZ() {
        return centre.getZ();
    }

    public void setZ(double z) {
        centre.setZ(z);
    }

    public void setRedCol(int redCol) {
        this.redCol = redCol;
    }

    public void setBlueCol(int blueCol) {
        this.blueCol = blueCol;
    }

    public void setGreenCol(int greenCol) {
        this.greenCol = greenCol;
    }

    public double getRedCol() {
        return redCol / 255.0;
    }

    public double getBlueCol() {
        return blueCol / 255.0;
    }

    public double getGreenCol() {
        return greenCol / 255.0;
    }

    public double getCol(int i, int j) {
        double col;

        origin.x=i-250;
        origin.y=j-250;
        origin.z=-200;
        v = origin.sub(centre);
        a = d.dot(d);
        b = 2 * v.dot(d);
        c = v.dot(v) - radius * radius;
        double discriminate = b * b - 4 * a * c;
        t = (-b - Math.sqrt(discriminate)) / (2 * a);
        p = origin.add(d.mul(t));
        Vector lightSource = light.sub(p);
        lightSource.normalise();
        Vector n = p.sub(centre);
        n.normalise();
        double dp = lightSource.dot(n);
        if (dp < 0) {
            col = 0;
        } else {
            col = dp;
        }
        if (col > 1) {
            col = 1;
        }
        return col;
    }
}
