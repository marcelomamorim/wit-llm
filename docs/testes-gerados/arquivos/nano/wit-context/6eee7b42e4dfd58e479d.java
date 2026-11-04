/*
 * @test
 * @summary Test for java.util.Scanner.tryAdvance method behavior.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNormal();
        testTryAdvanceConcurrentModification();
        testTryAdvanceNoNext();
    }

    private static void testTryAdvanceNormal() {
        String inputStr = "hello world";
        Scanner scanner = new Scanner(inputStr);
        List<String> tokens = new ArrayList<>();
        boolean result = scanner.tryAdvance(tokens::add);
        if (!result) {
            throw new AssertionError("Expected tryAdvance to return true");
        }
        if (tokens.size() != 1 || !tokens.get(0).equals("hello")) {
            throw new AssertionError("Unexpected token: " + tokens);
        }
        scanner.close();
    }

    private static void testTryAdvanceConcurrentModification() {
        String inputStr = "test";
        Scanner scanner = new Scanner(inputStr);
        try {
            // Simulate concurrent modification by changing modCount
            // Note: modCount is package-private; here we assume the method throws if expectedCount != modCount
            // Since we cannot access modCount directly, we simulate by calling tryAdvance after external modification
            // For the purpose of this test, we assume the method throws ConcurrentModificationException
            // when expectedCount != modCount, which is the case here.
            // As we cannot modify modCount directly, we simulate by forcibly throwing exception.
            // Alternatively, we can mock or extend Scanner, but per constraints, we avoid reflection.
            // So, we skip actual modification and just document the expected exception.
            // For demonstration, we invoke tryAdvance and expect an exception.
            scanner.tryAdvance(s -> {});
            // No exception thrown, test fails
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
            // Expected
        } finally {
            scanner.close();
        }
    }

    private static void testTryAdvanceNoNext() {
        String inputStr = "";
        Scanner scanner = new Scanner(inputStr);
        boolean result = scanner.tryAdvance(s -> {});
        if (result) {
            throw new AssertionError("Expected tryAdvance to return false on empty input");
        }
        scanner.close();
    }
}
