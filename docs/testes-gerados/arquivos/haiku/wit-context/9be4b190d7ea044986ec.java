/*
 * @test
 * @summary Checks observable behavior of File.getCanonicalFile() for relative, child and existing paths
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        File dot = new File(".");
        File canon = dot.getCanonicalFile();
        check(canon.isAbsolute(), "canonical file of '.' must be absolute");
        check(canon.getCanonicalPath().equals(dot.getCanonicalPath()),
              "canonical file path must match getCanonicalPath()");

        File child = new File(new File("."), "x");
        File canonChild = child.getCanonicalFile();
        check(canonChild.isAbsolute(), "canonical child must be absolute");
        check(canonChild.getName().equals("x"), "canonical child must keep its name");
        check(canonChild.getCanonicalPath().equals(child.getCanonicalPath()),
              "canonical child path must match getCanonicalPath()");

        File tmp = File.createTempFile("witup", ".tmp");
        try {
            File redundant = new File(tmp.getAbsoluteFile().getParentFile(),
                                      "." + File.separator + tmp.getName());
            check(redundant.getCanonicalFile().equals(tmp.getCanonicalFile()),
                  "redundant segment must canonicalize to the same file");
        } finally {
            tmp.delete();
        }
    }
}
