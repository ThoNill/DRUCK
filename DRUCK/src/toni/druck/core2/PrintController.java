package toni.druck.core2;

public interface PrintController {

	void prepareForPrint();
	
	void print(PageRenderer out);

	int getTestHeight(DataItem item);

	int getPageShiftHeight();

	boolean isPrintable();

	boolean isEnabled();

}