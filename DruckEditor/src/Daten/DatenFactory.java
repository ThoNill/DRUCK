/**
 */
package Daten;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see Daten.DatenPackage
 * @generated
 */
public interface DatenFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DatenFactory eINSTANCE = Daten.impl.DatenFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Position Box</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Position Box</em>'.
	 * @generated
	 */
	PositionBox createPositionBox();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DatenPackage getDatenPackage();

} //DatenFactory
