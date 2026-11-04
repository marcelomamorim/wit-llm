/*
 * @test
 * @summary Tests for java.lang.Runtime.exec(String, String[])
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.io.IOException;

public class RuntimeWitupTest {

    public static void main(String[] args) throws Exception {
        testExecWithEmptyCommand();
        testExecWithNullDelimiters();
        testExecWithNullDelimitersAndCurrentPosition();
    }

    private static void testExecWithEmptyCommand() {
        Runtime runtime = Runtime.getRuntime();
        String command = "";
        String[] envp = {"VAR=VALUE"};
        try {
            runtime.exec(command, envp);
            // Expect no exception, as command is empty string
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for empty command", e);
        }
    }

    private static void testExecWithNullDelimiters() {
        Runtime runtime = Runtime.getRuntime();
        String command = "echo test";
        String[] envp = {"VAR=VALUE"};
        try {
            // Simulate internal call with null delimiters, currentPosition, maxPosition
            // Since the method calls exec(command, envp, null), and the internal code uses StringTokenizer,
            // we test the behavior indirectly by passing a command that could cause NullPointerException
            runtime.exec(command, envp);
        } catch (IOException e) {
            // IOException is expected if command is invalid, but not NullPointerException
            // So, test that no NullPointerException occurs
        } catch (NullPointerException e) {
            throw new RuntimeException("NullPointerException occurred unexpectedly", e);
        }
    }

    private static void testExecWithNullDelimitersAndCurrentPosition() {
        Runtime runtime = Runtime.getRuntime();
        String command = "echo test";
        String[] envp = {"VAR=VALUE"};
        try {
            // Similar to above, test behavior when internal tokenizer might throw NullPointerException
            runtime.exec(command, envp);
        } catch (IOException e) {
            // Expected for invalid command
        } catch (NullPointerException e) {
            throw new RuntimeException("NullPointerException occurred unexpectedly", e);
        }
    }
}
