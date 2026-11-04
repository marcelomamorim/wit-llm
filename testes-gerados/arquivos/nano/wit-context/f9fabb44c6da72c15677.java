/*
 * @test
 * @summary Testa o método contains de LinkedBlockingQueue para entradas nulas e não nulas.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithExistingElement();
        testContainsWithNonExistingElement();
    }

    static void testContainsWithNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            boolean result = queue.contains(null);
            if (result != false) {
                throw new AssertionError("Expected false for null element, got " + result);
            }
        } catch (UnsupportedOperationException e) {
            // Caso esperado, pois o método pode lançar essa exceção dependendo da implementação.
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null element: " + e);
        }
    }

    static void testContainsWithExistingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String element = "test";
        queue.add(element);
        boolean result = queue.contains(element);
        if (!result) {
            throw new AssertionError("Expected true for existing element.");
        }
    }

    static void testContainsWithNonExistingElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("foo");
        boolean result = queue.contains("bar");
        if (result) {
            throw new AssertionError("Expected false for non-existing element.");
        }
    }
}
