package pl.mobileTraveler.scanner;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Examples {


    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

//        // okolice Lewiatana na Miasteczku Studenckim
//        printTags(50.068847, 19.907652, 0.0003);
//
//        // okolice B1
//        printTags(50.065976 , 19.919423, 0.0003);
//
//        // okolice A0
//        printTags(50.064681 , 19.923256, 0.0003);
//
//        // Wawel
//        printTags(50.054594 , 19.936649, 0.001);

//        example_wawel_1km_range();

        example_walk_Cracow("amenity");

    }

    /**
     * Przykład, który przeszukuje dosyć duży obszar (1km kw.) z placem zamkowym
     * Wawelu jako punktem centralnym
     */
    private static void example_wawel_1km_range() throws IOException, ParserConfigurationException, SAXException {
        List<OSMNode> osmNodesInRange = OSMDataScanner.getOSMNodesInRange(50.054594, 19.936649, 0.009);
        Map<String, Integer> values = NodeClassifier.getValuesForTag(osmNodesInRange, "historic");

        System.out.println("Historic values: ");
        for (Map.Entry<String, Integer> e : values.entrySet()) {
            System.out.println("\t" + e.getKey() + ": " + e.getValue());
        }
    }

    private static void example_walk_Cracow(String tag) throws IOException, ParserConfigurationException, SAXException {
        double range = 0.001;
        System.out.println("Bazylika Mariacka:");
        printTags(50.061719, 19.938798, range, tag); // Bazylika Mariacka
        System.out.println("pomnik Mickiewicza:");
        printTags(50.061512, 19.937985, range, tag); // pomnik Mickiewicza
        System.out.println("kościół Św. Wojciecha:");
        printTags(50.060808, 19.937849, range, tag); // kościół Św. Wojciecha
        System.out.println("Plac Dominikański/Grodzka:");
        printTags(50.059099, 19.937892, range, tag); // Plac Dominikański/Grodzka
        System.out.println("Plac Bernardyński:");
        printTags(50.054041, 19.938162, range, tag); // Plac Bernardyński
        System.out.println("Wawel, wejście:");
        printTags(50.052780, 19.935271, range, tag); // wejście na Wawel
        System.out.println("Wawel, przy zamku:");
        printTags(50.054351, 19.935469, range, tag); // Wawel: przy zamku
        System.out.println("Wawel, plac zamkowy:");
        printTags(50.054488, 19.936639, range, tag); // Wawel: plac zamkowy
    }

    /**
     * @param lat
     * @param lon
     * @param range
     * @param tag
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private static void printTags(double lat, double lon, double range, String tag)
            throws IOException, ParserConfigurationException, SAXException {
        Map<String, Integer> values = NodeClassifier.getValuesForTag(
                OSMDataScanner.getOSMNodesInRange(lat, lon, range), tag);
        for (Map.Entry<String, Integer> e : values.entrySet()) {
            System.out.println("\t" + e.getKey() + ": " + e.getValue());
        }
    }

    public static Map<String, Integer> temp(double lat, double lon, String tag)
            throws ParserConfigurationException, SAXException, IOException {
        return NodeClassifier.getValuesForTag(
                OSMDataScanner.getOSMNodesInRange(lat, lon, 0.003), tag);
    }

    /*
     range value accuracy:
     0.001  ~= 110m
     0.0001 ~= 11m
     */
    private static void printTags(double lat, double lon, double range)
            throws IOException, SAXException, ParserConfigurationException {

        List<OSMNode> osmNodesInRange = OSMDataScanner.getOSMNodesInRange(lat, lon, range);
        for (OSMNode osmNode : osmNodesInRange) {
            if (osmNode.hasTags()) {
                System.out.println(osmNode);
            }
        }
    }

}
