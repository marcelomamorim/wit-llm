/*
 * @test
 * @summary Test for Scanner.remove() method to ensure it throws UnsupportedOperationException.
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        Scanner scanner = new Scanner("sample input");
        try {
            scanner.remove();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        } finally {
            scanner.close();
        }
    }
}
