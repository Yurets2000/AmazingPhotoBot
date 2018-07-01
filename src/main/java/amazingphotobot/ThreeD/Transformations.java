package amazingphotobot.ThreeD;

import amazingphotobot.ThreeD.Point3D;
import amazingphotobot.ThreeD.Point2D;
import java.awt.image.BufferedImage;

public class Transformations {
    
    public static double[][] multiply(double[][] m1, double[][] m2) {
        int m1ColLength = m1[0].length;
        int m2RowLength = m2.length;    
        if(m1ColLength != m2RowLength) return null; 
        int mRRowLength = m1.length;    
        int mRColLength = m2[0].length; 
        double[][] mResult = new double[mRRowLength][mRColLength];
        for(int i = 0; i < mRRowLength; i++) {      
            for(int j = 0; j < mRColLength; j++) {    
                for(int k = 0; k < m1ColLength; k++) { 
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;
    }
    
        public static Point2D[] planeProjection(Point3D[] points, double dist, double projectDist){
            Point3D[] newPoints = projection(points, dist, projectDist);
            Point2D[] planePoints = new Point2D[newPoints.length];
            
            for (int i = 0; i < points.length; i++){
                planePoints[i] = new Point2D(newPoints[i].x, newPoints[i].y);
            }
            return planePoints;
        }
        
        public static Point3D[] projection(Point3D[] points, double dist, double projectDist){
            Point3D[] newPoints = new Point3D[points.length];
            for(int i = 0; i < points.length; i++){
                newPoints[i] = new Point3D(points[i]);
            }
            for(int i = 0; i < newPoints.length; i++){
                newPoints[i].x *= (dist - projectDist) / (dist - newPoints[i].z);
                newPoints[i].y *= (dist - projectDist) / (dist - newPoints[i].z);
                newPoints[i].z -= projectDist;
            }
            return newPoints;
        }       
        
        public static double[][] getXRotationMatrix(double angle){
            double[][] m = { {1,0,0,0},
                             {0,Math.cos(angle), -Math.sin(angle),0 },
                             {0,Math.sin(angle), Math.cos(angle),0 },
                             {0,0,0,1} };
            return m;
        }
        public static double[][] getYRotationMatrix(double angle){
            double[][] m = { {Math.cos(angle),0,-Math.sin(angle),0},
                             {0,1,0,0 },
                             {Math.sin(angle),0, Math.cos(angle),0 },
                             {0,0,0,1} };
            return m;
        }
        public static double[][] getZRotationMatrix(double angle){
            double[][] m = { {Math.cos(angle), -Math.sin(angle),0,0 },
                             {Math.sin(angle), Math.cos(angle),0,0 },
                             {0,0,1,0},
                             {0,0,0,1} };
            return m;
        }
        public static double[][] getTranslationMatrix(double dx, double dy, double dz){
            double[][] m = { {1,0,0,dx},
                             {0,1,0,dy},
                             {0,0,1,dz},
                             {0,0,0,1} };
            return m;
        }
        public static double[][] getShearMatrix(double kx, double ky, double kz){
            double[][] m = { {kx,0,0,0},
                             {0,ky,0,0},
                             {0,0,kz,0},
                             {0,0,0,1} };
            return m;
        }
        
        public static BufferedImage transform(double[][] m, BufferedImage bi, double dist, double projectDist){
            int height = bi.getHeight();
            int width = bi.getWidth();
            BufferedImage transformed = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Point3D[] points = new Point3D[width*height];
            Point3D[] newPoints = new Point3D[width*height];
            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x++){
                    points[y*width+x] = new Point3D(x, y, 0);
                }               
            }
            newPoints = transform(m, points);
            Point2D[] np = planeProjection(newPoints, dist, projectDist);
            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x++){
                    int p =  bi.getRGB(x, y);                  
                    if (np[y*width+x].y > 0 && np[y*width+x].y < height && np[y*width+x].x > 0 && np[y*width+x].x < width)
                        transformed.setRGB((int) np[y*width+x].x, (int) np[y*width+x].y, p);
                }
            }            
            return transformed;
        }
                
        public static Point3D[] transform(double[][] m, Point3D[] points){
            Point3D[] npoints = new Point3D[points.length];
            for (int i = 0; i < points.length; i++){
                Point3D p = points[i];
                double[][] pm = { { p.x }, { p.y }, { p.z }, { 1 } };
                double[][] res = multiply(m, pm);
                npoints[i] = new Point3D(res[0][0], res[1][0], res[2][0]);
            }
            return npoints;
        }
}
