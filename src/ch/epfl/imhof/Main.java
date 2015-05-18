package ch.epfl.imhof;

public class Main {
    
    public static void main(String[] args) {
        String osmFile = args[0];
        String hgtFile = args[1];
        PointGeo bl = new PointGeo(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        PointGeo tr = new PointGeo(Double.parseDouble(args[4]), Double.parseDouble(args[5]));
        int res = Integer.parseInt(args[6]);
        String outputFile = args[7];
    }
    
}
