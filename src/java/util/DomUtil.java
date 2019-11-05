package util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.stream.Stream;

public class DomUtil {

    public static void trimWhitespace(Node node)
    {
        NodeList children = node.getChildNodes();

        Stream.iterate(0, (k) -> k+1).limit(children.getLength())
                .map(children::item)
                .filter(c -> c.getNodeType() == Node.TEXT_NODE)
                .forEach(c -> c.getParentNode().removeChild(c));

        Stream.iterate(0, (k) -> k+1).limit(children.getLength())
                .map(children::item)
                .filter(c -> c.getNodeType() != Node.TEXT_NODE)
                .forEach(DomUtil::trimWhitespace);

    }


}
