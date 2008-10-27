/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.jvyaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:ola.bini@ki.se">Ola Bini</a>
 */
public class YAMLDumpTest extends TestCase {
    public YAMLDumpTest(final String name) {
        super(name);
    }

    public void testBasicStringDump() {
        assertEquals("--- str\n", YAML.dump("str"));
    }

    public void testBasicHashDump() {
        Map ex = new HashMap();
        ex.put("a", "b");
        assertEquals("--- \na: b\n", YAML.dump(ex));
    }

    public void testBasicListDump() {
        List ex = new ArrayList();
        ex.add("a");
        ex.add("b");
        ex.add("c");
        assertEquals("--- \n- a\n- b\n- c\n", YAML.dump(ex));
    }

    public void testVersionDumps() {
        assertEquals("--- !!int 1\n", YAML.dump(new Integer(1), YAML.config().explicitTypes(true)));
        assertEquals("--- !int 1\n", YAML.dump(new Integer(1), YAML.config().version("1.0")
                .explicitTypes(true)));
    }

    public void testMoreScalars() {
        assertEquals("--- !!str 1.0\n", YAML.dump("1.0"));
    }

    public void testDumpJavaBean() {
        final java.util.Calendar cal = java.util.Calendar.getInstance(TimeZone.getTimeZone("CET"));
        cal.clear();
        cal.set(1982, 5 - 1, 3); // Java's months are zero-based...

        final TestBean toDump = new TestBean("Ola Bini", 24, cal.getTime());
        assertEquals(
                "--- !java/object:org.jvyaml.TestBean\nname: Ola Bini\nage: 24\nborn: 1982-05-02T22:00:00Z\n",
                YAML.dump(toDump));

    }
}
