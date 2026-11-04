/*
 * @test
 * @summary Test java.io.Console.format(String, Object...) for normal and exceptional behavior
 * @run main ConsoleWitupTest
 */

import java.io.Console;
import java.util.FormatterClosedException;

public class ConsoleWitupTest {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            // No console available, skip test with message
            System.out.println("No console available, skipping tests.");
            return;
        }

        // Test normal usage of format with valid format string and arguments
        Console result = console.format("Hello %s! %d %f", "World", 42, 3.14);
        if (result != console) {
            throw new AssertionError("format should return this Console instance");
        }

        // Test that format throws FormatterClosedException if formatter is closed
        // We cannot close the formatter directly, so we simulate by closing the console's writer
        // and then calling format, expecting FormatterClosedException
        try {
            // Close the console's writer to close the underlying formatter
            console.writer().close();
            console.format("This should fail");
            throw new AssertionError("Expected FormatterClosedException not thrown");
        } catch (FormatterClosedException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
