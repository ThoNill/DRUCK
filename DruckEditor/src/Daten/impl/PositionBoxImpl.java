/**
 */
package Daten.impl;

import Daten.DatenPackage;
import Daten.PositionBox;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Position Box</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link Daten.impl.PositionBoxImpl#getName <em>Name</em>}</li>
 *   <li>{@link Daten.impl.PositionBoxImpl#isFilled <em>Filled</em>}</li>
 *   <li>{@link Daten.impl.PositionBoxImpl#isBordered <em>Bordered</em>}</li>
 *   <li>{@link Daten.impl.PositionBoxImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link Daten.impl.PositionBoxImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link Daten.impl.PositionBoxImpl#getX <em>X</em>}</li>
 *   <li>{@link Daten.impl.PositionBoxImpl#getY <em>Y</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PositionBoxImpl extends MinimalEObjectImpl.Container implements PositionBox {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "NN";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isFilled() <em>Filled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFilled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FILLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFilled() <em>Filled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFilled()
	 * @generated
	 * @ordered
	 */
	protected boolean filled = FILLED_EDEFAULT;

	/**
	 * The default value of the '{@link #isBordered() <em>Bordered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBordered()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BORDERED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBordered() <em>Bordered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBordered()
	 * @generated
	 * @ordered
	 */
	protected boolean bordered = BORDERED_EDEFAULT;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected int height = HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final int X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected int x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final int Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected int y = Y_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PositionBoxImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DatenPackage.Literals.POSITION_BOX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatenPackage.POSITION_BOX__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFilled() {
		return filled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFilled(boolean newFilled) {
		boolean oldFilled = filled;
		filled = newFilled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatenPackage.POSITION_BOX__FILLED, oldFilled, filled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBordered() {
		return bordered;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBordered(boolean newBordered) {
		boolean oldBordered = bordered;
		bordered = newBordered;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatenPackage.POSITION_BOX__BORDERED, oldBordered, bordered));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatenPackage.POSITION_BOX__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeight(int newHeight) {
		int oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatenPackage.POSITION_BOX__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setX(int newX) {
		int oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatenPackage.POSITION_BOX__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setY(int newY) {
		int oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DatenPackage.POSITION_BOX__Y, oldY, y));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DatenPackage.POSITION_BOX__NAME:
				return getName();
			case DatenPackage.POSITION_BOX__FILLED:
				return isFilled();
			case DatenPackage.POSITION_BOX__BORDERED:
				return isBordered();
			case DatenPackage.POSITION_BOX__WIDTH:
				return getWidth();
			case DatenPackage.POSITION_BOX__HEIGHT:
				return getHeight();
			case DatenPackage.POSITION_BOX__X:
				return getX();
			case DatenPackage.POSITION_BOX__Y:
				return getY();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DatenPackage.POSITION_BOX__NAME:
				setName((String)newValue);
				return;
			case DatenPackage.POSITION_BOX__FILLED:
				setFilled((Boolean)newValue);
				return;
			case DatenPackage.POSITION_BOX__BORDERED:
				setBordered((Boolean)newValue);
				return;
			case DatenPackage.POSITION_BOX__WIDTH:
				setWidth((Integer)newValue);
				return;
			case DatenPackage.POSITION_BOX__HEIGHT:
				setHeight((Integer)newValue);
				return;
			case DatenPackage.POSITION_BOX__X:
				setX((Integer)newValue);
				return;
			case DatenPackage.POSITION_BOX__Y:
				setY((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DatenPackage.POSITION_BOX__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DatenPackage.POSITION_BOX__FILLED:
				setFilled(FILLED_EDEFAULT);
				return;
			case DatenPackage.POSITION_BOX__BORDERED:
				setBordered(BORDERED_EDEFAULT);
				return;
			case DatenPackage.POSITION_BOX__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case DatenPackage.POSITION_BOX__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case DatenPackage.POSITION_BOX__X:
				setX(X_EDEFAULT);
				return;
			case DatenPackage.POSITION_BOX__Y:
				setY(Y_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DatenPackage.POSITION_BOX__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DatenPackage.POSITION_BOX__FILLED:
				return filled != FILLED_EDEFAULT;
			case DatenPackage.POSITION_BOX__BORDERED:
				return bordered != BORDERED_EDEFAULT;
			case DatenPackage.POSITION_BOX__WIDTH:
				return width != WIDTH_EDEFAULT;
			case DatenPackage.POSITION_BOX__HEIGHT:
				return height != HEIGHT_EDEFAULT;
			case DatenPackage.POSITION_BOX__X:
				return x != X_EDEFAULT;
			case DatenPackage.POSITION_BOX__Y:
				return y != Y_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (Name: ");
		result.append(name);
		result.append(", Filled: ");
		result.append(filled);
		result.append(", Bordered: ");
		result.append(bordered);
		result.append(", Width: ");
		result.append(width);
		result.append(", Height: ");
		result.append(height);
		result.append(", X: ");
		result.append(x);
		result.append(", Y: ");
		result.append(y);
		result.append(')');
		return result.toString();
	}

} //PositionBoxImpl
