/*
 * @test
 * @summary Testa o método peek() de LinkedBlockingDeque para comportamento padrão.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPeekEmptyDeque();
        testPeekWithElements();
    }

    static void testPeekEmptyDeque() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        Object result = deque.peek();
        if (result != null) {
            throw new AssertionError("Expected null when peeking empty deque, but got: " + result);
        }
    }

    static void testPeekWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        Object result = deque.peek();
        if (!"element1".equals(result)) {
            throw new AssertionError("Expected 'element1' when peeking, but got: " + result);
        }
    }
}
