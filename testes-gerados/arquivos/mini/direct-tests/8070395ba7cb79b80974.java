/*
 * @test
 * @summary Basic tests for Scanner.findWithinHorizon(String, int)
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindWithinHorizonSimpleMatch();
        testFindWithinHorizonNoMatch();
        testFindWithinHorizonZeroHorizon();
        testFindWithinHorizonNegativeHorizon();
    }

    private static void testFindWithinHorizonSimpleMatch() {
        String input = "abc 123 def";
        Scanner scanner = new Scanner(input);
        String result = scanner.findWithinHorizon("\\d+", 10);
        if (!"123".equals(result)) {
            throw new RuntimeException("Expected '123' but got: " + result);
        }
        scanner.close();
    }

    private static void testFindWithinHorizonNoMatch() {
        String input = "abc def ghi";
        Scanner scanner = new Scanner(input);
        String result = scanner.findWithinHorizon("\\d+", 10);
        if (result != null) {
            throw new RuntimeException("Expected null but got: " + result);
        }
        scanner.close();
    }

    private static void testFindWithinHorizonZeroHorizon() {
        String input = "abc 123 def";
        Scanner scanner = new Scanner(input);
        String result = scanner.findWithinHorizon("\\d+", 0);
        if (result != null) {
            throw new RuntimeException("Expected null with zero horizon but got: " + result);
        }
        scanner.close();
    }

    private static void testFindWithinHorizonNegativeHorizon() {
        String input = "abc 123 def";
        Scanner scanner = new Scanner(input);
        String result = scanner.findWithinHorizon("\\d+", -1);
        if (!"123".equals(result)) {
            throw new RuntimeException("Expected '123' with negative horizon but got: " + result);
        }
        scanner.close();
    }
}
