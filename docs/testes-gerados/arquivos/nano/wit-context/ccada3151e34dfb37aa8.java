/*
 * @test
 * @summary Test for java.util.Collections.next() method.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNextThrowsException();
    }

    public static void testNextThrowsException() {
        try {
            Collections.next();
            System.err.println("Expected NoSuchElementException was not thrown.");
            System.exit(1);
        } catch (NoSuchElementException e) {
            // Test passes if exception is caught
            System.out.println("NoSuchElementException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
