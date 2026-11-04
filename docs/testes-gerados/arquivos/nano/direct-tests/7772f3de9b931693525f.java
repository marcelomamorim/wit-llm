/*
 * @test    @summary Test ArrayBlockingQueue.toArray() method for correct behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Arrays;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testToArrayEmpty();
        testToArraySingleElement();
        testToArrayMultipleElements();
        testToArrayWithWrapAround();
    }

    static void testToArrayEmpty() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        Object[] result = queue.toArray();
        assert result.length == 0 : "Expected empty array";
    }

    static void testToArraySingleElement() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        queue.add("element");
        Object[] result = queue.toArray();
        assert result.length == 1 : "Expected array of length 1";
        assert result[0].equals("element") : "Expected element to be 'element'";
    }

    static void testToArrayMultipleElements() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Object[] result = queue.toArray();
        assert result.length == 3 : "Expected array of length 3";
        assert Arrays.equals(result, new Object[] {"a", "b", "c"}) : "Array contents mismatch";
    }

    static void testToArrayWithWrapAround() {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(5);
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.remove(); // remove "1"
        queue.add("4");
        queue.add("5");
        queue.remove(); // remove "2"
        queue.add("6");
        Object[] result = queue.toArray();
        assert result.length == 4 : "Expected array of length 4";
        assert Arrays.equals(result, new Object[] {"3", "4", "5", "6"}) : "Array contents mismatch";
    }
}
