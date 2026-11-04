/*
 * @test
 * @summary Test for Collections.previous() method to verify exception throwing behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testPreviousThrowsException();
    }

    public static void testPreviousThrowsException() {
        try {
            Collections.previous();
            System.err.println("Test failed: NoSuchElementException was not thrown.");
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.out.println("Test passed: NoSuchElementException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
