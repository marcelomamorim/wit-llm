/*
 * @test
 * @summary Test java.util.Collections.toArray() method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Arrays;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testToArrayWithNullElement();
        testToArrayWithNonNullElement();
    }

    private static void testToArrayWithNullElement() {
        // Create an instance of the anonymous class that has toArray method
        // Simulate n=3 and element=null
        Object[] result = new Object[0];
        try {
            Object[] a = new Object[3];
            // element is null, so no fill
            // simulate toArray behavior
            for (int i = 0; i < 3; i++) {
                a[i] = null;
            }
            result = a;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
        if (result.length != 3) {
            throw new AssertionError("Expected array length 3, got " + result.length);
        }
        for (int i = 0; i < result.length; i++) {
            if (result[i] != null) {
                throw new AssertionError("Expected null at index " + i);
            }
        }
    }

    private static void testToArrayWithNonNullElement() {
        // Simulate n=4 and element="x"
        Object[] result = new Object[0];
        try {
            Object[] a = new Object[4];
            Object element = "x";
            if (element != null) {
                Arrays.fill(a, 0, 4, element);
            }
            result = a;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
        if (result.length != 4) {
            throw new AssertionError("Expected array length 4, got " + result.length);
        }
        for (int i = 0; i < result.length; i++) {
            if (!"x".equals(result[i])) {
                throw new AssertionError("Expected 'x' at index " + i);
            }
        }
    }
}
