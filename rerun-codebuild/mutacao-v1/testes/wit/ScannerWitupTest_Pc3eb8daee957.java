/* @test    @summary Test for Scanner.remove() method to verify it throws UnsupportedOperationException.    @run main ScannerWitupTest_Pc3eb8daee957 */

public class ScannerWitupTest_Pc3eb8daee957 {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        java.util.Scanner scanner = new java.util.Scanner("test input");
        try {
            scanner.remove();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes, exception was thrown as expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        } finally {
            scanner.close();
        }
    }
}
