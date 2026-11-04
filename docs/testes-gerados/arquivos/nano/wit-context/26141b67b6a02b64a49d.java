/* @test    @summary Test for java.util.Collections.nextElement() method to observe exception behavior    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNextElementThrowsException();
    }

    public static void testNextElementThrowsException() {
        try {
            Collections.nextElement();
            System.err.println("Expected NoSuchElementException was not thrown.");
            System.exit(1);
        } catch (NoSuchElementException e) {
            // Expected exception caught, test passes.
            System.out.println("NoSuchElementException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
