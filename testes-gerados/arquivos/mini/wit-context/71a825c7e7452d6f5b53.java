/*
 * @test
 * @summary Test Runtime.exec(String[]) method for expected exceptions and normal behavior
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.io.IOException;

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.getRuntime();

        // Test normal behavior: exec with a valid command array
        // We use a simple command that should exist on all platforms: "echo" or "cmd /c echo" on Windows
        // Since we cannot assume platform, test that exec does not return null and does not throw IOException for a simple command
        String[] validCmd = getValidCommand();
        Process p = rt.exec(validCmd);
        if (p == null) {
            throw new AssertionError("exec returned null Process for valid command");
        }
        p.destroy(); // cleanup

        // Test behavior with empty command array - expect IOException or IllegalArgumentException or similar
        // According to source, empty command string triggers IllegalArgumentException in underlying exec(String, String[], File)
        // But here we pass empty array, so test that exec throws IOException or IllegalArgumentException
        try {
            rt.exec(new String[0]);
            throw new AssertionError("exec did not throw exception for empty command array");
        } catch (IOException | IllegalArgumentException e) {
            // expected
        }

        // Test behavior with null command array - should throw NullPointerException
        try {
            rt.exec((String[]) null);
            throw new AssertionError("exec did not throw NullPointerException for null command array");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static String[] getValidCommand() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return new String[] {"cmd", "/c", "echo", "test"};
        } else {
            return new String[] {"echo", "test"};
        }
    }
}
