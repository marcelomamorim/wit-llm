/*
 * @test
 * @summary Test the add method of sun.nio.ch.Util for unsupported operation exception
 * @run main UtilWitupTest
 */

import sun.nio.ch.Util;

public class UtilWitupTest {

    public static void main(String[] args) {
        testAddMethodThrowsException();
    }

    public static void testAddMethodThrowsException() {
        try {
            Util.add("testElement");
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("UnsupportedOperationException correctly thrown.");
        } catch (Throwable t) {
            // Any other exception means the test fails
            throw new AssertionError("Unexpected exception thrown: " + t, t);
        }
    }
}
