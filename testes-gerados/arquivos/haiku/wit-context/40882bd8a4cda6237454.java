/*
 * @test
 * @summary WITUP-derived checks for Runtime.exec(String, String[]) with empty, blank and null commands
 * @run main RuntimeWitupTest
 */

import java.io.IOException;

public class RuntimeWitupTest {

    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.getRuntime();
        testEmptyCommand(rt);
        testBlankCommand(rt);
        testNullCommand(rt);
    }

    static void testEmptyCommand(Runtime rt) {
        try {
            rt.exec("", null);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException for empty command", e);
        }
        throw new RuntimeException("expected exception for empty command");
    }

    static void testBlankCommand(Runtime rt) {
        try {
            rt.exec("   ", null);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException for blank command", e);
        }
        throw new RuntimeException("expected exception for blank command");
    }

    static void testNullCommand(Runtime rt) {
        try {
            rt.exec(null, null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException for null command", e);
        }
        throw new RuntimeException("expected NullPointerException for null command");
    }
}
