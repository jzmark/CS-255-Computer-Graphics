package com.cs255;
/**
 * @author Marek Jezinski 2017161, Gracjan Golebiewski 2018590 (worked as a Pair)
 * @version 1.1
 *
 * This is a class that enables the construction of Sphere objects
 * that contain colour and ambient shading. Modifications to size and
 * position of the sphere can also be performed.
 */

public class Sphere {
    //Creating the vector variables
    private Vector origin;
    private Vector d;
    private Vector centre;
    private double radius;
    private Vector p;
    private double t;
    private double a, b, c;
    private Vector v;
    private Vector light;

    //Setting values to colour variables
    private int redCol = 255, blueCol = 255, greenCol = 255;


    /**
     * Constructor used to create a Sphere object
     *
     * @param origin
     * @param d
     * @param centre
     * @param radius
     * @param light
     */
    public Sphere(Vector origin, Vector d, Vector centre, double radius, Vector light) {
        this.origin = origin;
        this.d = d;
        this.centre = centre;
        this.radius = radius;
        this.light = light;
    }

    /**
     * A method to check the current radius of the Sphere
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * A method to set a new value to the radius of the Sphere,
     * which can be used to alter the size of the rendered Sphere.
     * @param radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * A method to check the current value for vertical axis of a centre in a Sphere object
     * @return x, The vertical axis value (between 0 - 250) of the centre of a Sphere
     */
    public double getX() {
        return centre.getX();
    }

    /**
     * A method to set a value for vertical axis of the centre of a Sphere
     * @param x
     */
    public void setX(double x) {
        centre.setX(x);
    }

    /**
     * A method to check the current value for horizontal axis of a centre in a Sphere object
     * @return x, The horizontal axis value (between 0 - 250) of the centre of a Sphere
     */
    public double getY() {
        return centre.getY();
    }

    /**
     * A method to set a value for horizontal axis of the centre of a Sphere
     * @param y
     */
    public void setY(double y) {
        centre.setY(y);
    }

    /**
     * A method to check the current value for ? axis of a centre in a Sphere object
     * @return The ? axis of the centre of a Sphere
     */
    public double getZ() {
        return centre.getZ();
    }

    /**
     * A method to set a value for ? axis of the centre of a Sphere
     * @param z
     */
    public void setZ(double z) {
        centre.setZ(z);
    }

    /**
     * A method to set a colour in the red RGB scale (0 - 255)
     * @param redCol
     */
    public void setRedCol(int redCol) {
        this.redCol = redCol;
    }

    /**
     * A method to set a colour in the blue RGB scale (0 - 255)
     * @param blueCol
     */
    public void setBlueCol(int blueCol) {
        this.blueCol = blueCol;
    }

    /**
     * A method to set a colour in the green RGB scale (0 - 255)
     * @param greenCol
     */
    public void setGreenCol(int greenCol) {
        this.greenCol = greenCol;
    }

    /**
     * A method to return the red colour between the values of 0 - 1
     * @return a value = redCol / 255.0
     */
    public double getRedCol() {
        return redCol / 255.0;
    }

    /**
     * A method to return the blue colour between the values of 0 - 1
     * @return a value = blueCol / 255.0
     */
    public double getBlueCol() {
        return blueCol / 255.0;
    }

    /**
     * A method to return the green colour between the values of 0 - 1
     * @return a value = greenCol / 255.0
     */
    public double getGreenCol() {
        return greenCol / 255.0;
    }

    /**
     * A method that allows the Sphere to get correct colour
     * and shading based on the values assigned to the variables.
     * It does not allow the light source value to be lower or greater than 1,
     * and colour of the Sphere is mixed with the light source to ensure shading is present.
     *
     * @param i
     * @param j
     * @return col - current colour of the Sphere, with shading
     */
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
