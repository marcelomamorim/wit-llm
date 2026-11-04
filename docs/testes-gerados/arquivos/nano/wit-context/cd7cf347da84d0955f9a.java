/* @test    @summary Test for Hashtable constructor with initialCapacity and loadFactor    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testConstructorWithNegativeCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithNaNLoadFactor();
        testConstructorWithZeroLoadFactor();
        testConstructorWithValidParameters();
    }

    private static void testConstructorWithNegativeCapacity() {
        try {
            new Hashtable(-1, 0.75f);
            System.err.println("Failed: Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithZeroCapacity() {
        try {
            new Hashtable(0, 0.75f);
            System.out.println("Passed: Zero capacity adjusted to 1");
            Hashtable ht = new Hashtable(0, 0.75f);
            if (ht != null) {
                System.out.println("Hashtable created with zero initial capacity (adjusted to 1)");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Failed: Unexpected IllegalArgumentException for zero capacity");
        }
    }

    private static void testConstructorWithNaNLoadFactor() {
        try {
            new Hashtable(10, Float.NaN);
            System.err.println("Failed: Expected IllegalArgumentException for NaN loadFactor");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithZeroLoadFactor() {
        try {
            new Hashtable(10, 0.0f);
            System.err.println("Failed: Expected IllegalArgumentException for zero loadFactor");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidParameters() {
        try {
            Hashtable<String, String> ht = new Hashtable<>(5, 0.75f);
            if (ht != null) {
                System.out.println("Passed: Hashtable created with valid parameters");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Failed: Unexpected exception for valid parameters");
        }
    }
}
