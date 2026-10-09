/*
 * @test
 * @summary Testa o método offerFirst de LinkedBlockingDeque para comportamentos observáveis.
 * @run main LinkedBlockingDequeWitupTest_P199b56af12b4
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest_P199b56af12b4 {

    public static void main(String[] args) {
        testOfferFirstWithNull();
        testOfferFirstWithObject();
    }

    static void testOfferFirstWithNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.offerFirst(null);
            System.out.println("FAIL: Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testOfferFirstWithObject() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.offerFirst("test");
        if (result && deque.peekFirst().equals("test")) {
            System.out.println("PASS: offerFirst accepted object and deque contains it.");
        } else {
            System.out.println("FAIL: offerFirst did not behave as expected.");
        }
    }
}
