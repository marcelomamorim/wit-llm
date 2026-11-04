/*
 * @test
 * @summary Basic tests for java.util.Formatter.format(String, Object...)
 * @run main FormatterWitupTest
 */

import java.util.Formatter;

public class FormatterWitupTest {
    public static void main(String[] args) {
        Formatter formatter = new Formatter();

        // Test 1: Simple string formatting
        Formatter f1 = formatter.format("Hello %s", "World");
        if (f1 != formatter) throw new AssertionError("format should return this Formatter instance");

        // Test 2: Format integer
        Formatter f2 = formatter.format("Number: %d", 42);
        if (f2 != formatter) throw new AssertionError("format should return this Formatter instance");

        // Test 3: Format floating point
        Formatter f3 = formatter.format("Float: %.2f", 3.14159);
        if (f3 != formatter) throw new AssertionError("format should return this Formatter instance");

        // Test 4: Format multiple arguments
        Formatter f4 = formatter.format("%s %d %.1f", "Test", 7, 2.5);
        if (f4 != formatter) throw new AssertionError("format should return this Formatter instance");

        // Test 5: Format with no arguments
        Formatter f5 = formatter.format("No args");
        if (f5 != formatter) throw new AssertionError("format should return this Formatter instance");

        // Test 6: Format with null argument
        Formatter f6 = formatter.format("Null: %s", (Object) null);
        if (f6 != formatter) throw new AssertionError("format should return this Formatter instance");

        System.out.println("All tests passed.");
    }
}
