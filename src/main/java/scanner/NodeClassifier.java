package scanner;

/**
 * Created by Wit on 2015-09-13.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeClassifier {

    private NodeClassifier() {
    }

    public static Map<String, Integer> getValuesForTag(List<OSMNode> nodes, String tagKey) {
        Map<String, Integer> tags = new HashMap<String, Integer>();
        for (OSMNode node : nodes) {
            String key = node.getTagValue(tagKey);
            if (key != null) {
                if (tags.containsKey(key)) {
                    tags.put(key, tags.get(key) + 1);
                } else {
                    tags.put(key, 1);
                }
            }
        }
        return tags;
    }

}
