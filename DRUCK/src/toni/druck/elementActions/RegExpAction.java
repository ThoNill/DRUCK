package toni.druck.elementActions;

import java.util.regex.Pattern;

public class RegExpAction extends OnOffAction {
	private Pattern ifRe = null;
	public RegExpAction() {
	}

	public String getIfRe() {
		return ifRe.pattern();
	}

	public void setIfRe(String ifRe) {
		this.ifRe = Pattern.compile(ifRe);
	}

	
	protected boolean isOn(String value) {
		return ifRe.matcher(value).matches();
	}


}