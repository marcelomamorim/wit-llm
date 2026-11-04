/* @test    @summary Test putLast method of LinkedBlockingDeque for normal and null input behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testPutLastNormal();
        testPutLastNull();
    }

    static void testPutLastNormal() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.putLast("element");
        String result = deque.takeFirst();
        if (!"element".equals(result)) {
            throw new AssertionError("Expected 'element', but got: " + result);
        }
    }

    static void testPutLastNull() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.putLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException was not thrown for null input");
        }
    }
}
