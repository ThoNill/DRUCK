package datengraphiti.properties;

import org.eclipse.emf.common.notify.Adapter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import Daten.DatenFactory;
import Daten.PositionBox;

public class PropertiesSection extends GFPropertySection implements
		ITabbedPropertyConstants, KeyListener, SelectionListener, Adapter {
	/*
	 * private Text nameText; private Button gefuellt; private Button mitRand;
	 * private Text width;
	 */

	Control controls[] = null;
	String controlLabels[] = null;
	EClass clazz;
	PositionBox bereich;
	int updateZaehler = 0;

	private EClass getEClass() {
		if (clazz == null) {
			clazz = DatenFactory.eINSTANCE.createPositionBox().eClass();
		}
		return clazz;
	}

	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		Composite composite = factory.createFlatFormComposite(parent);
		/*
		 * controls[0] = factory.createText(composite, ""); controls[1] =
		 * factory.createButton(composite, "gef�llt", SWT.CHECK); controls[2] =
		 * factory.createButton(composite, "mitRand", SWT.CHECK); controls[3] =
		 * factory.createText(composite, "");
		 */
		createControls(factory, getEClass(), composite);

		for (int i = 0; i < controls.length; i++) {
			layoutComponent(factory, composite, controls[i], 10 * i,
					controlLabels[i], 200);
		}

	}

	private void createControls(TabbedPropertySheetWidgetFactory factory,
			EClass clazz, Composite composite) {
		controls = new Control[clazz.getEAllAttributes().size()];
		controlLabels = new String[clazz.getEAllAttributes().size()];

		int index = 0;
		for (EAttribute attribute : clazz.getEAllAttributes()) {
			createAttributeField(factory, composite, index, attribute);
			index++;
		}
	}

	private void createAttributeField(TabbedPropertySheetWidgetFactory factory,
			Composite composite, int index, EAttribute attribute) {
		switch (attribute.getEType().getName()) {
		case "EString": {
			Text nameText = factory.createText(composite, "");
			nameText.addKeyListener(this);
			controls[index] = nameText;
		}
			break;
		case "EBoolean": {
			Button button = factory.createButton(composite, "", SWT.CHECK);
			button.addSelectionListener(this);
			controls[index] = button;
		}
			break;
		case "EInt": {
			Text nameText = factory.createText(composite, "");

			nameText.addKeyListener(this);
			controls[index] = nameText;
		}
			break;
		}
		controlLabels[index] = attribute.getName();
	}

	private void layoutComponent(TabbedPropertySheetWidgetFactory factory,
			Composite composite, Control comp, int ypos, String name, int breite) {
		FormData data;
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(breite, 0);
		data.top = new FormAttachment(ypos, VSPACE);
		comp.setLayoutData(data);

		CLabel valueLabel = factory.createCLabel(composite, name);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(comp, -HSPACE);
		data.top = new FormAttachment(comp, 0, SWT.CENTER);
		valueLabel.setLayoutData(data);
	}

	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

	@Override
	public void refresh() {
		PictogramElement pe = getSelectedPictogramElement();
		if (pe != null) {
			PositionBox neuerBereich = (PositionBox) Graphiti.getLinkService()
					.getBusinessObjectForLinkedPictogramElement(pe);
			setBereich(neuerBereich);
			if (bereich == null)
				return;
			fromModelToGui();

		}
	}

	private void setBereich(PositionBox neuerBereich) {
		if (neuerBereich != bereich) {
			if (bereich != null) {
				bereich.eAdapters().remove(this);
			}
			if (neuerBereich != null) {
				neuerBereich.eAdapters().add(this);
			}
		}
		bereich = neuerBereich;
	}

	private void fromModelToGui() {
		if (updateZaehler > 0) {
			updateZaehler--;
			return;
		}
		int i = 0;
		for (EAttribute attribute : bereich.eClass().getEAllAttributes()) {
			fromModelToGui(controls[i], attribute);
			i++;
		}
		/*
		 * PictogramElement pic = getSelectedPictogramElement();
		 * GraphicsAlgorithm ga = pic.getGraphicsAlgorithm();
		 * ga.setX(bereich.getX()); ga.setY(bereich.getY());
		 * ga.setWidth(bereich.getWidth()); ga.setHeight(bereich.getHeight());
		 */
	}

	private void fromModelToGui(Control control, EAttribute attribute) {
		if (control == null)
			return;

		Object newValue = bereich.eGet(attribute);
		if (control instanceof Text) {
			((Text) control).setText(newValue.toString());
		}
		if (control instanceof Button && newValue instanceof Boolean) {
			((Button) control).setSelection((Boolean) newValue);
		}

	}

	@Override
	public void keyPressed(KeyEvent ev) {
		if (ev.character == SWT.CR) {
			updateModel();
		}
	}

	private void updateModel() {
		if (bereich == null)
			return;
		TransactionalEditingDomain domain = TransactionUtil
				.getEditingDomain(bereich);

		if (domain != null) {
			domain.getCommandStack().execute(new UpdateCommand(domain, this));
		} else {
			fromGuiToModel();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	public void fromGuiToModel() {
		updateZaehler++;
		int index = 0;
		for (EAttribute attribute : bereich.eClass().getEAllAttributes()) {
			Control control = controls[index];

			fromGuiToModel(attribute, control);
			index++;

		}

		/*
		 * PictogramElement pic = getSelectedPictogramElement();
		 * GraphicsAlgorithm ga = pic.getGraphicsAlgorithm(); if (bereich.getX()
		 * != ga.getX()) { bereich.setX(ga.getX()); } if (bereich.getY() !=
		 * ga.getY()) { bereich.setY(ga.getY()); } if (bereich.getWidth() !=
		 * ga.getWidth()) { bereich.setWidth(ga.getWidth()); } if
		 * (bereich.getHeight() != ga.getHeight()) {
		 * bereich.setHeight(ga.getHeight()); }
		 */

	}

	private void fromGuiToModel(EAttribute attribute, Control control) {
		if (control == null)
			return;

		Object newValue = "";
		switch (attribute.getEType().getName()) {
		case "EString": {
			newValue = ((Text) control).getText();
			if (newValue == null || "".equals(newValue)) {
				newValue = 0;
			}
		}
			break;
		case "EBoolean": {
			newValue = ((Button) control).getSelection();
		}
			break;
		case "EInt": {
			newValue = ((Text) control).getText();
			if (newValue == null || "".equals(newValue)) {
				newValue = 0;
			} else {
				newValue = new Integer(newValue.toString());
			}
		}
			break;
		}
		Object oldValue = bereich.eGet(attribute);
		if (oldValue != null) {
			if (!oldValue.equals(newValue)) {
				bereich.eSet(attribute, newValue);
			}
		} else {
			if (newValue != null) {
				bereich.eSet(attribute, newValue);
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// updateModel();

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		if (arg0.widget instanceof Button) {
			updateModel();
		}

	}

	private final class UpdateCommand extends RecordingCommand {
		private final PropertiesSection section;

		private UpdateCommand(TransactionalEditingDomain domain,
				PropertiesSection section) {
			super(domain);
			this.section = section;
		}

		@Override
		public void doExecute() {
			section.fromGuiToModel();
		}
	}

	@Override
	public void notifyChanged(Notification notification) {
		fromModelToGui();
	}

	@Override
	public Notifier getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAdapterForType(Object type) {
		// TODO Auto-generated method stub
		return false;
	}

}
