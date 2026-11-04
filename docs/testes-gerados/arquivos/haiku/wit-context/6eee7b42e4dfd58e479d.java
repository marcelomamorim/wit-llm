/*
 * @test
 * @summary Exercises Scanner.tokens().spliterator().tryAdvance for empty input, token delivery, closed scanner and concurrent modification from the consumer
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.util.function.Consumer;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testEmptyInputReturnsFalse();
        testTokensAreDeliveredInOrder();
        testClosedScannerThrowsIllegalState();
        testConsumerModifyingScannerThrowsConcurrentModification();
    }

    static void testEmptyInputReturnsFalse() {
        Scanner sc = new Scanner("");
        Spliterator<String> sp = sc.tokens().spliterator();
        final int[] calls = {0};
        Consumer<String> c = s -> calls[0]++;
        if (sp.tryAdvance(c)) {
            throw new RuntimeException("expected false on empty input");
        }
        if (calls[0] != 0) {
            throw new RuntimeException("consumer must not be called when no token");
        }
    }

    static void testTokensAreDeliveredInOrder() {
        Scanner sc = new Scanner("alpha beta");
        Spliterator<String> sp = sc.tokens().spliterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> c = seen::add;
        if (!sp.tryAdvance(c)) {
            throw new RuntimeException("expected first token");
        }
        if (!sp.tryAdvance(c)) {
            throw new RuntimeException("expected second token");
        }
        if (sp.tryAdvance(c)) {
            throw new RuntimeException("expected false after last token");
        }
        if (!"[alpha, beta]".equals(seen.toString())) {
            throw new RuntimeException("unexpected tokens: " + seen);
        }
    }

    static void testClosedScannerThrowsIllegalState() {
        Scanner sc = new Scanner("x y");
        Spliterator<String> sp = sc.tokens().spliterator();
        sc.close();
        boolean thrown = false;
        try {
            sp.tryAdvance(s -> { });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException on closed scanner");
        }
    }

    static void testConsumerModifyingScannerThrowsConcurrentModification() {
        final Scanner sc = new Scanner("a b c");
        Spliterator<String> sp = sc.tokens().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(s -> sc.next());
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ConcurrentModificationException");
        }
    }
}
