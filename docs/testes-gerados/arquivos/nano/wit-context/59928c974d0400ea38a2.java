/*
 * @test
 * @summary Tests for LinkedBlockingDeque methods to reveal potential bugs and regressions
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithNonNullAction();
    }

    public static void testForEachWithNullAction() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.forEach(null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null action");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    public static void testForEachWithNonNullAction() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i).append(",");
        try {
            deque.forEach(consumer);
            String result = sb.toString();
            if (result.equals("1,2,3,")) {
                System.out.println("PASS: forEach applied consumer correctly");
            } else {
                System.out.println("FAIL: Unexpected result: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
