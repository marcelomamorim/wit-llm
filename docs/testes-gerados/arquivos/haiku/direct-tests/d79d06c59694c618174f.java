/*
 * @test
 * @summary Verify EnumMap entry getValue returns the mapped value, including null and values updated via setValue
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {
    enum Color { RED, GREEN, BLUE }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static Map.Entry<Color, String> entryFor(Map<Color, String> m, Color key) {
        for (Map.Entry<Color, String> e : m.entrySet()) {
            if (e.getKey() == key) {
                return e;
            }
        }
        throw new AssertionError("missing entry for " + key);
    }

    static void testNonNullValue() {
        EnumMap<Color, String> m = new EnumMap<>(Color.class);
        m.put(Color.RED, "r");
        check("r".equals(entryFor(m, Color.RED).getValue()), "getValue should return mapped value");
    }

    static void testNullValue() {
        EnumMap<Color, String> m = new EnumMap<>(Color.class);
        m.put(Color.GREEN, null);
        check(entryFor(m, Color.GREEN).getValue() == null, "getValue should return null for null mapping");
    }

    static void testValueAfterSetValue() {
        EnumMap<Color, String> m = new EnumMap<>(Color.class);
        m.put(Color.BLUE, "old");
        Map.Entry<Color, String> e = entryFor(m, Color.BLUE);
        e.setValue("new");
        check("new".equals(e.getValue()), "getValue should reflect setValue");
        check("new".equals(m.get(Color.BLUE)), "map should reflect setValue");
    }

    public static void main(String[] args) {
        testNonNullValue();
        testNullValue();
        testValueAfterSetValue();
    }
}
