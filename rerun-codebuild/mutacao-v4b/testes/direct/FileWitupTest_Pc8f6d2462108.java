/* @test
   @summary Verifies the behavior of setLastModified method in java.io.File.
   @run main FileWitupTest_Pc8f6d2462108
 */
import java.io.File;
public class FileWitupTest_Pc8f6d2462108 {
    public static void main(String[] args) {
        String tempFilePath = System.getProperty("java.io.tmpdir") + "/testFile.txt";
        File testFile = new File(tempFilePath);
        try {
            if (!testFile.createNewFile()) {
                throw new AssertionError("Failed to create test file");
            }
            long validTime = System.currentTimeMillis();
            boolean result = testFile.setLastModified(validTime);
            if (!result) {
                throw new AssertionError("setLastModified returned false for valid timestamp");
            }
            long invalidTime = -1000L;
            boolean exceptionThrown = false;
            try {
                testFile.setLastModified(invalidTime);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalArgumentException for negative timestamp");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        } finally {
            if (testFile.exists()) {
                testFile.delete();
            }
        }
    }
}
