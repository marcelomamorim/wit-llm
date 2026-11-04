/* @test    @summary Test IdentityHashMap constructor with expectedMaxSize parameter    @run main IdentityHashMapWitupTest */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithPositiveSize();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
    }

    private static void testConstructorWithPositiveSize() {
        int size = 10;
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>(size);
        assert map != null : "Map should be instantiated with positive size";
        // Verify that map is empty after creation
        assert map.isEmpty() : "Map should be empty after creation";
    }

    private static void testConstructorWithZeroSize() {
        int size = 0;
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>(size);
        assert map != null : "Map should be instantiated with zero size";
        assert map.isEmpty() : "Map should be empty after creation";
    }

    private static void testConstructorWithNegativeSize() {
        boolean exceptionThrown = false;
        try {
            new IdentityHashMap<>(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for negative size";
    }
}
