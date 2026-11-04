/*
 * @test
 * @summary Teste do método offer() da classe java.util.concurrent.LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithNull();
        testOfferWithValidElement();
    }

    static void testOfferWithNull() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        try {
            queue.offer(null);
            // Se não lançar exceção, o teste falha
            System.err.println("Failed: offer(null) did not throw NullPointerException");
            System.exit(1);
        } catch (NullPointerException expected) {
            // Esperado
        } catch (Throwable t) {
            System.err.println("Failed: offer(null) threw unexpected exception: " + t);
            System.exit(1);
        }
    }

    static void testOfferWithValidElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        String element = "test";
        boolean result = queue.offer(element);
        if (!result) {
            System.err.println("Failed: offer() returned false for valid element");
            System.exit(1);
        }
        if (queue.isEmpty()) {
            System.err.println("Failed: queue is empty after offer()");
            System.exit(1);
        }
        String head = queue.peek();
        if (!element.equals(head)) {
            System.err.println("Failed: queue head does not match offered element");
            System.exit(1);
        }
    }
}
