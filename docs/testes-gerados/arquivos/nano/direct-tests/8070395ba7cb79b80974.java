/*
 * @test
 * @summary Test Scanner.findWithinHorizon for various patterns and horizons
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindWithinHorizonWithExactMatch();
        testFindWithinHorizonWithNoMatch();
        testFindWithinHorizonWithPartialMatch();
        testFindWithinHorizonWithEmptyPattern();
        testFindWithinHorizonWithLargeHorizon();
    }

    static void testFindWithinHorizonWithExactMatch() {
        String input = "abc123xyz";
        Scanner scanner = new Scanner(input);
        String pattern = "abc";
        String result = scanner.findWithinHorizon(pattern, 10);
        assert result != null && result.equals("abc") : "Failed exact match test";
        scanner.close();
    }

    static void testFindWithinHorizonWithNoMatch() {
        String input = "abc123xyz";
        Scanner scanner = new Scanner(input);
        String pattern = "def";
        String result = scanner.findWithinHorizon(pattern, 10);
        assert result == null : "Failed no match test";
        scanner.close();
    }

    static void testFindWithinHorizonWithPartialMatch() {
        String input = "abc123xyz";
        Scanner scanner = new Scanner(input);
        String pattern = "123";
        String result = scanner.findWithinHorizon(pattern, 5);
        assert result != null && result.equals("123") : "Failed partial match test";
        scanner.close();
    }

    static void testFindWithinHorizonWithEmptyPattern() {
        String input = "some input";
        Scanner scanner = new Scanner(input);
        String pattern = "";
        String result = scanner.findWithinHorizon(pattern, 10);
        assert result != null && result.equals("") : "Failed empty pattern test";
        scanner.close();
    }

    static void testFindWithinHorizonWithLargeHorizon() {
        String input = "longer input string with multiple tokens";
        Scanner scanner = new Scanner(input);
        String pattern = "multiple";
        String result = scanner.findWithinHorizon(pattern, 100);
        assert result != null && result.equals("multiple") : "Failed large horizon test";
        scanner.close();
    }
}
