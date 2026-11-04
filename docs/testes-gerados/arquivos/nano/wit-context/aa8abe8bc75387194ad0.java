/*
 * @test
 * @summary Test for java.util.Optional.map method to verify behavior with null mapper and non-empty Optional
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Function;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testMapWithNullMapper();
        testMapWithEmptyOptional();
        testMapWithNonEmptyOptional();
    }

    private static void testMapWithNullMapper() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.map(null);
            System.out.println("FAIL: Expected NullPointerException for null mapper");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null mapper");
        }
    }

    private static void testMapWithEmptyOptional() {
        Optional<String> emptyOpt = Optional.empty();
        Optional<?> result = emptyOpt.map(s -> s.length());
        if (result.isPresent()) {
            System.out.println("FAIL: Expected empty Optional for empty input");
        } else {
            System.out.println("PASS: Empty Optional returned for empty input");
        }
    }

    private static void testMapWithNonEmptyOptional() {
        Optional<String> opt = Optional.of("abc");
        Optional<Integer> result = opt.map(s -> s.length());
        if (result.isPresent() && result.get() == 3) {
            System.out.println("PASS: Correct mapping from String to its length");
        } else {
            System.out.println("FAIL: Unexpected result for non-empty Optional");
        }
    }
}
