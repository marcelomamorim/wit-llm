/* @test    @summary Test Runtime.exec(String, String[]) method for normal execution and exception handling    @run main RuntimeWitupTest */

import java.io.*;
import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testExecWithValidCommand();
        testExecWithNullCommand();
    }

    private static void testExecWithValidCommand() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("java -version", null);
            int exitValue = process.waitFor();
            // Exit value is system-dependent; just check that process completes
            if (exitValue != 0 && exitValue != 1) {
                throw new AssertionError("Unexpected exit value: " + exitValue);
            }
        } catch (IOException | InterruptedException e) {
            throw new AssertionError("Execution failed: " + e);
        }
    }

    private static void testExecWithNullCommand() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(null, null);
            throw new AssertionError("Expected NullPointerException for null command");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
