/* @test    @summary Test for ImmutableCollections.previous() method to verify exception behavior.    @run main ImmutableCollectionsWitupTest */

import java.util.NoSuchElementException;
import java.util.UnsupportedOperationException;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPreviousThrowsUOE();
    }

    private static void testPreviousThrowsUOE() {
        try {
            ImmutableCollections instance = new ImmutableCollections();
            // Invoca o método previous, esperando uma UnsupportedOperationException
            instance.previous();
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido
            System.out.println("Passed: previous() throws UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
