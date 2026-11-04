/*
 * @test
 * @summary Verify ProcessBuilder.command(List) rejects null and supports chaining
 * @run main ProcessBuilderWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testNullCommandThrowsNPE();
        testNonNullCommandReturnsSameBuilder();
        testEmptyListReturnsSameBuilder();
    }

    static void testNullCommandThrowsNPE() {
        ProcessBuilder pb = new ProcessBuilder("echo");
        boolean thrown = false;
        try {
            pb.command((List<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null command list");
        }
    }

    static void testNonNullCommandReturnsSameBuilder() {
        ProcessBuilder pb = new ProcessBuilder("echo");
        List<String> cmd = new ArrayList<>();
        cmd.add("echo");
        cmd.add("hello");
        ProcessBuilder result = pb.command(cmd);
        if (result != pb) {
            throw new RuntimeException("command(List) must return the same ProcessBuilder instance");
        }
    }

    static void testEmptyListReturnsSameBuilder() {
        ProcessBuilder pb = new ProcessBuilder("echo");
        ProcessBuilder result = pb.command(new ArrayList<String>());
        if (result != pb) {
            throw new RuntimeException("command(List) with empty list must return the same ProcessBuilder instance");
        }
    }
}
