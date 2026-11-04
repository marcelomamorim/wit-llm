/*
 * @test
 * @summary Test for java.util.Collections.add(E e) method.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        try {
            Collections.add("test element");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido, exceção esperada
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
