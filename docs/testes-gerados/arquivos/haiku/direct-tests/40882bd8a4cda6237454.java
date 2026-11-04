/*
 * @test
 * @summary Tests Runtime.exec(String, String[]) delegation: a valid command with envp runs, and a nonexistent command raises IOException
 * @run main RuntimeWitupTest
 */

import java.io.File;
import java.io.IOException;

public class RuntimeWitupTest {

    static String javaBinary() {
        String name = System.getProperty("os.name", "").startsWith("Windows") ? "java.exe" : "java";
        return System.getProperty("java.home") + File.separator + "bin" + File.separator + name;
    }

    static void testNonexistentCommandThrowsIOException() {
        boolean thrown = false;
        try {
            Runtime.getRuntime().exec("witup_no_such_command_" + System.nanoTime(), new String[] {"WITUP_TEST=1"});
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for nonexistent command");
        }
    }

    static void testValidCommandWithEnvpReturnsProcess() throws Exception {
        Process p = Runtime.getRuntime().exec(javaBinary() + " -version",
                                              new String[] {"WITUP_TEST=1"});
        if (p == null) {
            throw new RuntimeException("exec returned null");
        }
        int code = p.waitFor();
        if (code != 0) {
            throw new RuntimeException("Expected exit code 0 but was " + code);
        }
    }

    public static void main(String[] args) throws Exception {
        testNonexistentCommandThrowsIOException();
        testValidCommandWithEnvpReturnsProcess();
    }
}
