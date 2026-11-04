/* @test    @summary Test for Collections.setValue to verify exception throwing behavior    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        try {
            // Invoca o método setValue com um valor qualquer
            Collections.setValue("testValue");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
        } catch (Throwable t) {
            // Se lançar outra exceção, o teste falha
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}