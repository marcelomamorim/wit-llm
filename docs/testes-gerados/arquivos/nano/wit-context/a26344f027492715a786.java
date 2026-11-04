/*
 * @test
 * @summary Test for Collections.replace method to observe exception behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceThrowsUnsupportedOperationException();
    }

    private static void testReplaceThrowsUnsupportedOperationException() {
        try {
            // Invoca o método replace, que deve lançar UnsupportedOperationException
            boolean result = Collections.replace("key", "oldValue", "newValue");
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            // Qualquer outra exceção, o teste falha
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
