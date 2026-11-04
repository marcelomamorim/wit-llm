/*
 * @test
 * @summary WITUP-derived behavioral checks for File.deleteOnExit without exceptions for valid and invalid paths
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        String validName = "witup-deleteOnExit-probe.tmp";
        File valid = new File(validName);
        check(!valid.exists(), "probe file should not exist before the test");
        valid.deleteOnExit();
        valid.deleteOnExit();
        check(!valid.exists(), "deleteOnExit must not create the file");

        File invalid = new File("a" + (char) 0 + "b");
        invalid.deleteOnExit();
        check(!invalid.exists(), "invalid path must not be reported as existing");
        check(!invalid.isFile(), "invalid path must not be reported as a file");
    }
}
