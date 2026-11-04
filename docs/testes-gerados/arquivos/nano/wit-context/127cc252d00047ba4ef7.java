/*
 * @test
 * @summary Test for ImmutableCollections.add(int, E) method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testAddThrowsUOE();
    }

    public static void testAddThrowsUOE() {
        try {
            // Invoca o método que deve lançar UnsupportedOperationException
            ImmutableCollections.add(0, "test");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
        }
    }
}
