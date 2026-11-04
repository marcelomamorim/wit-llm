/*
 * @test
 * @summary Test Runtime.exec(String) method basic behavior
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.io.IOException;

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();

        // Test 1: Execute a simple command that should succeed
        Process p1 = runtime.exec("echo Hello");
        if (p1 == null) {
            throw new RuntimeException("Process returned by exec is null");
        }
        int exitCode1 = p1.waitFor();
        if (exitCode1 != 0) {
            throw new RuntimeException("Process exit code expected 0 but was " + exitCode1);
        }

        // Test 2: Execute a command that likely does not exist to check IOException
        boolean ioExceptionThrown = false;
        try {
            runtime.exec("nonexistent_command_xyz");
        } catch (IOException e) {
            ioExceptionThrown = true;
        }
        if (!ioExceptionThrown) {
            throw new RuntimeException("Expected IOException for invalid command");
        }

        // Test 3: Execute null command should throw NullPointerException
        boolean npeThrown = false;
        try {
            runtime.exec(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null command");
        }
    }
}
