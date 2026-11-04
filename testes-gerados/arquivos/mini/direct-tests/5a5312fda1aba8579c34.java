/*
 * @test
 * @summary Test LinkedBlockingDeque.toArray() method correctness and consistency
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Arrays;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyDequeToArray();
        testSingleElementDequeToArray();
        testMultipleElementsDequeToArray();
    }

    static void testEmptyDequeToArray() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Object[] arr = deque.toArray();
        if (arr == null) {
            throw new RuntimeException("toArray() returned null for empty deque");
        }
        if (arr.length != 0) {
            throw new RuntimeException("toArray() length for empty deque expected 0 but was " + arr.length);
        }
    }

    static void testSingleElementDequeToArray() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "element";
        deque.add(element);
        Object[] arr = deque.toArray();
        if (arr == null) {
            throw new RuntimeException("toArray() returned null for single element deque");
        }
        if (arr.length != 1) {
            throw new RuntimeException("toArray() length for single element deque expected 1 but was " + arr.length);
        }
        if (!element.equals(arr[0])) {
            throw new RuntimeException("toArray() element mismatch: expected '" + element + "' but was '" + arr[0] + "'");
        }
    }

    static void testMultipleElementsDequeToArray() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String[] elements = {"one", "two", "three", "four"};
        for (String e : elements) {
            deque.add(e);
        }
        Object[] arr = deque.toArray();
        if (arr == null) {
            throw new RuntimeException("toArray() returned null for multiple elements deque");
        }
        if (arr.length != elements.length) {
            throw new RuntimeException("toArray() length expected " + elements.length + " but was " + arr.length);
        }
        for (int i = 0; i < elements.length; i++) {
            if (!elements[i].equals(arr[i])) {
                throw new RuntimeException("toArray() element mismatch at index " + i + ": expected '" + elements[i] + "' but was '" + arr[i] + "'");
            }
        }
    }
}
