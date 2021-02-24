package org.example;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.IOException;
import java.io.StringReader;

public class AAA {
    public static void main(String[] args) throws IOException {
        GeometryJSON gjson2 = new GeometryJSON();

        Geometry read = gjson2.read(new StringReader("{\n" +
                "                \"type\": \"LineString\",\n" +
                "                \"coordinates\": [\n" +
                "                    [\n" +
                "                        120.6584555,\n" +
                "                        30.45144\n" +
                "                    ],\n" +
                "                    [\n" +
                "                        120.1654515,\n" +
                "                        30.54848\n" +
                "                    ]\n" +
                "                ]\n" +
                "            }"));
        System.out.println(read.toText());
    }
}
