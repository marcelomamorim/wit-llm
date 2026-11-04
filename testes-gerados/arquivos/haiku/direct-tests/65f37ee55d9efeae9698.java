/*
 * @test
 * @summary Verify that Properties.add(Map.Entry) always throws UnsupportedOperationException and leaves the table unchanged
 * @run main PropertiesWitupTest
 */

import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testAddEntryThrows();
        testAddNullEntryThrows();
        testAddWithDefaultsThrowsAndLeavesSizeUnchanged();
    }

    static void testAddEntryThrows() {
        Properties p = new Properties();
        Map.Entry<Object, Object> e = new AbstractMap.SimpleEntry<Object, Object>("k", "v");
        boolean thrown = false;
        try {
            p.add(e);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException");
        }
        if (p.size() != 0) {
            throw new AssertionError("size changed after failed add: " + p.size());
        }
    }

    static void testAddNullEntryThrows() {
        Properties p = new Properties();
        boolean thrown = false;
        try {
            p.add(null);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException for null entry");
        }
    }

    static void testAddWithDefaultsThrowsAndLeavesSizeUnchanged() {
        Properties defaults = new Properties();
        defaults.setProperty("d", "1");
        Properties p = new Properties(defaults);
        Map.Entry<Object, Object> e = new AbstractMap.SimpleEntry<Object, Object>("x", "y");
        boolean thrown = false;
        try {
            p.add(e);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException with defaults");
        }
        if (p.size() != 0) {
            throw new AssertionError("size changed after failed add: " + p.size());
        }
        if (!"1".equals(p.getProperty("d"))) {
            throw new AssertionError("default lookup broken after failed add");
        }
    }
}
