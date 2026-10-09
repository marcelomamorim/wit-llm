/* @test
   @summary Test createNewFile() method of java.io.File.
   @run main FileWitupTest_Pfaa96d5485f7
 */
import java.io.File;
import java.io.IOException;

public class FileWitupTest_Pfaa96d5485f7 {
    public static void main(String[] args) throws Exception {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("testfile", ".tmp");
            boolean created = false;
            try {
                created = tempFile.createNewFile();
            } catch (IOException e) {
                throw new AssertionError("createNewFile() threw IOException", e);
            }
            if (!created) {
                throw new AssertionError("Expected createNewFile() to return true for new file");
            }
            if (!tempFile.exists()) {
                throw new AssertionError("File does not exist after createNewFile() returns true");
            }
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
