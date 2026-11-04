/*
 * @test
 * @summary Test ProcessBuilder.command(List<String>) method for null argument and normal usage
 * @run main ProcessBuilderWitupTest
 */

import java.lang.ProcessBuilder;
import java.util.Arrays;
import java.util.List;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) {
        testCommandWithNull();
        testCommandWithValidList();
    }

    private static void testCommandWithNull() {
        ProcessBuilder pb = new ProcessBuilder("echo");
        try {
            pb.command(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testCommandWithValidList() {
        List<String> cmd = Arrays.asList("echo", "hello");
        ProcessBuilder pb = new ProcessBuilder("dummy");
        ProcessBuilder result = pb.command(cmd);
        if (result != pb) {
            throw new RuntimeException("command(List<String>) should return this");
        }
    }
}
