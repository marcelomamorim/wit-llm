/*
 * @test
 * @summary Tests for java.util.Optional methods, focusing on flatMap behavior.
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Function;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNullMapper();
        testFlatMapWithEmptyOptional();
        testFlatMapWithNonEmptyOptional();
    }

    private static void testFlatMapWithNullMapper() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.flatMap(null);
            System.out.println("FAIL: Expected NullPointerException for null mapper");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null mapper");
        }
    }

    private static void testFlatMapWithEmptyOptional() {
        Optional<String> emptyOpt = Optional.empty();
        Optional<Integer> result = emptyOpt.flatMap(s -> Optional.of(s.length()));
        if (!result.isPresent()) {
            System.out.println("PASS: flatMap on empty Optional returns empty");
        } else {
            System.out.println("FAIL: flatMap on empty Optional did not return empty");
        }
    }

    private static void testFlatMapWithNonEmptyOptional() {
        Optional<String> opt = Optional.of("hello");
        Optional<Integer> result = opt.flatMap(s -> Optional.of(s.length()));
        if (result.isPresent() && result.get() == 5) {
            System.out.println("PASS: flatMap on non-empty Optional returns correct value");
        } else {
            System.out.println("FAIL: flatMap on non-empty Optional returned incorrect result");
        }
    }
}
