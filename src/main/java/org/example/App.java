package org.example;

import com.google.gson.Gson;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws ParseException, IOException {
//        System.out.println("Hello World!");
//        String s1 = new ReadJarData().jarDataRead("/11.json");
//        String s = ReadJarData.jarDataReadStatic("/11.json");
//        System.out.println(s1);
//        System.out.println(s);

        GeometryUtil geometryDemo = new GeometryUtil();
        Polygon polygon = geometryDemo.createPolygonByWKT();
        MultiPolygon mulPolygonByWKT = geometryDemo.createMulPolygonByWKT();
        Point point = geometryDemo.createPoint(1, 1);
        boolean b1 = mulPolygonByWKT.equalsExact(point);
        boolean b = point.equalsExact(polygon);
        System.out.println(polygon);
        String s1 = new ReadJarData().jarDataRead("/110100.json");
        ProvMapdata provMapdata = new Gson().fromJson(s1, ProvMapdata.class);
        GeometryData geometry1 = provMapdata.getFeatures().get(0).getGeometry();

        GeometryJSON gjson2 = new GeometryJSON();
        Reader reader = new StringReader(new Gson().toJson(geometry1));
        Geometry geometry2 = gjson2.read(reader);

        point.within(geometry2);

        String s = geometry2.toText();

        String ret = null;
        try {


            GeometryJSON gjson = new GeometryJSON();
            Geometry geometry = gjson.read(s1);
            StringWriter writer = new StringWriter();
            GeometryJSON g = new GeometryJSON();
            g.write(geometry, writer);
            ret = writer.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ret);
    }
}
