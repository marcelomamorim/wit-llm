/* @test    @summary Test putFirst method of LinkedBlockingDeque for basic functionality and null handling    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testPutFirstWithElement();
        testPutFirstWithNull();
    }

    static void testPutFirstWithElement() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putFirst("element");
        String result = deque.takeFirst();
        assert "element".equals(result) : "Expected 'element', got " + result;
    }

    static void testPutFirstWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.putFirst(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
