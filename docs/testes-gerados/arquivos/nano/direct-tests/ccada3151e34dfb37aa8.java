/* @test    @summary Test for Collections.next() method to ensure it throws NoSuchElementException as specified.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNextThrowsException();
    }

    public static void testNextThrowsException() {
        try {
            Collections.next();
            System.err.println("Test failed: NoSuchElementException was not thrown.");
            System.exit(1);
        } catch (NoSuchElementException e) {
            // Expected exception caught, test passes.
            System.out.println("Test passed: NoSuchElementException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
