package pl.mobileTraveler.scanner;

/**
 * Created by Wit on 2015-09-13.
 */

import java.util.HashMap;
import java.util.Map;

public class OSMNode {
    private String id;
    private String lat;
    private String lon;
    private final Map<String, String> tags;
    private String version;


    public OSMNode() {
        this.tags = new HashMap();
    }

    public OSMNode(String id, String lat, String lon, String version, Map<String, String> tags) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.tags = tags;
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (Map.Entry<String, String> e : tags.entrySet()) {
            stringBuilder
                    .append('\t')
                    .append(e.getKey())
                    .append(": ")
                    .append(e.getValue())
                    .append('\n');
        }
        return this.id + " " + this.lat + " " + this.lon + stringBuilder.toString();

    }

    public boolean hasTags() {
        return !tags.isEmpty();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTagValue(String key) {
        return tags.get(key);
    }

}