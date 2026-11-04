/*
 * @test
 * @summary Checks public observable behavior of Map.Entry.setValue returned by EnumMap entrySet iteration
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {
    enum Color { RED, GREEN, BLUE }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetValueReturnsOldAndUpdatesMap() {
        EnumMap<Color, String> m = new EnumMap<>(Color.class);
        m.put(Color.RED, "r");
        m.put(Color.GREEN, "g");
        Map.Entry<Color, String> e = m.entrySet().iterator().next();
        check(e.getKey() == Color.RED, "first entry key should be RED");
        String old = e.setValue("x");
        check("r".equals(old), "setValue should return previous value");
        check("x".equals(m.get(Color.RED)), "map should reflect new value");
        check("g".equals(m.get(Color.GREEN)), "other entries should be unchanged");
    }

    static void testSetValueNullAllowed() {
        EnumMap<Color, String> m = new EnumMap<>(Color.class);
        m.put(Color.RED, "r");
        Map.Entry<Color, String> e = m.entrySet().iterator().next();
        String old = e.setValue(null);
        check("r".equals(old), "setValue(null) should return previous value");
        check(m.get(Color.RED) == null, "null value should be stored");
        check(m.containsKey(Color.RED), "key should remain present after null value");
    }

    static void testSetValueOnSecondEntry() {
        EnumMap<Color, String> m = new EnumMap<>(Color.class);
        m.put(Color.RED, "r");
        m.put(Color.BLUE, "b");
        java.util.Iterator<Map.Entry<Color, String>> it = m.entrySet().iterator();
        it.next();
        Map.Entry<Color, String> second = it.next();
        check(second.getKey() == Color.BLUE, "second entry key should be BLUE");
        String old = second.setValue("z");
        check("b".equals(old), "second setValue should return previous value");
        check("z".equals(m.get(Color.BLUE)), "map should reflect second entry update");
        check("r".equals(m.get(Color.RED)), "first entry should be unchanged");
    }

    public static void main(String[] args) {
        testSetValueReturnsOldAndUpdatesMap();
        testSetValueNullAllowed();
        testSetValueOnSecondEntry();
    }
}
