/* @test    @summary Test for IdentityHashMap constructor with expectedMaxSize parameter    @run main IdentityHashMapWitupTest */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithPositiveSize();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
    }

    private static void testConstructorWithPositiveSize() {
        try {
            int size = 10;
            IdentityHashMap<Object, Object> map = new IdentityHashMap<>(size);
            if (map == null) {
                throw new AssertionError("Constructor with positive size returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Constructor with positive size threw an exception: " + e);
        }
    }

    private static void testConstructorWithZeroSize() {
        try {
            IdentityHashMap<Object, Object> map = new IdentityHashMap<>(0);
            if (map == null) {
                throw new AssertionError("Constructor with zero size returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Constructor with zero size threw an exception: " + e);
        }
    }

    private static void testConstructorWithNegativeSize() {
        try {
            int negativeSize = -1;
            IdentityHashMap<Object, Object> map = new IdentityHashMap<>(negativeSize);
            throw new AssertionError("Constructor with negative size did not throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Constructor with negative size threw unexpected exception: " + e);
        }
    }
}
