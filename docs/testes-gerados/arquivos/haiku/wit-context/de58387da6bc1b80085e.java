/*
 * @test
 * @summary Witup-derived checks for Runtime.exec(String) argument validation
 * @run main RuntimeWitupTest
 */

import java.io.IOException;

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        int failures = 0;

        boolean iaeThrown = false;
        try {
            Runtime.getRuntime().exec("");
        } catch (IllegalArgumentException e) {
            iaeThrown = true;
        }
        if (!iaeThrown) {
            System.err.println("Expected IllegalArgumentException for empty command");
            failures++;
        }

        boolean npeThrown = false;
        try {
            Runtime.getRuntime().exec((String) null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            System.err.println("Expected NullPointerException for null command");
            failures++;
        }

        boolean ioThrown = false;
        try {
            Runtime.getRuntime().exec("witup_nonexistent_command_4f9a1c");
        } catch (IOException e) {
            ioThrown = true;
        }
        if (!ioThrown) {
            System.err.println("Expected IOException for nonexistent command");
            failures++;
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
