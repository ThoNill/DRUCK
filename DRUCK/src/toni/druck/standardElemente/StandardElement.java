package toni.druck.standardElemente;

import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import toni.druck.elementRenderer.ElementRenderer;
import toni.druck.helper.ClassFactory;
import toni.druck.model.DataModel;
import toni.druck.page.DataItem;
import toni.druck.page.Element;
import toni.druck.page.ElementAction;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Standardimplementierung der {@link Element} Schnittstelle
 * 
 * 
 */
public class StandardElement implements Element {
    private int width = 0;
    private int height = 0;
    private String name;

    private Page page;

    protected Element parent;

    protected Vector<Element> childs = null;

    private int relX = 0;
    private int relY = 0;

    private String font;
    private int fontsize = 0;

    private String align = "l";

    private int grayscale = 100;

    private int linewidth = -1;

    private boolean bordered = false;

    private boolean filled = false;

    private String text = null;

    private int borderWidth;

    private int paddingX;
    private int paddingY;

    private boolean printable = true;

    private boolean pageShiftHeightEnabled = false;
    private int pageShiftHeight;

    private int addToTestHeight = 0;

    private ElementRenderer renderer = null;

    private boolean absolute = false; // Absolute Positionierung

    private boolean enabled = true;

    private Vector<ElementAction> prepareBeforPrintActions;

    private int rotation = 0;

    public StandardElement(String name, Page page) {
        super();
        this.name = name;
        this.page = page;
    }

    public boolean isAbsolute() {
        return absolute;
    }

    public void setAbsolute(boolean absolute) {
        this.absolute = absolute;
        if (childs != null) {
            for (Element e : childs) {
                if (e instanceof StandardElement) {
                    ((StandardElement) e).setAbsolute(absolute);
                }
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ElementRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(ElementRenderer renderer) {
        this.renderer = renderer;
    }

    public void setRendererClass(String rendererClassName) {
        if (rendererClassName == null || "".equals(rendererClassName.trim()))
            return;
        this.renderer = (ElementRenderer) ClassFactory.getInstance(
                rendererClassName, ElementRenderer.class);
    }

    public void addBeforPrint(ElementAction action) {
        if (prepareBeforPrintActions == null) {
            prepareBeforPrintActions = new Vector<ElementAction>();
        }
        prepareBeforPrintActions.add(action);
    }

    public void prepareForPrint() {
        if (prepareBeforPrintActions != null) {
            for (ElementAction a : prepareBeforPrintActions) {
                a.prepareForPrint(this);
            }
        }

        if (childs != null) {
            for (Element e : childs) {
                e.prepareForPrint();
            } // end for
        }
    }

    public String getRendererClass() {
        return (this.renderer != null) ? this.renderer.getClass().getName()
                : null;
    }

    public StandardElement() {
        super();
    }

    public void print(PageRenderer out) {
        prepareForPrint();
        if (!isEnabled())
            return;
        out.print(this);
    }

    public void printChilds(PageRenderer out) {
        if (!isEnabled())
            return;
        if (childs != null) {
            for (Element e : childs) {

                out.print(e);

            } // end for
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFontsize() {
        if (fontsize == 0 && getParent() != null) {
            fontsize = getParent().getFontsize();
        }
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public int getGrayscale() {
        return grayscale;
    }

    public void setGrayscale(int grayscale) {
        this.grayscale = grayscale;
    }

    public int getLinewidth() {
        return linewidth;
    }

    public void setLinewidth(int linewidth) {
        this.linewidth = linewidth;
    }

    public boolean isBordered() {
        return bordered;
    }

    public void setBordered(boolean bordered) {
        this.bordered = bordered;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void addChild(Element e) {
        if (childs == null) {
            childs = new Vector<Element>();
        }
        childs.addElement(e);
        if (e instanceof StandardElement) {
            ((StandardElement) e).setAbsolute(absolute);
        }
        e.setParent(this);
    }

    public void addChild(Element... e) {
        for (int i = 0; i < e.length; i++) {
            addChild(e[i]);
        }
    }

    public void berechneGroesse() {
        if (childs != null) {
            for (Element e : childs) {
                e.berechneGroesse();
            }
        }
    }

    public void setzePositionen() {
        if (childs != null) {
            for (Element e : childs) {
                e.setzePositionen();
            }
        }
    }

    public void layout() {
        berechneGroesse();
        setzePositionen();
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public int getRelX() {
        return relX;
    }

    public void setRelX(int relX) {
        this.relX = relX;

    }

    public int getRelY() {
        return relY;
    }

    public void setRelY(int relY) {
        this.relY = relY;

    }

    public DataModel getData() {
        return page.getData();
    }

    public Page getPage() {
        return page;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getPaddingX() {
        return paddingX;
    }

    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
    }

    public int getPaddingY() {
        return paddingY;
    }

    public void setPaddingY(int paddingY) {
        this.paddingY = paddingY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPrintable() {
        return printable;
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public int getTestHeight(DataItem item) {
        return getHeight() + addToTestHeight;
    }

    public void setAddToHeight(int height) {
        addToTestHeight = height;
    }

    public String getName() {
        return name;
    }

    public int shiftY() {
        if (parent != null) {
            return parent.shiftY() + relY;
        }
        return relY;
    }

    public int shiftX() {
        if (parent != null) {
            return parent.shiftX() + relX;
        }
        return relX;
    }

    public int Y() {
        if (absolute) {
            return shiftY();
        } else {
            return page.getPosy() + shiftY();
        }
    }

    public int X() {
        if (absolute) {
            return shiftX();
        } else {
            return page.getPosx() + shiftX();
        }
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void printDefinitions(PageRenderer out) {

        if (childs != null) {

            for (Element e : childs) {
                e.printDefinitions(out);
            } // end for
        }
    }

    public String getFont() {
        return font;
    }

    public Dimension getSize() {
        return new Dimension(getWidth(), getHeight());
    }

    public int getPageShiftHeight() {
        if (pageShiftHeightEnabled)
            return pageShiftHeight;
        return getHeight();
    }

    public void setPageShiftHeight(int pageShiftHeight) {
        pageShiftHeightEnabled = true;
        this.pageShiftHeight = pageShiftHeight;
    }

    public List<Element> getChilds() {
        return childs;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

}