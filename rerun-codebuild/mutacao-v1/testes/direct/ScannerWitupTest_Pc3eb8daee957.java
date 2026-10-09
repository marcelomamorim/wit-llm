/*
 * @test
 * @summary Test that Scanner.remove() throws UnsupportedOperationException
 * @run main ScannerWitupTest_Pc3eb8daee957
 */

import java.util.*;

public class ScannerWitupTest_Pc3eb8daee957 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("test string");
        try {
            scanner.remove();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
