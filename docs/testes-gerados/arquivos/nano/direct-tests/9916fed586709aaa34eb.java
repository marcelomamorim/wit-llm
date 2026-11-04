/* @test    @summary Test for Scanner.hasNext() method to ensure it correctly identifies the presence of tokens.    @run main ScannerWitupTest */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextWithTokens();
        testHasNextWithoutTokens();
    }

    private static void testHasNextWithTokens() {
        String inputStr = "token1 token2";
        try (Scanner scanner = new Scanner(inputStr)) {
            boolean result = scanner.hasNext();
            if (!result) {
                throw new AssertionError("Expected hasNext() to return true when tokens are present.");
            }
        }
    }

    private static void testHasNextWithoutTokens() {
        String inputStr = "";
        try (Scanner scanner = new Scanner(inputStr)) {
            boolean result = scanner.hasNext();
            if (result) {
                throw new AssertionError("Expected hasNext() to return false when no tokens are present.");
            }
        }
    }
}
