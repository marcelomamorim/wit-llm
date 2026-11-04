/*
 * @test
 * @summary Verify that the entry set view of Properties rejects addAll with UnsupportedOperationException and leaves the table unchanged
 * @run main PropertiesWitupTest
 */

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testEntrySetAddAllThrowsUnsupportedOperationException();
        testEntrySetAddAllLeavesPropertiesUnchanged();
    }

    static void testEntrySetAddAllThrowsUnsupportedOperationException() {
        Properties p = new Properties();
        p.setProperty("k", "v");
        Set<Map.Entry<Object, Object>> entries = p.entrySet();
        List<Map.Entry<Object, Object>> c = new ArrayList<>();
        c.add(new AbstractMap.SimpleEntry<Object, Object>("a", "b"));
        boolean thrown = false;
        try {
            entries.addAll(c);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from entrySet().addAll");
        }
    }

    static void testEntrySetAddAllLeavesPropertiesUnchanged() {
        Properties p = new Properties();
        p.setProperty("k", "v");
        Set<Map.Entry<Object, Object>> entries = p.entrySet();
        List<Map.Entry<Object, Object>> c = new ArrayList<>();
        c.add(new AbstractMap.SimpleEntry<Object, Object>("a", "b"));
        try {
            entries.addAll(c);
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (p.size() != 1) {
            throw new RuntimeException("size changed: " + p.size());
        }
        if (!"v".equals(p.getProperty("k"))) {
            throw new RuntimeException("existing property changed");
        }
        if (p.getProperty("a") != null) {
            throw new RuntimeException("unexpected property added");
        }
    }
}
