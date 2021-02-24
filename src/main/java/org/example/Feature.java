package org.example;



public class Feature {

    private String type;

    private Properties properties;

    //geojson 地图 边界
    private GeometryData geometry;


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public GeometryData getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryData geometry) {
        this.geometry = geometry;
    }


    public Properties getProperties() {
        return properties;
    }


    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
