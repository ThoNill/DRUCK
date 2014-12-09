package datengraphiti.diagram;

import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;

import Daten.PositionBox;

public class PositionBoxUpdate extends UpdateHelper {

	public PositionBoxUpdate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(String value, Diagram diagram, Shape shape, Object obj) {
		GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
		switch (value) {
		case "text":
			if (ga instanceof Text && obj instanceof PositionBox) {
				Text text = (Text) ga;
				PositionBox box = (PositionBox) obj;
				text.setValue(box.getName());
			}
			break;
		case "filled":
			if (ga instanceof RoundedRectangle && obj instanceof PositionBox) {
				RoundedRectangle roundedRectangle = (RoundedRectangle) ga;
				PositionBox box = (PositionBox) obj;

				setPropertyValue(shape, "filled", box.isFilled());
				setPropertyValue(shape, "bordered", box.isBordered());

				roundedRectangle.setFilled(true);
				if (box.isFilled()) {
					roundedRectangle
							.setBackground(getColor(diagram, 0, 200, 0));
				} else {
					roundedRectangle.setBackground(getColor(diagram, 250, 250,
							250));
				}
				roundedRectangle.setLineVisible(box.isBordered());
				if (box.isBordered()) {
					roundedRectangle
							.setLineWidth(5);
				} else {
					roundedRectangle
					.setLineWidth(1);
					
				}
			}
			break;
		}
	}

	@Override
	public String getUpdateReason(String value, Shape shape, Object obj) {
		GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();

		switch (value) {
		case "text":
			if (ga instanceof Text && obj instanceof PositionBox) {
				Text text = (Text) ga;
				PositionBox box = (PositionBox) obj;
				if (text.getValue() == null)
					return "Text updaten";
				if (!text.getValue().equals(box.getName())) {
					return "Text updaten";
				}
			}
			break;
		case "filled":
			if (obj instanceof PositionBox) {
				PositionBox box = (PositionBox) obj;

				if (!(hasPropertyValue(shape, "filled", box.isFilled())
						&& hasPropertyValue(shape, "bordered", box.isBordered()))) {

					return "updaten";
				}
			}
			break;
		}
		return null;
	}
}
