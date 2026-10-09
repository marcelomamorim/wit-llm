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
        // Verify that setting a valid size works without exception
        long validSize = 12345L;
        entry.setSize(validSize);
        if (entry.size != validSize) {
            throw new AssertionError("Size not set correctly");
        }
    }
}
