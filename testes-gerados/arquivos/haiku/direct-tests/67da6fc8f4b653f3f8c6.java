/*
 * @test
 * @summary Verify EnumMap.setValue returns the previous value, stores the new value, handles nulls, and does not affect copies
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {

    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        testSetValueReturnsOldAndUpdatesMap();
        testSetValueOnNullOldValueReturnsNull();
        testSetValueStoresNull();
        testSetValueOnCopyDoesNotAffectOriginal();
    }

    static void testSetValueReturnsOldAndUpdatesMap() {
        Map<Color, String> m = new EnumMap<Color, String>(Color.class);
        m.put(Color.RED, "a");
        m.put(Color.GREEN, "g");
        Map.Entry<Color, String> e = findEntry(m, Color.RED);
        String old = e.setValue("b");
        check("a".equals(old), "setValue should return old value 'a' but was " + old);
        check("b".equals(m.get(Color.RED)), "map should now contain 'b' for RED");
        check("g".equals(m.get(Color.GREEN)), "other entries must be unchanged");
        check(m.size() == 2, "size should remain 2");
    }

    static void testSetValueOnNullOldValueReturnsNull() {
        Map<Color, String> m = new EnumMap<Color, String>(Color.class);
        m.put(Color.BLUE, null);
        Map.Entry<Color, String> e = findEntry(m, Color.BLUE);
        String old = e.setValue("x");
        check(old == null, "setValue should return null when old value was null");
        check("x".equals(m.get(Color.BLUE)), "map should now contain 'x' for BLUE");
    }

    static void testSetValueStoresNull() {
        Map<Color, String> m = new EnumMap<Color, String>(Color.class);
        m.put(Color.GREEN, "v");
        Map.Entry<Color, String> e = findEntry(m, Color.GREEN);
        String old = e.setValue(null);
        check("v".equals(old), "setValue should return 'v'");
        check(m.get(Color.GREEN) == null, "map value should be null after setValue(null)");
        check(m.containsKey(Color.GREEN), "key must still be present when value is null");
    }

    static void testSetValueOnCopyDoesNotAffectOriginal() {
        Map<Color, String> original = new EnumMap<Color, String>(Color.class);
        original.put(Color.RED, "orig");
        EnumMap<Color, String> copy = new EnumMap<Color, String>((EnumMap<Color, String>) original);
        Map.Entry<Color, String> e = findEntry(copy, Color.RED);
        String old = e.setValue("changed");
        check("orig".equals(old), "setValue on copy should return 'orig'");
        check("changed".equals(copy.get(Color.RED)), "copy should contain 'changed'");
        check("orig".equals(original.get(Color.RED)), "original must not be affected");
    }

    static Map.Entry<Color, String> findEntry(Map<Color, String> m, Color key) {
        for (Map.Entry<Color, String> e : m.entrySet()) {
            if (e.getKey() == key) {
                return e;
            }
        }
        throw new AssertionError("entry not found for " + key);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
