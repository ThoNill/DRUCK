package toni.druck.helper;

import org.apache.log4j.Logger;

/**
 * Objekt-Fabrik, die auftretende Exceptions zentral abfängt.
 * 
 * @author THOMAS NILL
 * 
 */
public class ClassFactory {
    private static final Logger LOG = Logger.getLogger(ClassFactory.class
            .getSimpleName());

    public ClassFactory() {
        super();
    }

    static public Object getInstance(String classname) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Create object of class [" + classname + "]");
            }
            Class cl = ClassFactory.class.getClassLoader().loadClass(classname);
            Object obj = cl.newInstance();
            return obj;

        } catch (ClassNotFoundException e) {
            LOG.fatal("class [" + classname + "] not found", e);
        } catch (InstantiationException e) {
            LOG.fatal("class [" + classname + "] can not be instantated", e);
        } catch (IllegalAccessException e) {
            LOG.fatal("class [" + classname
                    + "] Illegal Access at instantiation", e);
        }
        return null;
    }

    static public Object getInstance(Class cl) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Create object of class [" + cl.getName() + "]");
            }
            Object obj = cl.newInstance();
            return obj;

        } catch (InstantiationException e) {
            LOG.fatal("class [" + cl.getName() + "] can not be instantated", e);
        } catch (IllegalAccessException e) {
            LOG.fatal("class [" + cl.getName()
                    + "] Illegal Access at instantiation", e);
        }
        return null;
    }

    static public Object getInstance(String classname, Class check) {
        Object o = getInstance(classname);
        if (!check.isInstance(o)) {
            LOG.fatal("class [" + classname + "] is not of Type "
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
            LOG.fatal("class [" + classname + "] not found", e);
        }
        return c;
    }
}
