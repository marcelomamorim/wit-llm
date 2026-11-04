/*
 * @test
 * @summary Checks public observable behavior of EnumMap entry getValue via entrySet iteration, including null values
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public class EnumMapWitupTest {
    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        EnumMap<Color, String> map = new EnumMap<>(Color.class);
        map.put(Color.RED, "r");
        map.put(Color.BLUE, null);

        Iterator<Map.Entry<Color, String>> it = map.entrySet().iterator();

        Map.Entry<Color, String> first = it.next();
        check(first.getKey() == Color.RED, "unexpected first key");
        check("r".equals(first.getValue()), "getValue should return mapped value");

        Map.Entry<Color, String> second = it.next();
        check(second.getKey() == Color.BLUE, "unexpected second key");
        check(second.getValue() == null, "getValue should return null for null-mapped value");

        check(!it.hasNext(), "iteration should contain exactly two entries");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
