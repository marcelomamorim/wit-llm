/*
 * @test
 * @summary Test tryAdvance method of java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNextToken();
        testTryAdvanceWhenNoTokens();
        testTryAdvanceWithConcurrentModification();
    }

    private static void testTryAdvanceWithNextToken() {
        Scanner scanner = new Scanner("token1 token2");
        List<String> collectedTokens = new ArrayList<>();
        boolean result = scanner.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                collectedTokens.add(s);
            }
        });
        assert result : "Expected tryAdvance to return true when tokens are available";
        assert collectedTokens.size() == 1 : "Expected one token to be consumed";
        assert "token1".equals(collectedTokens.get(0)) : "Expected token1 to be consumed";
        scanner.close();
    }

    private static void testTryAdvanceWhenNoTokens() {
        Scanner scanner = new Scanner("");
        List<String> collectedTokens = new ArrayList<>();
        boolean result = scanner.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                collectedTokens.add(s);
            }
        });
        assert !result : "Expected tryAdvance to return false when no tokens";
        assert collectedTokens.isEmpty() : "No tokens should be collected";
        scanner.close();
    }

    private static void testTryAdvanceWithConcurrentModification() {
        Scanner scanner = new Scanner("token1 token2");
        List<String> collectedTokens = new ArrayList<>();
        try {
            // Simulate concurrent modification by changing modCount
            // Note: Since modCount is package-private, this test assumes no direct access.
            // As per constraints, we cannot modify internal state directly.
            // Therefore, this test is a placeholder to illustrate intent.
            // In real scenario, this would require reflection or a different approach.
            // Here, we simulate by calling next() to change internal state.
            scanner.next(); // consumes 'token1'
            // Now, calling tryAdvance should throw ConcurrentModificationException
            scanner.tryAdvance(new Consumer<String>() {
                public void accept(String s) {
                    // no-op
                }
            });
            assert false : "Expected ConcurrentModificationException to be thrown";
        } catch (ConcurrentModificationException e) {
            // Expected exception
        } finally {
            scanner.close();
        }
    }
}
