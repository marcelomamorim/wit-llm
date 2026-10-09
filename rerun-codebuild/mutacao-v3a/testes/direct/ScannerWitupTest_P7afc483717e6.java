/*
 * @test
 * @summary Test that Scanner.remove() throws UnsupportedOperationException
 * @run main ScannerWitupTest_P7afc483717e6
 */

import java.util.*;

public class ScannerWitupTest_P7afc483717e6 {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        Scanner scanner = new Scanner("sample text");
        try {
            scanner.remove();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception thrown: " + e);
        }
    }
}
