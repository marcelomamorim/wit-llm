/* @test    @summary Test for ImmutableCollections.remove method to verify exception throwing behavior    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsUOE();
    }

    private static void testRemoveThrowsUOE() {
        try {
            // Chamada que deve lançar UnsupportedOperationException
            boolean result = ImmutableCollections.remove(new Object(), new Object());
            // Se não lançar, falha o teste
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Teste passou, exceção esperada
            System.out.println("remove() correctly threw UnsupportedOperationException.");
        } catch (Throwable t) {
            // Se outra exceção for lançada, falha o teste
            throw new AssertionError("Unexpected exception thrown: " + t, t);
        }
    }
}
