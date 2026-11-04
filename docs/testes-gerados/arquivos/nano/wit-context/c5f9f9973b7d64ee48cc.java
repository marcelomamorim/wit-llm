/*
 * @test
 * @summary Test for Scanner.tokens() method to verify behavior when scanner is closed.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testTokensWhenScannerIsOpen();
        testTokensWhenScannerIsClosed();
    }

    private static void testTokensWhenScannerIsOpen() {
        String input = "sample input";
        Scanner scanner = new Scanner(input);
        try {
            Stream<String> stream = scanner.tokens();
            boolean hasElements = stream.anyMatch(s -> s != null);
            if (!hasElements) {
                throw new AssertionError("Expected non-empty stream when scanner is open.");
            }
            stream.close();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception when scanner is open: " + e);
        }
    }

    private static void testTokensWhenScannerIsClosed() {
        String input = "sample input";
        Scanner scanner = new Scanner(input);
        Stream<String> stream = scanner.tokens();
        stream.close();
        try {
            stream.anyMatch(s -> s != null);
            throw new AssertionError("Expected exception when using stream after close.");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
