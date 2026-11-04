/*
 * @test
 * @summary Basic behavior of Scanner token spliterator tryAdvance via public tokens() API
 * @run main ScannerWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Scanner;
import java.util.Spliterator;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testConsumesTokensInOrder();
        testEmptyInputReturnsFalse();
        testConcurrentModificationFromConsumer();
    }

    static Spliterator<String> spliterator(Scanner sc) {
        return sc.tokens().spliterator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testConsumesTokensInOrder() {
        Scanner sc = new Scanner("alpha beta");
        Spliterator<String> s = spliterator(sc);
        List<String> out = new ArrayList<>();
        check(s.tryAdvance(out::add), "first tryAdvance should return true");
        check(s.tryAdvance(out::add), "second tryAdvance should return true");
        check(!s.tryAdvance(out::add), "third tryAdvance should return false");
        check(out.equals(Arrays.asList("alpha", "beta")),
              "unexpected tokens: " + out);
    }

    static void testEmptyInputReturnsFalse() {
        Scanner sc = new Scanner("   ");
        Spliterator<String> s = spliterator(sc);
        List<String> out = new ArrayList<>();
        check(!s.tryAdvance(out::add), "tryAdvance on whitespace-only input should return false");
        check(out.isEmpty(), "consumer must not be called when no token exists");
    }

    static void testConcurrentModificationFromConsumer() {
        Scanner sc = new Scanner("x y");
        Spliterator<String> s = spliterator(sc);
        boolean thrown = false;
        try {
            s.tryAdvance(tok -> sc.useDelimiter(","));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException when scanner is modified in consumer");
    }
}
