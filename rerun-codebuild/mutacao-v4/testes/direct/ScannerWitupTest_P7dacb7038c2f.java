/* @test
   @summary Verifies that calling remove() on Scanner throws UnsupportedOperationException.
   @run main ScannerWitupTest_P7dacb7038c2f
 */
import java.util.*;
public class ScannerWitupTest_P7dacb7038c2f {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("sample text");
        try {
            scanner.remove();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // expected exception
        }
    }
}
