/*
 * @test    @summary Test the remove() method of ConcurrentLinkedDeque for correct exception and behavior.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutLastRet();
        testRemoveWithLastRet();
    }

    private static void testRemoveWithoutLastRet() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.remove();
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testRemoveWithLastRet() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        // Access internal node reference lastRet via reflection or simulate behavior
        // Since internal node is not accessible, we simulate the effect by setting lastRet manually
        // but as per constraints, we cannot access internal state directly.
        // Instead, we test the behavior when lastRet is set to a non-null value.
        // For this, we create a subclass to expose lastRet for testing purposes.
        class TestDeque extends ConcurrentLinkedDeque<Integer> {
            public Node<Integer> getLastRet() {
                return lastRet;
            }
            public void setLastRet(Node<Integer> node) {
                lastRet = node;
            }
        }
        TestDeque testDeque = new TestDeque();
        testDeque.add(1);
        testDeque.add(2);
        // Manually set lastRet to a node with item
        Node<Integer> node = testDeque.new Node<>(1);
        testDeque.setLastRet(node);
        try {
            testDeque.remove();
            if (testDeque.getLastRet() == null) {
                System.out.println("PASS: remove() executed and lastRet cleared");
            } else {
                System.out.println("FAIL: lastRet not cleared after remove()");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during remove(): " + e);
        }
    }

    // Internal node class for testing purposes
    static class Node<E> {
        E item;
        Node<E> next;
        Node(E item) {
            this.item = item;
        }
    }
}
