/*
 * @test
 * @summary Observable behavior of File.canExecute() for missing, invalid and existing paths
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {
    public static void main(String[] args) throws IOException {
        File missing = new File("this-file-should-not-exist-witup-12345");
        if (missing.canExecute()) {
            throw new RuntimeException("canExecute() returned true for a missing file");
        }

        File invalid = new File("bad\u0000name");
        if (invalid.canExecute()) {
            throw new RuntimeException("canExecute() returned true for an invalid pathname");
        }

        File tmp = File.createTempFile("witup", ".tmp");
        try {
            tmp.canExecute();
        } finally {
            tmp.delete();
        }
    }
}
