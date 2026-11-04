/*
 * @test
 * @summary Verifies EnumMap iterator remove() throws IllegalStateException when no element was returned by next() and removes the mapping after next()
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Iterator;

public class EnumMapWitupTest {

    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsISE();
        testRemoveAfterNextRemovesMapping();
        testSecondRemoveThrowsISE();
    }

    static EnumMap<Color, String> newMap() {
        EnumMap<Color, String> map = new EnumMap<>(Color.class);
        map.put(Color.RED, "r");
        map.put(Color.GREEN, "g");
        map.put(Color.BLUE, "b");
        return map;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveBeforeNextThrowsISE() {
        EnumMap<Color, String> map = newMap();
        Iterator<Color> it = map.keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove before next() must throw IllegalStateException");
        check(map.size() == 3, "map must be unchanged after failed remove");
    }

    static void testRemoveAfterNextRemovesMapping() {
        EnumMap<Color, String> map = newMap();
        Iterator<Color> it = map.keySet().iterator();
        Color first = it.next();
        check(first == Color.RED, "first key must be RED in natural order");
        it.remove();
        check(map.size() == 2, "size must decrease by one after remove");
        check(!map.containsKey(Color.RED), "removed key must no longer be present");
        check(map.containsKey(Color.GREEN), "other keys must remain");
    }

    static void testSecondRemoveThrowsISE() {
        EnumMap<Color, String> map = newMap();
        Iterator<Color> it = map.keySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second consecutive remove must throw IllegalStateException");
        check(map.size() == 2, "map must not change on failed second remove");
    }
}
