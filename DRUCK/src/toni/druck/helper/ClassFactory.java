package toni.druck.helper;

import org.apache.log4j.Logger;

/**
 * Objekt-Fabrik, die auftretende Exceptions zentral abfängt.
 * 
 * @author THOMAS NILL
 * 
 */
public class ClassFactory {
	static Logger logger = Logger.getLogger(ClassFactory.class.getSimpleName());

	static public Object getInstance(String classname) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Create object of class [" + classname + "]");
			}
			Class cl = ClassFactory.class.getClassLoader().loadClass(classname);
			Object obj = cl.newInstance();
			return obj;

		} catch (ClassNotFoundException e) {
			logger.fatal("class [" + classname + "] not found");
		} catch (InstantiationException e) {
			logger.fatal("class [" + classname + "] can not be instantated");
		} catch (IllegalAccessException e) {
			logger.fatal("class [" + classname
					+ "] Illegal Access at instantiation");
		}
		return null;
	}

	static public Object getInstance(Class cl) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Create object of class [" + cl.getName() + "]");
			}
			Object obj = cl.newInstance();
			return obj;

		} catch (InstantiationException e) {
			logger.fatal("class [" + cl.getName() + "] can not be instantated");
		} catch (IllegalAccessException e) {
			logger.fatal("class [" + cl.getName()
					+ "] Illegal Access at instantiation");
		}
		return null;
	}

	static public Object getInstance(String classname, Class check) {
		Object o = getInstance(classname);
		if (!check.isInstance(o)) {
			logger.fatal("class [" + classname + "] is not of Type "
					+ check.getName());
			return null;
		}
		return o;
	}

	static public Class getClass(String classname) {
		Class c = null;
		try {
			c = ClassFactory.class.getClassLoader().loadClass(classname);
		} catch (ClassNotFoundException e) {
			logger.fatal("class [" + classname + "] not found");
		}
		return c;
	}
}
