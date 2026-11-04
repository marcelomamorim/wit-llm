/* @test    @summary Test Runtime.exec(String command) method for basic execution and exception handling    @run main RuntimeWitupTest */

import java.io.*;
import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testExecWithValidCommand();
        testExecWithInvalidCommand();
    }

    private static void testExecWithValidCommand() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("java -version");
            int exitValue = process.waitFor();
            // Exit value is typically 0 for successful execution
            if (exitValue != 0) {
                throw new AssertionError("Expected exit value 0, but got " + exitValue);
            }
        } catch (IOException | InterruptedException e) {
            throw new AssertionError("Unexpected exception during exec with valid command", e);
        }
    }

    private static void testExecWithInvalidCommand() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("invalid_command_that_does_not_exist");
            int exitValue = process.waitFor();
            // Process should terminate with non-zero exit value
            if (exitValue == 0) {
                throw new AssertionError("Expected non-zero exit value for invalid command, but got 0");
            }
        } catch (IOException | InterruptedException e) {
            // IOException is expected if command cannot be executed
            if (!(e instanceof IOException)) {
                throw new AssertionError("Unexpected exception type", e);
            }
        }
    }
}
