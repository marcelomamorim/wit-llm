/*
 * @test
 * @summary Verifies ProcessBuilder(List<String>) rejects null command and keeps the given list observable
 * @run main ProcessBuilderWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCommandThrowsNullPointerException();
        testNonNullCommandIsAccepted();
        testCommandListIsUsedByBuilder();
    }

    static void testNullCommandThrowsNullPointerException() {
        try {
            new ProcessBuilder((List<String>) null);
            throw new AssertionError("Expected NullPointerException for null command");
        } catch (NullPointerException expected) {
        }
    }

    static void testNonNullCommandIsAccepted() {
        List<String> cmd = new ArrayList<>();
        cmd.add("echo");
        ProcessBuilder pb = new ProcessBuilder(cmd);
        if (pb.command() == null) {
            throw new AssertionError("command() returned null");
        }
    }

    static void testCommandListIsUsedByBuilder() {
        List<String> cmd = new ArrayList<>();
        cmd.add("a");
        ProcessBuilder pb = new ProcessBuilder(cmd);
        if (!pb.command().equals(cmd)) {
            throw new AssertionError("command() does not reflect the constructor argument");
        }
    }
}
