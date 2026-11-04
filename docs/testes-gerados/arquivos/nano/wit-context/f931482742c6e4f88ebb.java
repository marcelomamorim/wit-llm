/*
 * @test
 * @summary Test for ImmutableCollections.putIfAbsent method to verify exception throwing behavior.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentThrowsUOE();
    }

    public static void testPutIfAbsentThrowsUOE() {
        try {
            // Invoca o método putIfAbsent, que deve lançar UnsupportedOperationException
            ImmutableCollections.putIfAbsent("key", "value");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
        }
    }
}
