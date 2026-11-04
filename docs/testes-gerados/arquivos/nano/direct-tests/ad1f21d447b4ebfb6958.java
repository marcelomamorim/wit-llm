/* @test    @summary Test ArrayList constructor with positive initial capacity    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testConstructorWithPositiveCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    static void testConstructorWithPositiveCapacity() {
        int capacity = 10;
        ArrayList<Object> list = new ArrayList<>(capacity);
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + list.size());
        }
        // Since ArrayList does not expose capacity, test that no exception is thrown and list is empty
    }

    static void testConstructorWithZeroCapacity() {
        ArrayList<Object> list = new ArrayList<>(0);
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + list.size());
        }
    }

    static void testConstructorWithNegativeCapacity() {
        boolean exceptionThrown = false;
        try {
            new ArrayList<>(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        }
    }
}
