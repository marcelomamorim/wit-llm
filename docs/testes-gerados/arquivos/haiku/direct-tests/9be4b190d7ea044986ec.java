/*
 * @test
 * @summary Verify File.getCanonicalFile normalizes paths and is consistent with getCanonicalPath
 * @run main FileWitupTest
 */
import java.io.File;

public class FileWitupTest {
    public static void main(String[] args) throws Exception {
        String base = new File(".").getCanonicalPath();
        File baseDir = new File(base);

        File dotted = new File(baseDir, "x" + File.separator + ".." + File.separator + "y");
        File canon = dotted.getCanonicalFile();
        String expected = new File(baseDir, "y").getCanonicalPath();

        if (!canon.getPath().equals(expected)) {
            throw new RuntimeException("Expected " + expected + " but got " + canon.getPath());
        }
        if (!canon.getPath().equals(dotted.getCanonicalPath())) {
            throw new RuntimeException("getCanonicalFile disagrees with getCanonicalPath");
        }
        if (!canon.isAbsolute()) {
            throw new RuntimeException("Canonical file is not absolute: " + canon.getPath());
        }
        if (!canon.getCanonicalFile().getPath().equals(canon.getPath())) {
            throw new RuntimeException("getCanonicalFile is not idempotent");
        }

        File cur = new File(".").getCanonicalFile();
        if (!cur.getPath().equals(base)) {
            throw new RuntimeException("Expected current dir " + base + " but got " + cur.getPath());
        }
    }
}
