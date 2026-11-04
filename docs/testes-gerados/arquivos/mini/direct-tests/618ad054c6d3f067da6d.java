/*
 * @test
 * @summary Test ProcessBuilder.command(List<String>) method behavior
 * @run main ProcessBuilderWitupTest
 */

import java.lang.ProcessBuilder;
import java.util.List;
import java.util.ArrayList;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) {
        testCommandReturnsSameInstance();
        testCommandSetsCommandList();
        testCommandNullThrowsNPE();
    }

    private static void testCommandReturnsSameInstance() {
        ProcessBuilder pb = new ProcessBuilder("echo");
        List<String> cmd = new ArrayList<>();
        cmd.add("ls");
        ProcessBuilder ret = pb.command(cmd);
        if (ret != pb) {
            throw new AssertionError("command() should return the same ProcessBuilder instance");
        }
    }

    private static void testCommandSetsCommandList() {
        ProcessBuilder pb = new ProcessBuilder("echo");
        List<String> cmd = new ArrayList<>();
        cmd.add("java");
        cmd.add("-version");
        pb.command(cmd);
        List<String> result = pb.command();
        if (result == null) {
            throw new AssertionError("command() returned null after setting command list");
        }
        if (result.size() != cmd.size()) {
            throw new AssertionError("command() returned list of different size");
        }
        for (int i = 0; i < cmd.size(); i++) {
            if (!cmd.get(i).equals(result.get(i))) {
                throw new AssertionError("command() returned list with different elements");
            }
        }
    }

    private static void testCommandNullThrowsNPE() {
        ProcessBuilder pb = new ProcessBuilder("echo");
        try {
            pb.command(null);
            throw new AssertionError("command(null) should throw NullPointerException");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
