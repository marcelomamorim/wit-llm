/*
 * @test
 * @summary Tests for java.lang.Runtime.exec(String)
 * @run main RuntimeWitupTest
 */

import java.io.*;
import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testExecNullDelimiters();
        testExecEmptyCommand();
        testExecNullDelimitersDifferentConditions();
    }

    private static void testExecNullDelimiters() {
        Runtime runtime = Runtime.getRuntime();
        String command = "echo test";
        try {
            Process p = runtime.exec(command);
            if (p == null) {
                throw new AssertionError("Process should not be null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testExecEmptyCommand() {
        Runtime runtime = Runtime.getRuntime();
        String command = "";
        try {
            Process p = runtime.exec(command);
            if (p == null) {
                throw new AssertionError("Process should not be null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testExecNullDelimitersDifferentConditions() {
        // Since the method implementation calls exec(command, null, null),
        // and the source code does not process delimiters, this test is a placeholder
        // to ensure no exceptions occur when calling exec with a valid command.
        Runtime runtime = Runtime.getRuntime();
        String command = "echo test";
        try {
            Process p = runtime.exec(command);
            if (p == null) {
                throw new AssertionError("Process should not be null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
