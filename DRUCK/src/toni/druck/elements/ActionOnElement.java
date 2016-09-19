package toni.druck.elements;

import toni.druck.helper.ClassFactory;
import toni.druck.page.ElementAction;

public class ActionOnElement {
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ElementAction createElementAction() {
        return (ElementAction) ClassFactory.getInstance(className,
                ElementAction.class);
    }
}
