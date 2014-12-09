/**
 */
package Daten;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see Daten.DatenFactory
 * @model kind="package"
 * @generated
 */
public interface DatenPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Daten";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "tho.nill.daten";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "n";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DatenPackage eINSTANCE = Daten.impl.DatenPackageImpl.init();

	/**
	 * The meta object id for the '{@link Daten.impl.PositionBoxImpl <em>Position Box</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Daten.impl.PositionBoxImpl
	 * @see Daten.impl.DatenPackageImpl#getPositionBox()
	 * @generated
	 */
	int POSITION_BOX = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX__NAME = 0;

	/**
	 * The feature id for the '<em><b>Filled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX__FILLED = 1;

	/**
	 * The feature id for the '<em><b>Bordered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX__BORDERED = 2;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX__WIDTH = 3;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX__HEIGHT = 4;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX__X = 5;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX__Y = 6;

	/**
	 * The number of structural features of the '<em>Position Box</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Position Box</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_BOX_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link Daten.PositionBox <em>Position Box</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Position Box</em>'.
	 * @see Daten.PositionBox
	 * @generated
	 */
	EClass getPositionBox();

	/**
	 * Returns the meta object for the attribute '{@link Daten.PositionBox#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see Daten.PositionBox#getName()
	 * @see #getPositionBox()
	 * @generated
	 */
	EAttribute getPositionBox_Name();

	/**
	 * Returns the meta object for the attribute '{@link Daten.PositionBox#isFilled <em>Filled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Filled</em>'.
	 * @see Daten.PositionBox#isFilled()
	 * @see #getPositionBox()
	 * @generated
	 */
	EAttribute getPositionBox_Filled();

	/**
	 * Returns the meta object for the attribute '{@link Daten.PositionBox#isBordered <em>Bordered</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bordered</em>'.
	 * @see Daten.PositionBox#isBordered()
	 * @see #getPositionBox()
	 * @generated
	 */
	EAttribute getPositionBox_Bordered();

	/**
	 * Returns the meta object for the attribute '{@link Daten.PositionBox#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see Daten.PositionBox#getWidth()
	 * @see #getPositionBox()
	 * @generated
	 */
	EAttribute getPositionBox_Width();

	/**
	 * Returns the meta object for the attribute '{@link Daten.PositionBox#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see Daten.PositionBox#getHeight()
	 * @see #getPositionBox()
	 * @generated
	 */
	EAttribute getPositionBox_Height();

	/**
	 * Returns the meta object for the attribute '{@link Daten.PositionBox#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see Daten.PositionBox#getX()
	 * @see #getPositionBox()
	 * @generated
	 */
	EAttribute getPositionBox_X();

	/**
	 * Returns the meta object for the attribute '{@link Daten.PositionBox#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see Daten.PositionBox#getY()
	 * @see #getPositionBox()
	 * @generated
	 */
	EAttribute getPositionBox_Y();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DatenFactory getDatenFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link Daten.impl.PositionBoxImpl <em>Position Box</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Daten.impl.PositionBoxImpl
		 * @see Daten.impl.DatenPackageImpl#getPositionBox()
		 * @generated
		 */
		EClass POSITION_BOX = eINSTANCE.getPositionBox();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_BOX__NAME = eINSTANCE.getPositionBox_Name();

		/**
		 * The meta object literal for the '<em><b>Filled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_BOX__FILLED = eINSTANCE.getPositionBox_Filled();

		/**
		 * The meta object literal for the '<em><b>Bordered</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_BOX__BORDERED = eINSTANCE.getPositionBox_Bordered();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_BOX__WIDTH = eINSTANCE.getPositionBox_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_BOX__HEIGHT = eINSTANCE.getPositionBox_Height();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_BOX__X = eINSTANCE.getPositionBox_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_BOX__Y = eINSTANCE.getPositionBox_Y();

	}

} //DatenPackage
