/* @test
   @summary Verifies that setSize throws IllegalArgumentException for negative sizes.
   @run main ZipEntryWitupTest_P31b043221ba1
 */
import java.util.zip.ZipEntry;
public class ZipEntryWitupTest_P31b043221ba1 {
    public static void main(String[] args) {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setSize(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // expected
        }
        // Test with zero size (valid case)
        try {
            entry.setSize(0);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero size", e);
        }
        // Test with positive size (valid case)
        try {
            entry.setSize(12345);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for positive size", e);
        }
    }
}
