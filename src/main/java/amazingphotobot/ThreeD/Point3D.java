package amazingphotobot.ThreeD;

import amazingphotobot.ThreeD.Point2D;

public class Point3D extends Point2D{
    public double z;

    public Point3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }
    public Point3D(Point3D p){
        super(p);
        this.z = p.z;
    }
}
