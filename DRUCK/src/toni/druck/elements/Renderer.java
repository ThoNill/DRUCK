package toni.druck.elements;

import toni.druck.elementRenderer.ElementRenderer;
import toni.druck.helper.ClassFactory;

public class Renderer {
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ElementRenderer createElementRenderer() {
        return (ElementRenderer) ClassFactory.getInstance(className,
                ElementRenderer.class);
    }
}
