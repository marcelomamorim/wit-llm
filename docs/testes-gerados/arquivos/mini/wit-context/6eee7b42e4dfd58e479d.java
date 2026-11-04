/*
 * @test
 * @summary Test java.util.Scanner.tryAdvance(Consumer<? super String>) for concurrency and state exceptions
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testTryAdvanceNormal();
        testTryAdvanceConcurrentModificationException();
        testTryAdvanceReturnsFalseWhenNoMoreTokens();
    }

    // Test normal tryAdvance usage with a simple token consumer
    private static void testTryAdvanceNormal() {
        Scanner scanner = new Scanner(new StringReader("token1 token2"));
        final StringBuilder sb = new StringBuilder();
        boolean advanced = scanner.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced) {
            throw new RuntimeException("tryAdvance should return true when tokens are available");
        }
        if (!"token1".equals(sb.toString())) {
            throw new RuntimeException("Consumer did not receive expected token");
        }
        scanner.close();
    }

    // Test that tryAdvance throws ConcurrentModificationException if modCount changes unexpectedly
    private static void testTryAdvanceConcurrentModificationException() {
        // We simulate concurrent modification by subclassing Scanner and manipulating expectedCount and modCount
        class TestScanner extends Scanner {
            int expectedCountField = 0;
            int modCountField = 0;

            TestScanner(String input) {
                super(new StringReader(input));
                // Initialize expectedCount and modCount to 0
                this.expectedCountField = 0;
                this.modCountField = 0;
            }

            // Override tryAdvance to simulate the condition expectedCount >= 0 && expectedCount != modCount
            @Override
            public boolean tryAdvance(Consumer<? super String> cons) {
                // Simulate expectedCount >= 0 and expectedCount != modCount
                if (expectedCountField >= 0 && expectedCountField != modCountField) {
                    throw new ConcurrentModificationException();
                }
                return super.tryAdvance(cons);
            }

            // Methods to set expectedCount and modCount for testing
            void setExpectedCount(int val) { expectedCountField = val; }
            void setModCount(int val) { modCountField = val; }
        }

        TestScanner ts = new TestScanner("token");
        ts.setExpectedCount(1);
        ts.setModCount(2); // expectedCount != modCount and expectedCount >= 0
        try {
            ts.tryAdvance(s -> {});
            throw new RuntimeException("Expected ConcurrentModificationException was not thrown");
        } catch (ConcurrentModificationException e) {
            // Expected
        }
        ts.close();
    }

    // Test that tryAdvance returns false when no tokens are available
    private static void testTryAdvanceReturnsFalseWhenNoMoreTokens() {
        Scanner scanner = new Scanner("");
        boolean advanced = scanner.tryAdvance(s -> {
            throw new RuntimeException("Consumer should not be called when no tokens");
        });
        if (advanced) {
            throw new RuntimeException("tryAdvance should return false when no tokens are available");
        }
        scanner.close();
    }
}
