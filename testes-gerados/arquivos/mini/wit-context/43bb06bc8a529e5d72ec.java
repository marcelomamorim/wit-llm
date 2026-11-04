/*
 * @test
 * @summary Test Scanner.remove() throws UnsupportedOperationException
 * @run main ScannerWitupTest
 */

public class ScannerWitupTest {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner("");
        try {
            scanner.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        } finally {
            scanner.close();
        }
    }
}
