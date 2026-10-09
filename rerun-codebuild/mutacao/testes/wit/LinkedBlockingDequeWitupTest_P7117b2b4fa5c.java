/* @test    @summary Test for LinkedBlockingDeque.putLast(E e) method behavior and exception paths    @run main LinkedBlockingDequeWitupTest_P7117b2b4fa5c */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest_P7117b2b4fa5c {

    public static void main(String[] args) throws Exception {
        testPutLastWithNull();
        testPutLastWithValidElement();
    }

    private static void testPutLastWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.putLast(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testPutLastWithValidElement() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        deque.putLast(element);
        String retrieved = deque.takeFirst();
        if (element.equals(retrieved)) {
            System.out.println("PASS: Element successfully added and retrieved");
        } else {
            System.out.println("FAIL: Retrieved element does not match");
        }
    }
}
