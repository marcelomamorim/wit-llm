/* @test    @summary Test the command(List<String>) method of ProcessBuilder for null input handling    @run main ProcessBuilderWitupTest */

import java.lang.ProcessBuilder;
import java.util.Arrays;
import java.util.List;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testCommandWithNull();
        testCommandWithValidList();
    }

    public static void testCommandWithNull() {
        ProcessBuilder pb = new ProcessBuilder();
        try {
            pb.command((List<String>) null);
            // If no exception, test fails
            System.err.println("FAIL: command(null) did not throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("PASS: command(null) threw NullPointerException as expected");
        } catch (Exception e) {
            System.err.println("FAIL: command(null) threw unexpected exception: " + e);
        }
    }

    public static void testCommandWithValidList() {
        List<String> command = Arrays.asList("echo", "hello");
        ProcessBuilder pb = new ProcessBuilder();
        try {
            pb.command(command);
            // Verify that the command was set correctly
            if (pb.command().equals(command)) {
                System.out.println("PASS: command(List) with valid list sets command correctly");
            } else {
                System.err.println("FAIL: command(List) with valid list did not set command correctly");
            }
        } catch (Exception e) {
            System.err.println("FAIL: command(List) with valid list threw exception: " + e);
        }
    }
}
