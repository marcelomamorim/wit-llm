/* @test    @summary Test for Hashtable constructor with initialCapacity parameter    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInitialCapacity();
        testConstructorWithZeroInitialCapacity();
        testConstructorWithNegativeInitialCapacity();
    }

    private static void testConstructorWithValidInitialCapacity() {
        try {
            Hashtable<Object, Object> ht = new Hashtable<>(10);
            if (ht.size() != 0) {
                System.out.println("FAIL: Expected size 0, got " + ht.size());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid initialCapacity: " + e);
        }
    }

    private static void testConstructorWithZeroInitialCapacity() {
        try {
            Hashtable<Object, Object> ht = new Hashtable<>(0);
            if (ht.size() != 0) {
                System.out.println("FAIL: Expected size 0, got " + ht.size());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for zero initialCapacity: " + e);
        }
    }

    private static void testConstructorWithNegativeInitialCapacity() {
        try {
            Hashtable<Object, Object> ht = new Hashtable<>(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative initialCapacity, but no exception was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            System.out.println("FAIL: Expected IllegalArgumentException, but got: " + e);
        }
    }
}
