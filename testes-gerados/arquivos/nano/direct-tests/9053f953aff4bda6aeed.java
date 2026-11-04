/* @test    @summary Test for Optional.flatMap method to ensure correct behavior with present and empty Optionals    @run main OptionalFlatMapTest */

import java.util.Optional;
import java.util.function.Function;

public class OptionalFlatMapTest {

    public static void main(String[] args) {
        testFlatMapWithPresentValue();
        testFlatMapWithEmptyOptional();
    }

    static void testFlatMapWithPresentValue() {
        Optional<Integer> opt = Optional.of(5);
        Function<Integer, Optional<String>> mapper = (Integer i) -> Optional.of("Number: " + i);
        Optional<String> result = opt.flatMap(mapper);
        if (!result.isPresent()) {
            throw new AssertionError("Expected present Optional");
        }
        if (!"Number: 5".equals(result.get())) {
            throw new AssertionError("Unexpected value: " + result.get());
        }
    }

    static void testFlatMapWithEmptyOptional() {
        Optional<Integer> opt = Optional.empty();
        Function<Integer, Optional<String>> mapper = (Integer i) -> Optional.of("Number: " + i);
        Optional<String> result = opt.flatMap(mapper);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional");
        }
    }
}
