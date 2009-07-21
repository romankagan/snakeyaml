/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.yaml.snakeyaml;

import java.io.InputStream;
import java.io.StringReader;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.resolver.Resolver;

/**
 * Convenience utility to parse JavaBeans. All the methods are Thread safe. When
 * the YAML document contains a global tag with the class definition like
 * '!!com.package.MyBean' it is ignored in favour of the runtime class
 * <code>T</code>.
 * 
 * @see http://www.artima.com/weblogs/viewpost.jsp?thread=208860
 */
public class JavaBeanLoader<T> {
    private Loader loader;

    public JavaBeanLoader(TypeDescription typeDescription) {
        Constructor constructor = new Constructor(typeDescription.getType());
        typeDescription.setRoot(true);
        constructor.addTypeDescription(typeDescription);
        loader = new Loader(constructor);
        Resolver resolver = new Resolver();
        loader.setResolver(resolver);
    }

    public JavaBeanLoader(Class<? extends T> clazz) {
        this(new TypeDescription(clazz));
    }

    /**
     * Parse the first YAML document in a stream and produce the corresponding
     * JavaBean.
     * 
     * @param yaml
     *            YAML document
     * @return parsed JavaBean
     */
    @SuppressWarnings("unchecked")
    public T load(String yaml) {
        return (T) loader.load(new StringReader(yaml));
    }

    /**
     * Parse the first YAML document in a stream and produce the corresponding
     * JavaBean.
     * 
     * @param io
     *            data to load from (BOM is respected and removed)
     * @return parsed JavaBean
     */
    @SuppressWarnings("unchecked")
    public T load(InputStream io) {
        return (T) loader.load(new UnicodeReader(io));
    }

    /**
     * Parse the first YAML document in a stream and produce the corresponding
     * Java object.
     * 
     * @param io
     *            data to load from (BOM must not be present)
     * @return parsed JavaBean
     */
    @SuppressWarnings("unchecked")
    public T load(java.io.Reader io) {
        return (T) loader.load(io);
    }

}
