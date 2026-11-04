/*
 * @test
 * @summary Test for Scanner.match() method to verify exception throwing behavior.
 * @run main ScannerWitupTest
 */

import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testMatchThrowsIllegalStateExceptionWhenMatchInvalid();
        testMatchReturnsMatchResultWhenMatchValid();
    }

    private static void testMatchThrowsIllegalStateExceptionWhenMatchInvalid() {
        Scanner scanner = new Scanner("test");
        // Simulate invalid match state
        // Since matchValid is private, we cannot set it directly; instead, we simulate the invalid state by calling match() without prior valid match
        try {
            scanner.match();
            // If no exception, test fails
            System.err.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            // Expected exception
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testMatchReturnsMatchResultWhenMatchValid() {
        Scanner scanner = new Scanner("abc");
        // Use findInLine to set match state
        scanner.findInLine("a");
        try {
            MatchResult result = scanner.match();
            if (result != null) {
                System.out.println("PASS: match() returned a MatchResult.");
            } else {
                System.err.println("FAIL: match() returned null.");
            }
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
