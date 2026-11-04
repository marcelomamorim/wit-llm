/*
 * @test
 * @summary Runtime.exec(String[]) starts a process with the given command and reports IOException for a nonexistent command
 * @run main RuntimeWitupTest
 */

import java.io.File;
import java.io.IOException;

public class RuntimeWitupTest {

    public static void main(String[] args) throws Exception {
        testValidCommandRunsAndExitsZero();
        testNonexistentCommandThrowsIOException();
    }

    static void testValidCommandRunsAndExitsZero() throws Exception {
        String javaHome = System.getProperty("java.home");
        boolean windows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        String exe = javaHome + File.separator + "bin" + File.separator + (windows ? "java.exe" : "java");

        Process p = Runtime.getRuntime().exec(new String[] { exe, "-version" });
        if (p == null) {
            throw new RuntimeException("exec returned null");
        }
        // drain output so the child cannot block on a full pipe
        p.getInputStream().readAllBytes();
        p.getErrorStream().readAllBytes();
        int rc = p.waitFor();
        if (rc != 0) {
            throw new RuntimeException("Expected exit code 0 but was " + rc);
        }
    }

    static void testNonexistentCommandThrowsIOException() {
        boolean thrown = false;
        try {
            Runtime.getRuntime().exec(new String[] { "no-such-command-witup-" + System.nanoTime() });
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for nonexistent command");
        }
    }
}
