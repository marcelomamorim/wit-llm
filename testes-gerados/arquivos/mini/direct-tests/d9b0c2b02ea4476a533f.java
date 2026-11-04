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
        testConstructorWithNull();
        testConstructorWithValidList();
    }

    private static void testConstructorWithNull() {
        try {
            new ProcessBuilder((List<String>) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorWithValidList() {
        List<String> command = new ArrayList<>();
        command.add("echo");
        command.add("hello");
        ProcessBuilder pb = new ProcessBuilder(command);
        if (pb == null) {
            throw new RuntimeException("ProcessBuilder instance is null");
        }
    }
}
