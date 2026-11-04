/* @test    @summary Test Scanner.next(Pattern pattern) for various patterns    @run main ScannerWitupTest */

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testNextWithSimplePattern();
        testNextWithComplexPattern();
        testNextWithNoMatchPattern();
        testNextWithNullPattern();
    }

    private static void testNextWithSimplePattern() {
        String input = "abc 123 def";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        String result = scanner.next(pattern);
        assert result.equals("123") : "Expected '123', got '" + result + "'";
        scanner.close();
    }

    private static void testNextWithComplexPattern() {
        String input = "foo bar 42 baz";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("[a-z]+\s[0-9]+" );
        String result = scanner.next(pattern);
        assert result.equals("bar 42") : "Expected 'bar 42', got '" + result + "'";
        scanner.close();
    }

    private static void testNextWithNoMatchPattern() {
        String input = "hello world";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        try {
            String result = scanner.next(pattern);
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
        scanner.close();
    }

    private static void testNextWithNullPattern() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            scanner.next(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        scanner.close();
    }
}
