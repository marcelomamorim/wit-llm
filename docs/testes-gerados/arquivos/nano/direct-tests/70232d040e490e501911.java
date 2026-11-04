/* @test    @summary Test Console.format with various inputs    @run main ConsoleWitupTest */

import java.io.Console;

public class ConsoleWitupTest {

    public static void main(String[] args) {
        testFormatWithSimpleString();
        testFormatWithMultipleArguments();
        testFormatWithNullArguments();
        testFormatWithEmptyFormat();
        testFormatWithNullFormat();
    }

    static void testFormatWithSimpleString() {
        Console console = new Console();
        try {
            console.format("Hello World");
        } catch (Exception e) {
            throw new AssertionError("Failed to format simple string", e);
        }
    }

    static void testFormatWithMultipleArguments() {
        Console console = new Console();
        try {
            console.format("Number: %d, String: %s", 42, "test");
        } catch (Exception e) {
            throw new AssertionError("Failed to format with multiple arguments", e);
        }
    }

    static void testFormatWithNullArguments() {
        Console console = new Console();
        try {
            console.format("Null args: %s", (Object) null);
        } catch (Exception e) {
            throw new AssertionError("Failed to format with null argument", e);
        }
    }

    static void testFormatWithEmptyFormat() {
        Console console = new Console();
        try {
            console.format("");
        } catch (Exception e) {
            throw new AssertionError("Failed to format empty string", e);
        }
    }

    static void testFormatWithNullFormat() {
        Console console = new Console();
        try {
            console.format(null);
            throw new AssertionError("Expected NullPointerException for null format");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null format", e);
        }
    }
}
