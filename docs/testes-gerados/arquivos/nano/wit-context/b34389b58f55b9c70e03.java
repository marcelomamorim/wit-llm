/* @test    @summary Test HashSet constructor with initialCapacity and loadFactor for exception paths    @run main HashSetWitupTest */

import java.util.HashSet;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testNegativeInitialCapacity();
        testZeroInitialCapacity();
        testExcessiveLoadFactor();
    }

    private static void testNegativeInitialCapacity() {
        try {
            new HashSet<Integer>(-1, 0.75f);
            System.out.println("FAIL: Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative initialCapacity");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testZeroInitialCapacity() {
        try {
            new HashSet<Integer>(0, 0.75f);
            System.out.println("PASS: Zero initialCapacity accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: Unexpected IllegalArgumentException for zero initialCapacity");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testExcessiveLoadFactor() {
        try {
            new HashSet<Integer>(10, -0.5f);
            System.out.println("FAIL: Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative loadFactor");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
        try {
            new HashSet<Integer>(10, 0f);
            System.out.println("PASS: Zero loadFactor accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: Unexpected IllegalArgumentException for zero loadFactor");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
