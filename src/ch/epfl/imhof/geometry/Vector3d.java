package ch.epfl.imhof.geometry;

/**
 * A vector represented in a three dimension Cartesian coordinate system.
 * 
 * @author Matthieu Bovel (250300)
 *
 */
public final class Vector3d {
    private final double x;
    private final double y;
    private final double z;
    
    /**
     * Construct a vector given its x, y and z components.
     * 
     * @param x
     *            the x component
     * @param y
     *            the y component
     * @param z
     *            the z component
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Returns the x component of the vector.
     * 
     * @return the x component of the vector
     */
    public double x() {
        return x;
    }
    
    /**
     * Returns the y component of the vector.
     * 
     * @return the y component of the vector
     */
    public double y() {
        return y;
    }
    
    /**
     * Returns the z component of the vector.
     * 
     * @return the z component of the vector
     */
    public double z() {
        return z;
    }
    
    /**
     * Returns the norm of the vector.
     * 
     * @return the norm of the vector
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    /**
     * Returns the normalized vector of this vector.
     * 
     * @return the normalized vector of this vector
     */
    public Vector3d normalized() {
        double norm = norm();
        return new Vector3d(x / norm, y / norm, z / norm);
    }
    
    // Naming convention from http://victorjs.org and
    // http://sylvester.jcoglan.com
    /**
     * Returns the product of two vector.
     * 
     * @param that
     *            the vector to multiply with
     *            
     * @return the product of this vector and that vector
     */
    public double dot(Vector3d that) {
        return x * that.x + y * that.y + z * that.z;
    }
    
    // public Vector3d cross(Vector3d that) {
    // return new Vector3d(y * that.z - z * that.y, z * that.x - x * that.z, x
    // * that.y - y - that.x);
    // }
    //
    // public Vector3d add(Vector3d that) {
    // return new Vector3d(x + that.x, y + that.y, z + that.z);
    // }
    //
    // public Vector3d multiply(double k) {
    // return new Vector3d(k * x, k * y, k * z);
    // }
}
