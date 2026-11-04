/*
 * @test
 * @summary Test Runtime.exec(String[]) method behavior
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.io.IOException;

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.getRuntime();

        // Test with a valid command array (platform dependent, use "true" command which exists on Unix-like systems)
        // This test assumes "true" command is available; if not, it will throw IOException
        try {
            Process p = rt.exec(new String[]{"true"});
            if (p == null) {
                throw new RuntimeException("Process returned is null");
            }
            p.waitFor();
        } catch (IOException e) {
            // IOException is allowed if command not found, print message
            System.out.println("IOException caught as expected if command not found: " + e.getMessage());
        }

        // Test with null argument - should throw NullPointerException or IOException
        try {
            rt.exec((String[]) null);
            throw new RuntimeException("Expected NullPointerException or IOException for null argument");
        } catch (NullPointerException | IOException e) {
            // Expected exception
        }

        // Test with empty array - should throw IOException
        try {
            rt.exec(new String[0]);
            throw new RuntimeException("Expected IOException for empty command array");
        } catch (IOException e) {
            // Expected IOException
        }

        // Test with array containing null element - should throw IOException
        try {
            rt.exec(new String[]{null});
            throw new RuntimeException("Expected IOException for command array with null element");
        } catch (IOException e) {
            // Expected IOException
        }

        System.out.println("Runtime.exec(String[]) tests passed.");
    }
}
