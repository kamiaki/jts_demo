package org.example;

import com.google.gson.Gson;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.IOException;
import java.util.List;

public class GeometryData {


    private String type;

    private List coordinates;

    public String getType() {
        return type;
    }


    public List getCoordinates() {
        return coordinates;
    }


    public void setCoordinates(List coordinates) {
        this.coordinates = coordinates;
    }


    public void setType(String type) {
        this.type = type;
    }



    public Geometry toGeometry() throws IOException {
        String provJsonString = new Gson().toJson(this);
        GeometryJSON gjson = new GeometryJSON();
        return gjson.read(provJsonString);
    }


}
