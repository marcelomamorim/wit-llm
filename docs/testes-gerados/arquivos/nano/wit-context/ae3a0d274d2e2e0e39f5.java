/* @test    @summary Test HashSet constructor with initialCapacity for exception paths    @run main HashSetWitupTest */

import java.util.HashSet;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testHashSetWithNegativeCapacity();
        testHashSetWithZeroCapacity();
        testHashSetWithLargeCapacity();
    }

    private static void testHashSetWithNegativeCapacity() {
        try {
            new HashSet(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative capacity");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testHashSetWithZeroCapacity() {
        try {
            new HashSet(0);
            System.out.println("PASS: HashSet with zero capacity created");
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: Unexpected IllegalArgumentException for zero capacity");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testHashSetWithLargeCapacity() {
        try {
            HashSet<Integer> set = new HashSet<>(1000000);
            if (set != null) {
                System.out.println("PASS: HashSet with large capacity created");
            } else {
                System.out.println("FAIL: HashSet with large capacity is null");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: Unexpected IllegalArgumentException for large capacity");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
