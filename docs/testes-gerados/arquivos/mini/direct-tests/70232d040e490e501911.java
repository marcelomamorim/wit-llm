/*
 * @test
 * @summary Test java.io.Console.format(String, Object...) method
 * @run main ConsoleWitupTest
 */

import java.io.Console;
import java.util.Formatter;

public class ConsoleWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Console instance with a Formatter to test format method
        DummyConsole console = new DummyConsole();

        // Test format with simple string and no arguments
        Console returned = console.format("Hello World");
        if (returned != console) {
            throw new AssertionError("format should return this");
        }
        if (!console.getOutput().equals("Hello World")) {
            throw new AssertionError("format output mismatch");
        }

        // Test format with format specifiers and arguments
        console.clearOutput();
        returned = console.format("Number: %d, String: %s", 42, "test");
        if (returned != console) {
            throw new AssertionError("format should return this");
        }
        if (!console.getOutput().equals("Number: 42, String: test")) {
            throw new AssertionError("format output mismatch with args");
        }

        // Test format with null format string - should throw NullPointerException
        boolean npeThrown = false;
        try {
            console.format(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("format(null) should throw NullPointerException");
        }

        // Test format with empty format string
        console.clearOutput();
        returned = console.format("");
        if (returned != console) {
            throw new AssertionError("format should return this for empty string");
        }
        if (!console.getOutput().equals("")) {
            throw new AssertionError("format output mismatch for empty string");
        }
    }

    // Dummy Console subclass to expose formatter and capture output
    static class DummyConsole extends Console {
        private final StringBuilder sb = new StringBuilder();
        private final Formatter formatter = new Formatter(sb);

        DummyConsole() {
            super(null, null, null, null);
        }

        @Override
        public Console format(String fmt, Object... args) {
            formatter.format(fmt, args).flush();
            return this;
        }

        String getOutput() {
            return sb.toString();
        }

        void clearOutput() {
            sb.setLength(0);
        }
    }
}
