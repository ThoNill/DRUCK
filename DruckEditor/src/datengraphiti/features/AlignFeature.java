package datengraphiti.features;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class AlignFeature extends AbstractCustomFeature {
	protected static PictogramElement vorbild;

	public enum AlignCase {
		SET_VORBILD, SET_X, SET_Y, SET_WIDTH, SET_HEIGHT, NEBENEINANDER, UNTEREINANDER
	};

	private AlignCase art;

	private boolean hasDoneChanges = false;

	public AlignFeature(IFeatureProvider fp, AlignCase art) {
		super(fp);
		this.art = art;
	}

	@Override
	public String getName() {
		switch (art) {
		case SET_VORBILD:
			return "Setze Prototyp";
		case SET_X:
			return "Setze X";
		case SET_Y:
			return "Setze Y";
		case SET_WIDTH:
			return "Setze Breite";
		case SET_HEIGHT:
			return "Setze Höhe";
		case NEBENEINANDER:
			return "nebeneinander";
		case UNTEREINANDER:
			return "untereinander";
		}
		return "Unbekannt";
	}

	@Override
	public String getDescription() {
		return "Change the name of the EClass";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (art.equals(AlignCase.SET_VORBILD)) {
			return pes != null && pes.length == 1;
		}
		if (art.equals(AlignCase.NEBENEINANDER)
				|| art.equals(AlignCase.UNTEREINANDER)) {
			return pes.length > 1;
		}
		return vorbild != null && pes != null && pes.length > 1;
	}

	@Override
	public void execute(ICustomContext context) {
		hasDoneChanges = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null) {

			switch (art) {
			case SET_VORBILD:
				vorbild = pes[0];
				break;
			case NEBENEINANDER:
				pes = Arrays.copyOf(pes, pes.length);
				nebeneinander(pes);
				hasDoneChanges = true;
				break;
			case UNTEREINANDER:
				pes = Arrays.copyOf(pes, pes.length);
				untereinander(pes);
				hasDoneChanges = true;
				break;
			default:
				for (PictogramElement element : pes) {
					change(element);
				}
				break;
			}

		}
	}

	private void nebeneinander(PictogramElement[] pes) {
		sortiereNachX(pes);

		int y[] = creatConstantArray(pes[0].getGraphicsAlgorithm().getY(),
				pes.length);
		int[] x = legeNebeneinander(pes);
		setzePictogramme(pes, x, y);
	}

	private void sortiereNachX(PictogramElement[] pes) {
		Arrays.sort(pes, new Comparator<PictogramElement>() {
			@Override
			public int compare(PictogramElement o1, PictogramElement o2) {
				return ((Integer) o1.getGraphicsAlgorithm().getX())
						.compareTo(o2.getGraphicsAlgorithm().getX());
			}
		});
	}

	private int[] legeNebeneinander(PictogramElement[] pes) {
		int[] x = new int[pes.length];
		x[0] = pes[0].getGraphicsAlgorithm().getX();
		for (int i = 1; i < pes.length; i++) {
			x[i] = x[i - 1] + pes[i - 1].getGraphicsAlgorithm().getWidth();
		}
		return x;
	}

	private void untereinander(PictogramElement[] pes) {
		sortiereNachY(pes);
		int x[] = creatConstantArray(pes[0].getGraphicsAlgorithm().getX(),
				pes.length);
		int[] y = legeUntereinander(pes);
		setzePictogramme(pes, x, y);
	}

	private void sortiereNachY(PictogramElement[] pes) {
		Arrays.sort(pes, new Comparator<PictogramElement>() {
			@Override
			public int compare(PictogramElement o1, PictogramElement o2) {
				return ((Integer) o1.getGraphicsAlgorithm().getY())
						.compareTo(o2.getGraphicsAlgorithm().getY());
			}
		});
	}

	private int[] legeUntereinander(PictogramElement[] pes) {
		int[] y = new int[pes.length];
		y[0] = pes[0].getGraphicsAlgorithm().getY();
		for (int i = 1; i < pes.length; i++) {
			y[i] = y[i - 1] + pes[i - 1].getGraphicsAlgorithm().getHeight();
		}
		return y;
	}

	private int[] creatConstantArray(int n, int length) {
		int r[] = new int[length];
		for (int i = 0; i < length; i++) {
			r[i] = n;
		}
		return r;
	}

	private void setzePictogramme(PictogramElement[] pes, int[] x, int[] y) {
		for (int i = 0; i < pes.length; i++) {
			pes[i].getGraphicsAlgorithm().setX(x[i]);
			pes[i].getGraphicsAlgorithm().setY(y[i]);
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}

	public void change(PictogramElement element) {
		GraphicsAlgorithm eg = element.getGraphicsAlgorithm();
		GraphicsAlgorithm vg = vorbild.getGraphicsAlgorithm();
		switch (art) {
		case SET_X:
			eg.setX(vg.getX());
			break;
		case SET_Y:
			eg.setY(vg.getY());
			break;
		case SET_WIDTH:
			eg.setWidth(vg.getWidth());
			break;
		case SET_HEIGHT:
			eg.setHeight(vg.getHeight());
			break;

		}
		hasDoneChanges = true;

	}
}
