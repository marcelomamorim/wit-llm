/* @test    @summary Test for ReplaceableUCharacterIterator constructor with null argument to verify it throws IllegalArgumentException    @run main ReplaceableUCharacterIteratorWitupTest */

import sun.text.normalizer.ReplaceableUCharacterIterator;

public class ReplaceableUCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
    }

    public static void testConstructorWithNull() {
        try {
            new ReplaceableUCharacterIterator(null);
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
