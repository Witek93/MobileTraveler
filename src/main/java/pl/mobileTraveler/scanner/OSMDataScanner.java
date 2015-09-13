package pl.mobileTraveler.scanner;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class OSMDataScanner {

    private static final String OPENSTREETMAP_API_06 = "http://www.openstreetmap.org/api/0.6/";


    private static HttpURLConnection openNewURLConnection(URL osm) throws IOException {
        return (HttpURLConnection) osm.openConnection();
    }

    /*
       range value accuracy:
       0.001  ~= 110m
       0.0001 ~= 11m
    */
    private static Document getXML(double lat, double lon, double range) throws IOException, SAXException,
            ParserConfigurationException {

        DecimalFormat format = new DecimalFormat("##0.0000000", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        String left = format.format(lon - range);
        String bottom = format.format(lat - range);
        String right = format.format(lon + range);
        String top = format.format(lat + range);

        String string = OPENSTREETMAP_API_06 + "map?bbox=" + left + "," + bottom + "," + right + ","
                + top;
        URL osm = new URL(string);
        HttpURLConnection connection = openNewURLConnection(osm);

        return initDocumentBuilder().parse(connection.getInputStream());
    }

    private static DocumentBuilder initDocumentBuilder() throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        return docBuilder;
    }


    public static List<OSMNode> getNodes(Document xmlDocument) {
        List<OSMNode> osmNodes = new ArrayList<OSMNode>();

        Node osmRoot = xmlDocument.getFirstChild();
        NodeList osmXMLNodes = osmRoot.getChildNodes();
        for (int i = 1; i < osmXMLNodes.getLength(); i++) {
            Node item = osmXMLNodes.item(i);
            if (item.getNodeName().equals("node")) {
                NamedNodeMap attributes = item.getAttributes();
                NodeList tagXMLNodes = item.getChildNodes();

                Map<String, String> tags = getTags(tagXMLNodes);

                Node namedItemID = attributes.getNamedItem("id");
                Node namedItemLat = attributes.getNamedItem("lat");
                Node namedItemLon = attributes.getNamedItem("lon");
                Node namedItemVersion = attributes.getNamedItem("version");

                String id = namedItemID.getNodeValue();
                String latitude = namedItemLat.getNodeValue();
                String longitude = namedItemLon.getNodeValue();
                String version = "0";
                if (namedItemVersion != null) {
                    version = namedItemVersion.getNodeValue();
                }

                osmNodes.add(new OSMNode(id, latitude, longitude, version, tags));
            }

        }
        return osmNodes;
    }

    private static Map<String, String> getTags(NodeList tagXMLNodes) throws DOMException {
        Map<String, String> tags = new HashMap<String, String>();
        for (int j = 1; j < tagXMLNodes.getLength(); j++) {
            Node tagItem = tagXMLNodes.item(j);
            NamedNodeMap tagAttributes = tagItem.getAttributes();
            if (tagAttributes != null) {
                tags.put(tagAttributes.getNamedItem("k").getNodeValue(), tagAttributes.getNamedItem("v")
                        .getNodeValue());
            }
        }
        return tags;
    }

    /**
     * @param range 0.001  ~= 110m, 0.0001 ~= 11m
     */
    public static List<OSMNode> getOSMNodesInRange(double lat, double lon, double range) throws IOException,
            SAXException, ParserConfigurationException {
        return OSMDataScanner.getNodes(getXML(lat, lon, range));
    }

}
