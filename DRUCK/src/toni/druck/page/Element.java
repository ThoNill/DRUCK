package toni.druck.page;

import java.awt.Dimension;
import java.util.List;

import toni.druck.model.DataModel;


/*****
 * 
 * @author Thomas Nill
 * 
 * Ein Ausgabeelement auf dem Ausdruck, kann Kinder enthalten.
 * Stellt Größenangaben, Positionen, Fonts usw. zur Verfügung
 * 
 * Datentyp
 * 
 */
public interface Element extends PrintController {

	void printChilds(PageRenderer out);

	int getFontsize();

	void setFontsize(int fontsize);

	int getGrayscale();

	void setGrayscale(int grayscale);

	int getLinewidth();

	void setLinewidth(int linewidth);

	boolean isBordered();

	void setBordered(boolean bordered);

	boolean isFilled();

	void setFilled(boolean filled);

	void setFont(String font);

	void addChild(Element e);

	void berechneGroesse();

	void setzePositionen();

	void layout();

	Element getParent();

	void setParent(Element parent);

	int getRelX();

	void setRelX(int relX);

	int getRelY();

	void setRelY(int relY);

	DataModel getData();

	Page getPage();

	int getBorderWidth();

	void setBorderWidth(int borderWidth);

	int getPaddingX();

	void setPaddingX(int paddingX);

	int getPaddingY();

	void setPaddingY(int paddingY);

	int getWidth();

	void setWidth(int width);

	int getHeight();

	void setHeight(int height);

	String getName();

	void setName(String name);

	int shiftY();

	int shiftX();

	int Y();

	int X();

	String getAlign();

	void setAlign(String align);

	String getText();

	void setText(String text);

	void setPage(Page page);

	void printDefinitions(PageRenderer out);

	String getFont();

	Dimension getSize();

	List<Element> getChilds();

	void setPageShiftHeight(int height);
	
	int getRotation();
	
}