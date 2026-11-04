/*
 * @test
 * @summary Test addFirst method of LinkedBlockingDeque for null and full deque scenarios
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddFirstWithNull();
        testAddFirstWithFullDeque();
    }

    private static void testAddFirstWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>(2);
        try {
            deque.addFirst(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAddFirstWithFullDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        try {
            deque.offerFirst(1);
            deque.offerFirst(2); // deque is now full
            deque.addFirst(3); // should throw IllegalStateException
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            System.out.println("PASS: IllegalStateException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
