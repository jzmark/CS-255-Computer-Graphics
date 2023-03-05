package com.cs255;

import java.lang.Math.*;

/**
 * @author Marek Jezinski 2017161, Gracjan Golebiewski 2018590 (worked as a Pair)
 * @version 1.1
 *
 * This is a class that allows the creation of vectors
 *
 */

public class Vector {
  //Creating the variables
  double x, y, z;

  /**
   * This method allows the construction of an empty Vector
   */
  public Vector() {}

  /**
   * Constructor for a Vector that will have
   * the selected axis based on variables selected
   * @param i
   * @param j
   * @param k
   */
  public Vector(double i, double j, double k) {
    x = i;
    y = j;
    z = k;
  }

  /**
   * A method to check the current value for vertical axis of the Vector
   * @return x, The vertical axis value for Vector
   */
  public double getX() {
    return x;
  }

  /**
   * A method to set a value for vertical axis of the Vector
   * @param x
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * A method to check the current value for horizontal axis of the Vector
   * @return y, The horizontal axis value for Vector
   */
  public double getY() {
    return y;
  }

  /**
   * A method to set a value for horizontal axis of the Vector
   * @param y
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * A method to check the current value for depth of the Vector
   * @return Z, The depth value for Vector
   */
  public double getZ() {
    return z;
  }

  /**
   * A method to set a value for depth of the Vector
   * @param z
   */
  public void setZ(double z) {
    this.z = z;
  }

  /**
   * A method that returns the magnitude of the Vector
   * @return a value = magnitude of a Vector
   */
  public double magnitude() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  /**
   * A method to transform a Vector into a Unit Vector,
   * by calculating its length and dividing each axis by it.
   */
  public void normalise() {
    double mag = magnitude();
    if (mag != 0) {
      x /= mag;
      y /= mag;
      z /= mag;
    }
  }

  /**
   * A method to sum the components of the chosen Vector
   *
   * @param a
   * @return sum of Vector
   */
  public double dot(Vector a) {
    return x * a.x + y * a.y + z * a.z;
  }

  /**
   * A method to subtract the components from the chosen Vector
   * @param a
   * @return new Vector
   */
  public Vector sub(Vector a) {
    return new Vector(x - a.x, y - a.y, z - a.z);
  }

  /**
   * A method to add the components from the chosen Vector
   * @param a
   * @return new Vector
   */
  public Vector add(Vector a) {
    return new Vector(x + a.x, y + a.y, z + a.z);
  }

  /**
   * A method to multiply the components by the chosen value
   * @param d
   * @return new Vector
   */
  public Vector mul(double d) {
    return new Vector(d * x, d * y, d * z);
  }

  //Print statement for the X, Y and Z axis
  public void print() {
    System.out.println("x=" + x + ", y=" + y + ", z=" + z);
  }
}