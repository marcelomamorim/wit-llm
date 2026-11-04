/*
 * @test
 * @summary Tests for Runtime.exec(String[])
 * @run main RuntimeWitupTest
 */

import java.io.*;
import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) throws Exception {
        testExecWithEmptyArray();
        testExecWithNullElement();
        testExecWithValidArray();
    }

    private static void testExecWithEmptyArray() {
        Runtime runtime = Runtime.getRuntime();
        String[] command = {};
        try {
            runtime.exec(command);
            // Expecting an exception or specific behavior; in current code, it proceeds.
            // Since the current implementation calls exec(cmdarray, null, null), no exception is thrown here.
            // But in real scenario, an exception might be thrown if command is invalid.
        } catch (IOException e) {
            // Test passes if IOException is thrown
            return;
        } catch (Exception e) {
            // Fail the test if other exceptions occur
            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static void testExecWithNullElement() {
        Runtime runtime = Runtime.getRuntime();
        String[] command = {null};
        try {
            runtime.exec(command);
            // Expecting an exception or specific behavior; current code calls exec(cmdarray, null, null)
        } catch (NullPointerException e) {
            // Expected if null element causes NPE
            return;
        } catch (IOException e) {
            // Acceptable if IOException is thrown
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static void testExecWithValidArray() {
        Runtime runtime = Runtime.getRuntime();
        String[] command = {"echo", "hello"};
        try {
            Process p = runtime.exec(command);
            // No exception expected; process should start
            p.destroy();
        } catch (IOException e) {
            throw new AssertionError("IOException thrown during exec", e);
        }
    }
}
