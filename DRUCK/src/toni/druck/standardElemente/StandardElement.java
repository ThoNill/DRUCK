package toni.druck.standardElemente;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

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

    protected List<Element> childs = null;

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

    private List<ElementAction> prepareBeforPrintActions;

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

    @Override
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
            prepareBeforPrintActions = new ArrayList<ElementAction>();
        }
        prepareBeforPrintActions.add(action);
    }

    @Override
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

    @Override
    public void print(PageRenderer out) {
        prepareForPrint();
        if (!isEnabled())
            return;
        out.print(this);
    }

    @Override
    public void printChilds(PageRenderer out) {
        if (!isEnabled())
            return;
        if (childs != null) {
            for (Element e : childs) {

                out.print(e);

            } // end for
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getFontsize() {
        if (fontsize == 0 && getParent() != null) {
            fontsize = getParent().getFontsize();
        }
        return fontsize;
    }

    @Override
    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    @Override
    public int getGrayscale() {
        return grayscale;
    }

    @Override
    public void setGrayscale(int grayscale) {
        this.grayscale = grayscale;
    }

    @Override
    public int getLinewidth() {
        return linewidth;
    }

    @Override
    public void setLinewidth(int linewidth) {
        this.linewidth = linewidth;
    }

    @Override
    public boolean isBordered() {
        return bordered;
    }

    @Override
    public void setBordered(boolean bordered) {
        this.bordered = bordered;
    }

    @Override
    public boolean isFilled() {
        return filled;
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public void setFont(String font) {
        this.font = font;
    }

    @Override
    public void addChild(Element e) {
        if (childs == null) {
            childs = new ArrayList<Element>();
        }
        childs.add(e);
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

    @Override
    public void berechneGroesse() {
        if (childs != null) {
            for (Element e : childs) {
                e.berechneGroesse();
            }
        }
    }

    @Override
    public void setzePositionen() {
        if (childs != null) {
            for (Element e : childs) {
                e.setzePositionen();
            }
        }
    }

    @Override
    public void layout() {
        berechneGroesse();
        setzePositionen();
    }

    @Override
    public Element getParent() {
        return parent;
    }

    @Override
    public void setParent(Element parent) {
        this.parent = parent;
    }

    @Override
    public int getRelX() {
        return relX;
    }

    @Override
    public void setRelX(int relX) {
        this.relX = relX;

    }

    @Override
    public int getRelY() {
        return relY;
    }

    @Override
    public void setRelY(int relY) {
        this.relY = relY;

    }

    @Override
    public DataModel getData() {
        return page.getData();
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public int getBorderWidth() {
        return borderWidth;
    }

    @Override
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    @Override
    public int getPaddingX() {
        return paddingX;
    }

    @Override
    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
    }

    @Override
    public int getPaddingY() {
        return paddingY;
    }

    @Override
    public void setPaddingY(int paddingY) {
        this.paddingY = paddingY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean isPrintable() {
        return printable;
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    @Override
    public int getTestHeight(DataItem item) {
        return getHeight() + addToTestHeight;
    }

    public void setAddToHeight(int height) {
        addToTestHeight = height;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int shiftY() {
        if (parent != null) {
            return parent.shiftY() + relY;
        }
        return relY;
    }

    @Override
    public int shiftX() {
        if (parent != null) {
            return parent.shiftX() + relX;
        }
        return relX;
    }

    @Override
    public int Y() {
        if (absolute) {
            return shiftY();
        } else {
            return page.getPosy() + shiftY();
        }
    }

    @Override
    public int X() {
        if (absolute) {
            return shiftX();
        } else {
            return page.getPosx() + shiftX();
        }
    }

    @Override
    public String getAlign() {
        return align;
    }

    @Override
    public void setAlign(String align) {
        this.align = align;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public void printDefinitions(PageRenderer out) {

        if (childs != null) {

            for (Element e : childs) {
                e.printDefinitions(out);
            } // end for
        }
    }

    @Override
    public String getFont() {
        return font;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public int getPageShiftHeight() {
        if (pageShiftHeightEnabled)
            return pageShiftHeight;
        return getHeight();
    }

    @Override
    public void setPageShiftHeight(int pageShiftHeight) {
        pageShiftHeightEnabled = true;
        this.pageShiftHeight = pageShiftHeight;
    }

    @Override
    public List<Element> getChilds() {
        return childs;
    }

    @Override
    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

}