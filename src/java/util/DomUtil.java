package util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomUtil {

    public static void trimWhitespace(Node node)
    {
        NodeList children = node.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                child.getParentNode().removeChild(child);
            }
            trimWhitespace(child);
        }
    }


}
