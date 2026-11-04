/*
 * @test
 * @summary Verifies observable behavior of Runtime.exec(String) for missing commands and null input
 * @run main RuntimeWitupTest
 */

import java.io.IOException;

public class RuntimeWitupTest {

    public static void main(String[] args) throws Exception {
        testExecMissingCommandThrowsIOException();
        testExecNullCommandThrowsNullPointerException();
    }

    static void testExecMissingCommandThrowsIOException() {
        String cmd = "witup-nonexistent-command-4f9a2c7e";
        boolean thrown = false;
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for missing command: " + cmd);
        }
    }

    static void testExecNullCommandThrowsNullPointerException() {
        boolean thrown = false;
        try {
            Runtime.getRuntime().exec((String) null);
        } catch (NullPointerException expected) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null command", e);
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null command");
        }
    }
}
