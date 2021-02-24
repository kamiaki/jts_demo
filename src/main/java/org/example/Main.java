package org.example;

import com.google.gson.Gson;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) throws IOException {
        String s1 = new ReadJarData().jarDataRead("/china.json");
        GeoJsonObj geoJsonObj = new Gson().fromJson(s1, GeoJsonObj.class);
        GeoJsonObj.Features.GeometryBean geometry = geoJsonObj.getFeatures().get(0).getGeometry();
        GeometryJSON gjson2 = new GeometryJSON();
        Reader reader = new StringReader(new Gson().toJson(geometry));
        Geometry geometry2 = gjson2.read(reader);
        System.out.println(geometry2.toText());
    }
}
