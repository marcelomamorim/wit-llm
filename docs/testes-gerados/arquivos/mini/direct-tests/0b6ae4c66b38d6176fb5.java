/*
 * @test
 * @summary Test IdentityHashMap constructor with expectedMaxSize parameter
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testValidExpectedMaxSize();
        testZeroExpectedMaxSize();
        testNegativeExpectedMaxSize();
    }

    private static void testValidExpectedMaxSize() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>(10);
        if (map == null) throw new AssertionError("Map should not be null");
    }

    private static void testZeroExpectedMaxSize() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>(0);
        if (map == null) throw new AssertionError("Map should not be null");
    }

    private static void testNegativeExpectedMaxSize() {
        try {
            new IdentityHashMap<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative expectedMaxSize");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("expectedMaxSize is negative")) {
                throw new AssertionError("Exception message incorrect: " + msg);
            }
        }
    }
}
