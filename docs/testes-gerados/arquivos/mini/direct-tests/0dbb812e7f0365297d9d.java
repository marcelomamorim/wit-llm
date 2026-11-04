/*
 * @test
 * @summary Test java.util.Arrays.asList(T... a) method
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testAsListWithNullArray();
        testAsListWithEmptyArray();
        testAsListWithSingleElement();
        testAsListWithMultipleElements();
        testAsListWithNullElement();
    }

    private static void testAsListWithNullArray() {
        try {
            Arrays.asList((Object[]) null);
            throw new RuntimeException("Expected NullPointerException for null array argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAsListWithEmptyArray() {
        List<Object> list = Arrays.asList();
        if (list == null) {
            throw new RuntimeException("Returned list is null for empty array");
        }
        if (list.size() != 0) {
            throw new RuntimeException("Expected empty list size for empty array");
        }
    }

    private static void testAsListWithSingleElement() {
        String element = "test";
        List<String> list = Arrays.asList(element);
        if (list == null) {
            throw new RuntimeException("Returned list is null for single element array");
        }
        if (list.size() != 1) {
            throw new RuntimeException("Expected list size 1 for single element array");
        }
        if (!element.equals(list.get(0))) {
            throw new RuntimeException("List element does not match input element");
        }
    }

    private static void testAsListWithMultipleElements() {
        Integer[] input = {1, 2, 3};
        List<Integer> list = Arrays.asList(input);
        if (list == null) {
            throw new RuntimeException("Returned list is null for multiple element array");
        }
        if (list.size() != input.length) {
            throw new RuntimeException("List size does not match input array length");
        }
        for (int i = 0; i < input.length; i++) {
            if (!input[i].equals(list.get(i))) {
                throw new RuntimeException("List element at index " + i + " does not match input element");
            }
        }
    }

    private static void testAsListWithNullElement() {
        String[] input = {null};
        List<String> list = Arrays.asList(input);
        if (list == null) {
            throw new RuntimeException("Returned list is null for array with null element");
        }
        if (list.size() != 1) {
            throw new RuntimeException("Expected list size 1 for array with null element");
        }
        if (list.get(0) != null) {
            throw new RuntimeException("Expected null element in list");
        }
    }
}
