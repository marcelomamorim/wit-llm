/* @test    @summary Test Hashtable constructor with valid initialCapacity and loadFactor    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithZeroInitialCapacity();
        testConstructorWithNegativeInitialCapacity();
        testConstructorWithNaNLoadFactor();
        testConstructorWithZeroLoadFactor();
        testConstructorWithNegativeLoadFactor();
    }

    static void testConstructorWithValidParameters() {
        Hashtable<Object, Object> ht = new Hashtable<>(10, 0.75f);
        assert ht != null : "Hashtable should be created with valid parameters";
        System.out.println("testConstructorWithValidParameters passed");
    }

    static void testConstructorWithZeroInitialCapacity() {
        Hashtable<Object, Object> ht = new Hashtable<>(0, 0.75f);
        assert ht != null : "Hashtable should be created with initialCapacity=0";
        System.out.println("testConstructorWithZeroInitialCapacity passed");
    }

    static void testConstructorWithNegativeInitialCapacity() {
        try {
            new Hashtable<>(-1, 0.75f);
            assert false : "Expected IllegalArgumentException for negative initialCapacity";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for negative initialCapacity");
        }
    }

    static void testConstructorWithNaNLoadFactor() {
        try {
            new Hashtable<>(10, Float.NaN);
            assert false : "Expected IllegalArgumentException for NaN loadFactor";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for NaN loadFactor");
        }
    }

    static void testConstructorWithZeroLoadFactor() {
        try {
            new Hashtable<>(10, 0.0f);
            assert false : "Expected IllegalArgumentException for zero loadFactor";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for zero loadFactor");
        }
    }

    static void testConstructorWithNegativeLoadFactor() {
        try {
            new Hashtable<>(10, -0.5f);
            assert false : "Expected IllegalArgumentException for negative loadFactor";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for negative loadFactor");
        }
    }
}
