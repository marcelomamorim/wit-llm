/*
 * @test
 * @summary Checks public findFirst/findAny behavior that is implemented by FindOps sinks
 * @run main FindOpsWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.DoubleStream;

public class FindOpsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRefFindFirstNonEmpty() {
        Optional<String> r = Stream.of("a", "b", "c").findFirst();
        check(r.isPresent(), "findFirst on non-empty stream must be present");
        check("a".equals(r.get()), "findFirst must return first element in encounter order");
    }

    static void testRefFindFirstEmpty() {
        Optional<String> r = Stream.<String>empty().findFirst();
        check(r != null, "findFirst on empty stream must not return null");
        check(!r.isPresent(), "findFirst on empty stream must be empty");
    }

    static void testRefFindAnyNonEmpty() {
        List<String> items = Arrays.asList("x", "y");
        Optional<String> r = items.stream().findAny();
        check(r.isPresent(), "findAny on non-empty stream must be present");
        check(items.contains(r.get()), "findAny must return an element of the stream");
    }

    static void testRefFindAnyEmpty() {
        Optional<Object> r = Stream.empty().findAny();
        check(r != null, "findAny on empty stream must not return null");
        check(!r.isPresent(), "findAny on empty stream must be empty");
    }

    static void testRefFindFirstWithNullElementThrows() {
        boolean thrown = false;
        try {
            Stream.of((String) null).findFirst();
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "findFirst must reject null element with NullPointerException");
    }

    static void testIntFind() {
        OptionalInt first = IntStream.of(5, 6, 7).findFirst();
        check(first.isPresent() && first.getAsInt() == 5, "int findFirst must return 5");
        OptionalInt any = IntStream.of(9).findAny();
        check(any.isPresent() && any.getAsInt() == 9, "int findAny must return 9");
        check(!IntStream.empty().findFirst().isPresent(), "int findFirst on empty must be empty");
        check(!IntStream.empty().findAny().isPresent(), "int findAny on empty must be empty");
    }

    static void testLongFind() {
        OptionalLong first = LongStream.of(10L, 20L).findFirst();
        check(first.isPresent() && first.getAsLong() == 10L, "long findFirst must return 10");
        check(!LongStream.empty().findAny().isPresent(), "long findAny on empty must be empty");
    }

    static void testDoubleFind() {
        OptionalDouble first = DoubleStream.of(1.5, 2.5).findFirst();
        check(first.isPresent() && first.getAsDouble() == 1.5, "double findFirst must return 1.5");
        check(!DoubleStream.empty().findAny().isPresent(), "double findAny on empty must be empty");
    }

    public static void main(String[] args) {
        testRefFindFirstNonEmpty();
        testRefFindFirstEmpty();
        testRefFindAnyNonEmpty();
        testRefFindAnyEmpty();
        testRefFindFirstWithNullElementThrows();
        testIntFind();
        testLongFind();
        testDoubleFind();
    }
}
