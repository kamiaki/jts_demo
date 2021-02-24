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

import java.util.LinkedList;
import java.util.List;

/**
 * Geometry 工具类
 * 判断两个几何图形是否存在指定的空间关系。包括：
 * <p>
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
     */
    public static void main(String[] args) throws ParseException {
        Coordinate coordinate = new Coordinate(1, 1);
        Point point = createPoint(coordinate);
        Point pointByWKT = createPointByWKT("POINT (109.013388 32.715519)");
        MultiPoint mulPointByWKT = createMulPointByWKT("MULTIPOINT(109.013388 32.715519,119.32488 31.435678)");
        Coordinate[] coords = new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 2)};
        LineString line = createLine(coords);

        System.out.println(point);
        System.out.println(pointByWKT);
        System.out.println(mulPointByWKT);
        System.out.println(line);

        LineString lineByWKT = createLineByWKT("LINESTRING(0 0, 2 0)");
        List<Coordinate[]> List = new LinkedList<>();
        Coordinate[] coords1 = new Coordinate[]{new Coordinate(2, 1), new Coordinate(4, 2)};
        Coordinate[] coords2 = new Coordinate[]{new Coordinate(3, 2), new Coordinate(1, 2)};
        List.add(coords1);
        List.add(coords2);
        MultiLineString mLine = createMLine(List);
        System.out.println(lineByWKT);
        System.out.println(mLine);

        MultiLineString mLineByWKT = createMLineByWKT("MULTILINESTRING((0 0, 2 0),(1 1,2 2))");
        Polygon polygonByWKT = createPolygonByWKT("POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))");
        System.out.println(mLineByWKT);
        System.out.println(polygonByWKT);

        MultiPolygon mulPolygonByWKT = createMulPolygonByWKT("MULTIPOLYGON(((40 10, 30 0, 40 10, 30 20, 40 10),(30 10, 30 0, 40 10, 30 20, 30 10)))");
        System.out.println(mulPolygonByWKT);

        Coordinate[] coords3 = new Coordinate[]{new Coordinate(1, 2), new Coordinate(1, 2)};
        LineString line2 = createLine(coords3);
        Polygon poly = createPolygonByWKT("POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))");
        Geometry g1 = geometryFactory.createGeometry(line2);
        Geometry g2 = geometryFactory.createGeometry(poly);
        Geometry[] garray = new Geometry[]{g1, g2};
        GeometryCollection geoCollect = createGeoCollect(garray);
        System.out.println(geoCollect);

        Polygon circle = createCircle(0, 0, 20, 32);
        System.out.println(circle);
    }

    /**
     * create a point
     *
     * @return
     */
    public static Point createPoint(Coordinate coordinate) {
        Coordinate coord = new Coordinate(coordinate.x, coordinate.y);
        Point point = geometryFactory.createPoint(coord);
        return point;
    }

    /**
     * create a point by WKT
     * POINT (109.013388 32.715519)
     *
     * @return
     */
    public static Point createPointByWKT(String str) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        Point point = (Point) reader.read(str);
        return point;
    }

    /**
     * create multiPoint by wkt
     * MULTIPOINT(109.013388 32.715519,119.32488 31.435678)
     *
     * @return
     */
    public static MultiPoint createMulPointByWKT(String str) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        MultiPoint mpoint = (MultiPoint) reader.read(str);
        return mpoint;
    }

    /**
     * create a line
     *
     * @return
     */
    public static LineString createLine(Coordinate[] coords) {
        LineString line = geometryFactory.createLineString(coords);
        return line;
    }

    /**
     * create a line by WKT
     * "LINESTRING(0 0, 2 0)"
     *
     * @return
     * @throws ParseException
     */
    public static LineString createLineByWKT(String str) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        LineString line = (LineString) reader.read(str);
        return line;
    }

    /**
     * create multiLine
     *
     * @return
     */
    public static MultiLineString createMLine(List<Coordinate[]> List) {
        List<LineString> lineStrings = new LinkedList<>();
        List.forEach(x -> lineStrings.add(geometryFactory.createLineString(x)));
        LineString[] lineStrings1 = lineStrings.toArray(new LineString[lineStrings.size()]);
        MultiLineString ms = geometryFactory.createMultiLineString(lineStrings1);
        return ms;
    }

    /**
     * create multiLine by WKT
     *  "MULTILINESTRING((0 0, 2 0),(1 1,2 2))"
     * @return
     * @throws ParseException
     */
    public static MultiLineString createMLineByWKT(String str) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        MultiLineString line = (MultiLineString) reader.read(str);
        return line;
    }

    /**
     * create a polygon(多边形) by WKT
     *  "POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))"
     * @return
     * @throws ParseException
     */
    public static Polygon createPolygonByWKT(String str) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        Polygon polygon = (Polygon) reader.read(str);
        return polygon;
    }

    /**createMulPolygonByWKT
     * create multi polygon by wkt
     *  "MULTIPOLYGON(((40 10, 30 0, 40 10, 30 20, 40 10),(30 10, 30 0, 40 10, 30 20, 30 10)))"
     * @return
     * @throws ParseException
     */
    public static MultiPolygon createMulPolygonByWKT(String str) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        MultiPolygon mpolygon = (MultiPolygon) reader.read(str);
        return mpolygon;
    }

    /**
     * create GeometryCollection  contain point or multiPoint or line or multiLine or polygon or multiPolygon
     *
     * @return
     */
    public static GeometryCollection createGeoCollect(Geometry[] garray) {
        GeometryCollection gc = geometryFactory.createGeometryCollection(garray);
        return gc;
    }

    /**
     * create a Circle  创建一个圆，圆心(x,y) 半径RADIUS
     *
     * @param x
     * @param y
     * @param radius
     * @return
     */
    public static Polygon createCircle(double x, double y, double radius, int count) {
        Coordinate coords[] = new Coordinate[count + 1];
        for (int i = 0; i < count; i++) {
            double angle = ((double) i / (double) count) * Math.PI * 2.0;
            double dx = Math.cos(angle) * radius;
            double dy = Math.sin(angle) * radius;
            coords[i] = new Coordinate(x + dx, y + dy);
        }
        coords[count] = coords[0];  // 首尾相接
        LinearRing ring = geometryFactory.createLinearRing(coords);
        Polygon polygon = geometryFactory.createPolygon(ring, null);
        return polygon;
    }
}
