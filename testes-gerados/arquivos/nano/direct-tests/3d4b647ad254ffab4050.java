/* @test    @summary Test Hashtable constructor with initial capacity    @run main HashtableWitupTest */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testConstructorWithPositiveCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithLargeCapacity();
    }

    static void testConstructorWithPositiveCapacity() {
        int capacity = 10;
        Hashtable<Object, Object> ht = new Hashtable<>(capacity);
        if (ht.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + ht.size());
        }
    }

    static void testConstructorWithZeroCapacity() {
        int capacity = 0;
        Hashtable<Object, Object> ht = new Hashtable<>(capacity);
        if (ht.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + ht.size());
        }
    }

    static void testConstructorWithLargeCapacity() {
        int capacity = 1000;
        Hashtable<Object, Object> ht = new Hashtable<>(capacity);
        if (ht.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + ht.size());
        }
    }
}
