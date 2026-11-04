/* @test    @summary Test for LinkedBlockingDeque.toArray() method    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Arrays;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testToArrayEmpty();
        testToArrayWithElements();
    }

    static void testToArrayEmpty() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Object[] array = deque.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 0 : "Array length should be 0";
    }

    static void testToArrayWithElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Object[] array = deque.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 3 : "Array length should be 3";
        assert Arrays.equals(array, new Object[]{1, 2, 3}) : "Array contents mismatch";
    }
}
