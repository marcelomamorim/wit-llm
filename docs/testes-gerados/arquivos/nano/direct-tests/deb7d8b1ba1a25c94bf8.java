/*
 * @test
 * @summary Test the Scanner.next() method for correct token retrieval.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNextWithSimpleToken();
        testNextWithMultipleTokens();
        testNextAfterInputEnd();
    }

    private static void testNextWithSimpleToken() {
        String inputStr = "hello";
        try (Scanner scanner = new Scanner(new StringReader(inputStr))) {
            String token = scanner.next();
            if (!"hello".equals(token)) {
                throw new AssertionError("Expected 'hello', got: " + token);
            }
        }
    }

    private static void testNextWithMultipleTokens() {
        String inputStr = "one two three";
        try (Scanner scanner = new Scanner(new StringReader(inputStr))) {
            String first = scanner.next();
            String second = scanner.next();
            String third = scanner.next();
            if (!"one".equals(first) || !"two".equals(second) || !"three".equals(third)) {
                throw new AssertionError("Tokens do not match expected values.");
            }
        }
    }

    private static void testNextAfterInputEnd() {
        String inputStr = "end";
        try (Scanner scanner = new Scanner(new StringReader(inputStr))) {
            String token = scanner.next();
            if (!"end".equals(token)) {
                throw new AssertionError("Expected 'end', got: " + token);
            }
            boolean exceptionThrown = false;
            try {
                scanner.next();
            } catch (NoSuchElementException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected NoSuchElementException after input end.");
            }
        }
    }
}
