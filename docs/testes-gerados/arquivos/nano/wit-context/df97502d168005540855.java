/*
 * @test
 * @summary Test for Collections.remove method to verify exception throwing behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsUnsupportedOperationException();
    }

    public static void testRemoveThrowsUnsupportedOperationException() {
        try {
            boolean result = Collections.remove(new Object(), new Object());
            // Se não lançar exceção, o teste deve falhar
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            // Qualquer outra exceção indica falha
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
