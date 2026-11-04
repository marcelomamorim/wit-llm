/*
 * @test
 * @summary Teste do método offer com diferentes entradas e condições.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testOfferWithNullElement();
        testOfferWithValidElement();
    }

    private static void testOfferWithNullElement() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        try {
            boolean result = queue.offer(null, 1, TimeUnit.MILLISECONDS);
            // Como o método offer aceita null, não lança exceção, apenas retorna true.
            if (!result) {
                throw new AssertionError("offer com null retornou false");
            }
        } catch (NullPointerException e) {
            throw new AssertionError("offer com null lançou NullPointerException", e);
        }
    }

    private static void testOfferWithValidElement() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        String element = "test";
        boolean result = queue.offer(element, 1, TimeUnit.MILLISECONDS);
        if (!result) {
            throw new AssertionError("offer com elemento válido retornou false");
        }
        if (queue.isEmpty()) {
            throw new AssertionError("queue deve conter elemento após offer");
        }
        String polled = queue.poll();
        if (!element.equals(polled)) {
            throw new AssertionError("Elemento polled não corresponde ao oferecido");
        }
    }
}
