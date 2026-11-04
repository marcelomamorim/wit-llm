/*
 * @test
 * @summary Test Runtime.exec(String, String[]) method behavior
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.io.IOException;
import java.lang.Process;

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();

        // Test 1: exec with a valid command and null environment
        Process p1 = runtime.exec("echo Hello", null);
        if (p1 == null) {
            throw new RuntimeException("Process returned is null");
        }
        p1.destroy();

        // Test 2: exec with a valid command and empty environment
        Process p2 = runtime.exec("echo Hello", new String[0]);
        if (p2 == null) {
            throw new RuntimeException("Process returned is null");
        }
        p2.destroy();

        // Test 3: exec with a likely invalid command to check IOException
        boolean ioExceptionThrown = false;
        try {
            runtime.exec("nonexistent_command_xyz", null);
        } catch (IOException e) {
            ioExceptionThrown = true;
        }
        if (!ioExceptionThrown) {
            throw new RuntimeException("Expected IOException was not thrown for invalid command");
        }

        // Test 4: exec with null command should throw NullPointerException
        boolean npeThrown = false;
        try {
            runtime.exec(null, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException was not thrown for null command");
        }
    }
}
