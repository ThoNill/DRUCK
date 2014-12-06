package toni.druck.helper;

import java.text.NumberFormat;
import java.util.Locale;

public class GetNumberFormat extends ThreadLocal<NumberFormat> {
	public static GetNumberFormat format = new GetNumberFormat();

	@Override
	protected NumberFormat initialValue() {
		NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
		nf.setMinimumFractionDigits(2);
		nf.setMinimumIntegerDigits(1);
		nf.setGroupingUsed(true);
		return nf;
	}
}
