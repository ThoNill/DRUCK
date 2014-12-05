package tester;

import java.util.Vector;

import toni.druck.core2.Element;

public class TestRendererItem {

	String element;
	String text;
	boolean visible;

	int posx;
	int posy;
	int width;
	int height;
	
	
	public TestRendererItem() {
		super();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String toString() {
		return "TestRendererItem [element=" + element + ", text=" + text
				+ ", visible=" + visible + ", posx=" + posx + ", posy=" + posy
				+ ", width=" + width + ", height=" + height + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + height;
		result = prime * result + (visible ? 1231 : 1237);
		result = prime * result + posx;
		result = prime * result + posy;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestRendererItem other = (TestRendererItem) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		if (height != other.height)
			return false;
		if (visible != other.visible)
			return false;
		if (posx != other.posx)
			return false;
		if (posy != other.posy)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getElementName() {
		return element;
	}
	
	
	public void setElement(Element element) {
		this.element = element.getName();
		this.visible = element.isEnabled();
	}
	
	public void setElementName(String element) {
		this.element = element;
	}
	
	public void setPosition(int posx,int posy) {
		this.posx = posx;
		this.posy = posy;
	}
	
	public void setDimension(int width,int height) {
		this.width = width;
		this.height = height;
	}
	
	
}
