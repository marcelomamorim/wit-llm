/*
 * @test
 * @summary Test Scanner.hasNext(String pattern) for various patterns.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextWithExactMatch();
        testHasNextWithNoMatch();
        testHasNextWithEmptyPattern();
        testHasNextWithSpecialCharacters();
        testHasNextWithMultipleMatches();
    }

    private static void testHasNextWithExactMatch() {
        String input = "abc123";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNext("abc123");
            if (!result) {
                throw new AssertionError("Expected hasNext("abc123") to be true");
            }
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextWithNoMatch() {
        String input = "abc123";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNext("xyz");
            if (result) {
                throw new AssertionError("Expected hasNext("xyz") to be false");
            }
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextWithEmptyPattern() {
        String input = "some text";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNext("");
            if (!result) {
                throw new AssertionError("Expected hasNext("") to be true");
            }
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextWithSpecialCharacters() {
        String input = "a.b*c?";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNext("a\.b\*c\?");
            if (!result) {
                throw new AssertionError("Expected hasNext("a\.b\*c\?") to be true");
            }
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextWithMultipleMatches() {
        String input = "123 456 789";
        Scanner scanner = new Scanner(input);
        try {
            boolean result1 = scanner.hasNext("\d+");
            boolean result2 = scanner.hasNext("\d+");
            if (!result1 || !result2) {
                throw new AssertionError("Expected multiple hasNext(\d+) calls to be true");
            }
        } finally {
            scanner.close();
        }
    }
}
