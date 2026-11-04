/*
 * @test
 * @summary Test tryAdvance method of java.util.Spliterators
 * @run main SpliteratorsWitupTest
 */
import java.util.*;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithCollection();
        testTryAdvanceWithNullAction();
        testTryAdvanceEmptyCollection();
    }

    static void testTryAdvanceWithCollection() {
        List<String> list = Arrays.asList("a", "b", "c");
        Spliterator<String> spliterator = list.spliterator();
        boolean result;
        List<String> collected = new ArrayList<>();
        Consumer<String> consumer = collected::add;
        while ((result = spliterator.tryAdvance(consumer))) {
            // continue until no more elements
        }
        assert collected.equals(list) : "Collected elements do not match original";
        assert result == false : "tryAdvance should return false after exhausting elements";
    }

    static void testTryAdvanceWithNullAction() {
        List<String> list = Arrays.asList("x");
        Spliterator<String> spliterator = list.spliterator();
        try {
            spliterator.tryAdvance(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testTryAdvanceEmptyCollection() {
        List<String> emptyList = Collections.emptyList();
        Spliterator<String> spliterator = emptyList.spliterator();
        boolean result = spliterator.tryAdvance(s -> {});
        assert result == false : "tryAdvance on empty collection should return false";
    }
}
