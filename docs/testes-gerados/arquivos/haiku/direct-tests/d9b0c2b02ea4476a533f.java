/*
 * @test
 * @summary Verifies ProcessBuilder(List<String>) null rejection and command list retention via public API
 * @run main ProcessBuilderWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testNullCommandThrowsNPE();
        testCommandReturnsSameListInstance();
        testCommandPreservesElementsAndOrder();
        testEmptyCommandListAccepted();
    }

    static void testNullCommandThrowsNPE() {
        boolean thrown = false;
        try {
            new ProcessBuilder((List<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null command list");
        }
    }

    static void testCommandReturnsSameListInstance() {
        List<String> cmd = new ArrayList<>();
        cmd.add("echo");
        ProcessBuilder pb = new ProcessBuilder(cmd);
        if (pb.command() != cmd) {
            throw new RuntimeException("command() should return the list passed to the constructor");
        }
    }

    static void testCommandPreservesElementsAndOrder() {
        List<String> cmd = Arrays.asList("a", "b", "c");
        ProcessBuilder pb = new ProcessBuilder(cmd);
        if (!pb.command().equals(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("command() did not preserve elements and order");
        }
    }

    static void testEmptyCommandListAccepted() {
        ProcessBuilder pb = new ProcessBuilder(new ArrayList<String>());
        if (pb.command().size() != 0) {
            throw new RuntimeException("Expected empty command list");
        }
    }
}
