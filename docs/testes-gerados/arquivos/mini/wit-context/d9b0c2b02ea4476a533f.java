/*
 * @test
 * @summary Test ProcessBuilder(List<String>) constructor for null and valid input
 * @run main ProcessBuilderWitupTest
 */

import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.List;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) {
        testConstructorNullCommand();
        testConstructorValidCommand();
        System.out.println("All tests passed.");
    }

    private static void testConstructorNullCommand() {
        try {
            new ProcessBuilder((List<String>) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorValidCommand() {
        List<String> cmd = new ArrayList<>();
        cmd.add("echo");
        cmd.add("hello");
        ProcessBuilder pb = new ProcessBuilder(cmd);
        if (pb == null) {
            throw new RuntimeException("ProcessBuilder constructor returned null");
        }
    }
}
