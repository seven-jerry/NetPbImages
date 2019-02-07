package configuration.loader;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import processing.build.PBMImageBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContentElementIterator implements Iterator<Element> {
    private int currentIndex;
    private List<Node> childNodes = new ArrayList<>();

    public void setParentElement(Element parent) {
        NodeList children = parent.getChildNodes();
        this.childNodes = new ArrayList<>();
        for (int i = 0; i < children.getLength(); ++i) {
            Element child = (Element) children.item(i);
            this.childNodes.add(child);
        }
        this.currentIndex = 0;
    }

    public void addNode(Node node) {
        this.childNodes.add(node);
    }

    @Override
    public boolean hasNext() {
        return this.currentIndex < this.childNodes.size();
    }

    @Override
    public Element next() {
        Node nextElement = this.childNodes.get(this.currentIndex);
        this.currentIndex++;
        return (Element) nextElement;
    }
}
