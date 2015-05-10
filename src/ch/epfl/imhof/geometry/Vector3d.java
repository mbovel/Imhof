package ch.epfl.imhof.geometry;

public class Vector3d {
    private final double x;
    private final double y;
    private final double z;
    
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }
    
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    public Vector3d normalized() {
        double norm = norm();
        return new Vector3d(x / norm, y / norm, z / norm);
    }
    
    // Naming convention from http://victorjs.org and
    // http://sylvester.jcoglan.com
    public double dot(Vector3d that) {
        return x * that.x + y * that.y + z * that.z;
    }
    
    public Vector3d cross(Vector3d that) {
        return new Vector3d(y * that.z - z * that.y, z * that.x - x * that.z, x
                * that.y - y - that.x);
    }
    
    public Vector3d add(Vector3d that) {
        return new Vector3d(x + that.x, y + that.y, z + that.z);
    }
    
    public Vector3d multiply(double k) {
        return new Vector3d(k * x, k * y, k * z);
    }
}
