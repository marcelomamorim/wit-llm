/* @test    @summary Test for ProcessBuilder constructor with null command    @run main ProcessBuilderWitupTest */

import java.lang.ProcessBuilder;
import java.util.Arrays;
import java.util.List;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCommand();
        testConstructorWithEmptyList();
        testConstructorWithValidList();
    }

    private static void testConstructorWithNullCommand() {
        try {
            new ProcessBuilder((List<String>) null);
            System.err.println("Failed: Expected NullPointerException for null command list");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithEmptyList() {
        try {
            ProcessBuilder pb = new ProcessBuilder(Arrays.asList());
            if (pb != null) {
                System.out.println("Passed: Constructor with empty list creates instance");
            } else {
                System.err.println("Failed: Constructor with empty list returned null");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithValidList() {
        try {
            List<String> command = Arrays.asList("echo", "hello");
            ProcessBuilder pb = new ProcessBuilder(command);
            if (pb != null) {
                System.out.println("Passed: Constructor with valid list");
            } else {
                System.err.println("Failed: Constructor with valid list returned null");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
