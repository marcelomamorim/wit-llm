/*
 * @test
 * @summary Test for Optional.map method to ensure correct behavior.
 * @run main OptionalMapTest
 */

import java.util.Optional;
import java.util.function.Function;

public class OptionalMapTest {

    public static void main(String[] args) {
        testMapWithNonNullValue();
        testMapWithEmptyOptional();
        testMapWithNullResult();
    }

    static void testMapWithNonNullValue() {
        Optional<String> opt = Optional.of("test");
        Optional<Integer> result = opt.map(String::length);
        assert result.isPresent() : "Result should be present";
        assert result.get() == 4 : "Length should be 4";
    }

    static void testMapWithEmptyOptional() {
        Optional<String> opt = Optional.empty();
        Optional<Integer> result = opt.map(String::length);
        assert !result.isPresent() : "Result should be empty";
    }

    static void testMapWithNullResult() {
        Optional<String> opt = Optional.of("nulltest");
        Optional<Object> result = opt.map(s -> null);
        assert !result.isPresent() : "Result should be empty when mapper returns null";
    }
}
