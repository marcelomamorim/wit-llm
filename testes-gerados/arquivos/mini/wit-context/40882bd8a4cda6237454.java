/*
 * @test
 * @summary Test Runtime.exec(String, String[]) for argument validation and exception paths
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.io.IOException;

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.getRuntime();

        // Test 1: command is empty string -> expect IllegalArgumentException
        try {
            rt.exec("");
            throw new RuntimeException("Expected IllegalArgumentException for empty command string");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test 2: command is non-empty string, envp null -> should not throw NullPointerException
        // According to expaths, NPE may occur if internal delimiters are null, but current code calls exec(command, envp, null)
        // We test that no NPE is thrown for a simple valid command
        try {
            Process p = rt.exec("echo Hello World", null);
            if (p == null) {
                throw new RuntimeException("Process returned is null");
            }
            // destroy process to avoid resource leak
            p.destroy();
        } catch (IOException ioe) {
            // IOException is allowed if command not found or OS error
        }

        // Test 3: command is non-empty string, envp empty array -> should not throw NullPointerException
        try {
            Process p = rt.exec("echo Hello World", new String[0]);
            if (p == null) {
                throw new RuntimeException("Process returned is null");
            }
            p.destroy();
        } catch (IOException ioe) {
            // IOException allowed
        }

        // No direct test for delimiters null because it's internal detail; tests above cover observable behavior
    }
}
