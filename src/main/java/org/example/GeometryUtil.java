package org.example;

import com.google.gson.Gson;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.*;
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
    private final static String CHINA = "/china.json";

    /**
     * 在右边 补零到指定位数
     * @param str
     * @param strLength
     * @return
     */
    private static String addZeroForNumRight(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuilder sb = new StringBuilder();
//                sb.append("0").append(str);//   左补0
                sb.append(str).append("0");//   右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 判断点，在那个行政区划
     * return String [[xxxx,省]，[xxxx,市]，[xxxx,县]]
     */
    public static String[][] getPointInPcc(double lon, double lat) throws Exception {
        String[][] pcc = new String[][]{{"", ""}, {"", ""}, {"", ""}};
        GeometryJSON geometryJSON = new GeometryJSON();
        // 创建点
        Point point = createPoint(lon, lat);
        // 获取省区域
        String geoJsonChina = jarDataReadStatic(CHINA);
        if (geoJsonChina == null || geoJsonChina.trim().length() <= 0) return pcc;
        List<GeoJsonObj.Features> featuresProvinces = new Gson().fromJson(geoJsonChina, GeoJsonObj.class).getFeatures();
        // 循环 判断省
        for (GeoJsonObj.Features featuresProvince : featuresProvinces) {
            Geometry geometryProvince = geometryJSON.read(new StringReader(new Gson().toJson(featuresProvince.getGeometry())));
            if (geometryProvince.contains(point)) {
                pcc[0][0] = addZeroForNumRight(featuresProvince.getProperties().getId(), 6);
                pcc[0][1] = featuresProvince.getProperties().getName();
                // 获取 市区域 存着市的省文件不用补0
                String geoJsonCity = jarDataReadStatic(new StringBuilder("/").append(featuresProvince.getProperties().getId()).append(".json").toString());
                if (geoJsonCity == null || geoJsonCity.trim().length() <= 0) break;
                List<GeoJsonObj.Features> featuresCities = new Gson().fromJson(geoJsonCity, GeoJsonObj.class).getFeatures();
                // 循环 判断市
                for (GeoJsonObj.Features featuresCity : featuresCities) {
                    Geometry geometryCity = geometryJSON.read(new StringReader(new Gson().toJson(featuresCity.getGeometry())));
                    if (geometryCity.contains(point)) {
                        pcc[1][0] = addZeroForNumRight(featuresCity.getProperties().getId(), 6);
                        pcc[1][1] = featuresCity.getProperties().getName();
                        // 获取县 区域 存着县的市文件用补0
                        String geoJsonCounty = jarDataReadStatic(new StringBuilder("/").append(pcc[1][0]).append(".json").toString());
                        if (geoJsonCounty == null || geoJsonCounty.trim().length() <= 0) break;
                        List<GeoJsonObj.Features> featuresCounties = new Gson().fromJson(geoJsonCounty, GeoJsonObj.class).getFeatures();
                        // 循环 判断县
                        for (GeoJsonObj.Features featuresCounty : featuresCounties) {
                            Geometry geometryCounty = geometryJSON.read(new StringReader(new Gson().toJson(featuresCounty.getGeometry())));
                            if (geometryCounty.contains(point)) {
                                pcc[2][0] = addZeroForNumRight(featuresCounty.getProperties().getId(), 6);
                                pcc[2][1] = featuresCounty.getProperties().getName();
                                break;
                            }
                        }// 循环 判断县
                    }
                }// 循环 判断省
            }
        }// 循环 判断省
        return pcc;
    }


    /**
     * jar数据读取 静态
     *
     * @param filePath
     * @return
     */
    public static String jarDataReadStatic(String filePath) {
        String dataStr;
        try {
            InputStream inputStream = ReadJarData.class.getResourceAsStream(filePath);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = bReader.readLine()) != null) {
                sb.append(s);
            }
            bReader.close();
            dataStr = sb.toString();
        } catch (Exception e) {
            dataStr = "";
        }
        return dataStr;
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
     * "MULTILINESTRING((0 0, 2 0),(1 1,2 2))"
     *
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
     * "POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))"
     *
     * @return
     * @throws ParseException
     */
    public static Polygon createPolygonByWKT(String str) throws ParseException {
        WKTReader reader = new WKTReader(geometryFactory);
        Polygon polygon = (Polygon) reader.read(str);
        return polygon;
    }

    /**
     * createMulPolygonByWKT
     * create multi polygon by wkt
     * "MULTIPOLYGON(((40 10, 30 0, 40 10, 30 20, 40 10),(30 10, 30 0, 40 10, 30 20, 30 10)))"
     *
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

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String[][] pointInPcc = getPointInPcc(116.410162,39.842012);
        System.out.println(pointInPcc[0][0] + pointInPcc[0][1]);
        System.out.println(pointInPcc[1][0] + pointInPcc[1][1]);
        System.out.println(pointInPcc[2][0] + pointInPcc[2][1]);

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
}
