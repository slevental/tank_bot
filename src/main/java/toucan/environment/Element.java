package toucan.environment;

import java.io.Serializable;

public class Element implements Serializable{
    protected final ElementType type;

    public Element(ElementType type) {
        this.type = type;
    }

    public ElementType getType() {
        return type;
    }

    public Element copy() {
        return new Element(type);
    }
}
