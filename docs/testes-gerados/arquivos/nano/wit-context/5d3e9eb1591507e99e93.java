/* @test    @summary Teste do método offer com timeout na PriorityBlockingQueue    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testOfferNullElement();
        testOfferValidElement();
    }

    private static void testOfferNullElement() {
        PriorityBlockingQueue<Object> queue = new PriorityBlockingQueue<>();
        try {
            queue.offer(null, 1, TimeUnit.MILLISECONDS);
            System.out.println("FAIL: NullPointerException esperado ao oferecer null.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException ao oferecer null.");
        } catch (Exception e) {
            System.out.println("FAIL: Exceção inesperada ao oferecer null: " + e);
        }
    }

    private static void testOfferValidElement() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        boolean result = false;
        try {
            result = queue.offer(42, 1, TimeUnit.MILLISECONDS);
            if (result && queue.contains(42)) {
                System.out.println("PASS: Elemento válido oferecido com sucesso.");
            } else {
                System.out.println("FAIL: Elemento válido não foi oferecido corretamente.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exceção inesperada ao oferecer elemento válido: " + e);
        }
    }
}
