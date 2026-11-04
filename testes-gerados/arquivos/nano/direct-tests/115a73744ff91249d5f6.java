/* @test    @summary Test constructor OpenFileInputStreamAction(String filename) with valid filename    @run main OpenFileInputStreamActionWitupTest */

import sun.security.action.OpenFileInputStreamAction;
import java.io.*;

public class OpenFileInputStreamActionWitupTest {

    public static void main(String[] args) throws Exception {
        String tempFilename = "temp_test_file.txt";
        File tempFile = new File(tempFilename);
        try {
            if (!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if (!created) {
                    throw new IOException("Failed to create temp file");
                }
            }
            OpenFileInputStreamAction action = new OpenFileInputStreamAction(tempFilename);
            try (FileInputStream fis = action.run()) {
                if (fis == null) {
                    throw new AssertionError("FileInputStream is null");
                }
            }
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
