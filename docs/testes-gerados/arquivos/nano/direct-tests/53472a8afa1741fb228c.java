/* @test    @summary Test for Collections.previous() method to ensure it throws NoSuchElementException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testPreviousThrowsException();
    }

    public static void testPreviousThrowsException() {
        try {
            Collections.previous();
            throw new AssertionError("Expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            // Test passes if exception is caught
        }
    }
}
