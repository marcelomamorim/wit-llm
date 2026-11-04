/*
 * @test
 * @summary Test for Scanner.hasNextLong() method.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextLongWithValidInput();
        testHasNextLongWithInvalidInput();
        testHasNextLongWithEmptyInput();
    }

    private static void testHasNextLongWithValidInput() {
        String inputStr = "1234567890";
        try (Scanner scanner = new Scanner(inputStr)) {
            boolean result = scanner.hasNextLong();
            if (!result) {
                throw new AssertionError("Expected hasNextLong() to be true for valid long input.");
            }
        }
    }

    private static void testHasNextLongWithInvalidInput() {
        String inputStr = "abc def";
        try (Scanner scanner = new Scanner(inputStr)) {
            boolean result = scanner.hasNextLong();
            if (result) {
                throw new AssertionError("Expected hasNextLong() to be false for non-long input.");
            }
        }
    }

    private static void testHasNextLongWithEmptyInput() {
        String inputStr = "";
        try (Scanner scanner = new Scanner(inputStr)) {
            boolean result = scanner.hasNextLong();
            if (result) {
                throw new AssertionError("Expected hasNextLong() to be false for empty input.");
            }
        }
    }
}
