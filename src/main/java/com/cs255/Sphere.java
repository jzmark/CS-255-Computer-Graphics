package com.cs255;

public class Sphere {
    Vector origin;
    Vector d;
    Vector centre;
    double radius;
    Vector p;
    double t;
    double a, b, c;
    Vector v;
    Vector light;

    public Sphere(Vector origin, Vector d, Vector centre, double radius, Vector light) {
        this.origin = origin;
        this.d = d;
        this.centre = centre;
        this.radius = radius;
        this.light = light;
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
