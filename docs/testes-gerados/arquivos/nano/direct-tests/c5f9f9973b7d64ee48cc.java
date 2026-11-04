/*
 * @test
 * @summary Test the tokens() method of java.util.Scanner for correct stream behavior.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.util.stream.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testTokensReturnsStreamOfStrings();
        testTokensStreamCloseClosesScanner();
        testTokensStreamMultipleCalls();
    }

    private static void testTokensReturnsStreamOfStrings() {
        String input = "apple banana cherry";
        Scanner scanner = new Scanner(input);
        try (Stream<String> stream = scanner.tokens()) {
            List<String> tokens = stream.collect(Collectors.toList());
            assert tokens.size() == 3 : "Expected 3 tokens";
            assert tokens.get(0).equals("apple") : "First token should be 'apple'";
            assert tokens.get(1).equals("banana") : "Second token should be 'banana'";
            assert tokens.get(2).equals("cherry") : "Third token should be 'cherry'";
        }
    }

    private static void testTokensStreamCloseClosesScanner() {
        String input = "dog cat mouse";
        Scanner scanner = new Scanner(input);
        try (Stream<String> stream = scanner.tokens()) {
            List<String> tokens = stream.collect(Collectors.toList());
            assert tokens.size() == 3 : "Expected 3 tokens";
        }
        // After stream close, scanner should be closed; attempting to use it again should throw an exception
        try {
            scanner.next();
            assert false : "Expected IllegalStateException after stream close";
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    private static void testTokensStreamMultipleCalls() {
        String input = "one two";
        Scanner scanner = new Scanner(input);
        try (Stream<String> stream1 = scanner.tokens()) {
            List<String> tokens1 = stream1.collect(Collectors.toList());
            assert tokens1.size() == 2 : "Expected 2 tokens";
        }
        // Calling tokens() again should produce a new stream
        try (Stream<String> stream2 = scanner.tokens()) {
            List<String> tokens2 = stream2.collect(Collectors.toList());
            assert tokens2.size() == 2 : "Expected 2 tokens";
        }
        scanner.close();
    }
}
