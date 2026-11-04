/* @test    @summary Test for ImmutableCollections.addAll method to verify it throws UnsupportedOperationException as expected.    @run main ImmutableCollectionsWitupTest */

import java.util.Collection;
import java.util.Arrays;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsUOE();
    }

    public static void testAddAllThrowsUOE() {
        Collection<Integer> dummyCollection = Arrays.asList(1, 2, 3);
        try {
            // Invoca o método que deve lançar UnsupportedOperationException
            ImmutableCollections.addAll(dummyCollection);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
        } catch (Throwable t) {
            // Se outra exceção for lançada, o teste falha
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
