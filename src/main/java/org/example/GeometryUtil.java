package org.example;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * Geometry 工具类
 * 判断两个几何图形是否存在指定的空间关系。包括：
 *
 * 相等（equals）、
 * 分离（disjoint）、
 * 相交（intersect）、
 * 相接（touches）、
 * 交叉（crosses）、
 * 包含于（within）、
 * 包含（contains）、
 * 覆盖/覆盖于（overlaps）。
 * 同时，也支持一般的关系（relate）操作符。
 */
public class GeometryUtil {
    private final static GeometryFactory geometryFactory = new GeometryFactory();

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        GeometryUtil gt = new GeometryUtil();
        Polygon p = gt.createCircle(0, 1, 2);
        //圆上所有的坐标(32个)
        Coordinate coords[] = p.getCoordinates();
        for (Coordinate coord : coords) {
            System.out.println(coord.x + "," + coord.y);
        }
        Coordinate coordinate = new Coordinate();
        coordinate.x = 1;
        coordinate.y = 2;
        System.out.println(coordinate.x);

        Point pt = gt.createPoint(1,1);
        System.out.println(pt.getX() + "," + pt.getY());

        Point pt2 = gt.createPointByWKT(1,1);
        System.out.println(pt2.getX() + "," + pt2.getY());

        LineString l_1 = gt.createLine(20, 0, 30, 0);
        LineString l_2 = gt.createLine(20, 0, 30, 10);
        LineString l_3 = gt.createLine(30, 10, 30, 15);
        LineString l_4 = gt.createLine(20, 10, 30, 0);

        Polygon pol = gt.createPolygonByWKT();
        System.out.println(pol);

        System.out.println(l_1.within(pol));
        System.out.println(l_2.within(pol));
        System.out.println(l_3.within(pol));
        System.out.println(l_4.within(pol));

        System.out.println(pol.within(l_3));


    }

    /**
     * create a point
     *
     * @return
     */
    public static Point createPoint(double lon, double lat) {
        Coordinate coord = new Coordinate(lon, lat);
        Point point = geometryFactory.createPoint(coord);
        return point;
    }

    /**
     * create a point by WKT
     * POINT (109.013388 32.715519)
     *
     * @return
     * @throws ParseException
     */
    public static Point createPointByWKT(double lon, double lat) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        Point point = (Point) reader.read("POINT (" + lon + " " + lat + ")");
        return point;
    }

    /**
     * create multiPoint by wkt
     *
     * @return
     */
    public MultiPoint createMulPointByWKT() throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        MultiPoint mpoint = (MultiPoint) reader.read("MULTIPOINT(109.013388 32.715519,119.32488 31.435678)");
        return mpoint;
    }

    /**
     * create a line
     *
     * @return
     */
    public LineString createLine(int a, int b, int c, int d) {
        Coordinate[] coords = new Coordinate[]{new Coordinate(a, b), new Coordinate(c, d)};
        LineString line = geometryFactory.createLineString(coords);
        return line;
    }

    /**
     * create a line by WKT
     *
     * @return
     * @throws ParseException
     */
    public LineString createLineByWKT() throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        LineString line = (LineString) reader.read("LINESTRING(0 0, 2 0)");
        return line;
    }

    /**
     * create multiLine
     *
     * @return
     */
    public MultiLineString createMLine() {
        Coordinate[] coords1 = new Coordinate[]{new Coordinate(2, 2), new Coordinate(2, 2)};
        LineString line1 = geometryFactory.createLineString(coords1);
        Coordinate[] coords2 = new Coordinate[]{new Coordinate(2, 2), new Coordinate(2, 2)};
        LineString line2 = geometryFactory.createLineString(coords2);
        LineString[] lineStrings = new LineString[2];
        lineStrings[0] = line1;
        lineStrings[1] = line2;
        MultiLineString ms = geometryFactory.createMultiLineString(lineStrings);
        return ms;
    }

    /**
     * create multiLine by WKT
     *
     * @return
     * @throws ParseException
     */
    public MultiLineString createMLineByWKT() throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        MultiLineString line = (MultiLineString) reader.read("MULTILINESTRING((0 0, 2 0),(1 1,2 2))");
        return line;
    }

    /**
     * create a polygon(多边形) by WKT
     *
     * @return
     * @throws ParseException
     */
    public Polygon createPolygonByWKT() throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        Polygon polygon = (Polygon) reader.read("POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))");
        return polygon;
    }

    /**
     * create multi polygon by wkt
     *
     * @return
     * @throws ParseException
     */
    public MultiPolygon createMulPolygonByWKT() throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        MultiPolygon mpolygon = (MultiPolygon) reader.read("MULTIPOLYGON(((40 10, 30 0, 40 10, 30 20, 40 10),(30 10, 30 0, 40 10, 30 20, 30 10)))");
        return mpolygon;
    }

    /**
     * create GeometryCollection  contain point or multiPoint or line or multiLine or polygon or multiPolygon
     *
     * @return
     * @throws ParseException
     */
    public GeometryCollection createGeoCollect() throws ParseException {
        LineString line = createLine(1, 1, 1, 1);
        Polygon poly = createPolygonByWKT();
        Geometry g1 = geometryFactory.createGeometry(line);
        Geometry g2 = geometryFactory.createGeometry(poly);
        Geometry[] garray = new Geometry[]{g1, g2};
        GeometryCollection gc = geometryFactory.createGeometryCollection(garray);
        return gc;
    }

    /**
     * create a Circle  创建一个圆，圆心(x,y) 半径RADIUS
     *
     * @param x
     * @param y
     * @param RADIUS
     * @return
     */
    public Polygon createCircle(double x, double y, final double RADIUS) {
        final int SIDES = 32;//圆上面的点个数
        Coordinate coords[] = new Coordinate[SIDES + 1];
        for (int i = 0; i < SIDES; i++) {
            double angle = ((double) i / (double) SIDES) * Math.PI * 2.0;
            double dx = Math.cos(angle) * RADIUS;
            double dy = Math.sin(angle) * RADIUS;
            coords[i] = new Coordinate((double) x + dx, (double) y + dy);
        }
        coords[SIDES] = coords[0];
        LinearRing ring = geometryFactory.createLinearRing(coords);
        Polygon polygon = geometryFactory.createPolygon(ring, null);
        return polygon;
    }
}
