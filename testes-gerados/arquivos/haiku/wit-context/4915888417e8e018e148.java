/*
 * @test
 * @summary File.createNewFile creates a new file, returns false for an existing file, and throws IOException for a path containing a NUL character
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {
    public static void main(String[] args) throws Exception {
        File f = new File("FileWitupTest_createNew.tmp");
        if (f.exists()) {
            f.delete();
        }
        try {
            if (!f.createNewFile()) {
                throw new RuntimeException("first createNewFile should return true");
            }
            if (!f.exists()) {
                throw new RuntimeException("file should exist after createNewFile");
            }
            if (f.createNewFile()) {
                throw new RuntimeException("second createNewFile should return false");
            }
        } finally {
            f.delete();
        }

        File invalid = new File("bad\u0000name");
        boolean thrown = false;
        try {
            invalid.createNewFile();
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for invalid file path");
        }
    }
}
