/*
 * @test
 * @summary Tests the map method of ReferencePipeline for correct behavior.
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Function;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapWithIdentity();
        testMapWithSimpleFunction();
        testMapWithNullFunction();
    }

    static void testMapWithIdentity() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> result = stream.map(Function.identity());
        Object[] output = result.toArray();
        assert output.length == 3 : "Expected 3 elements";
        assert output[0].equals(1) : "First element should be 1";
        assert output[1].equals(2) : "Second element should be 2";
        assert output[2].equals(3) : "Third element should be 3";
    }

    static void testMapWithSimpleFunction() {
        Stream<String> stream = Stream.of("a", "bb", "ccc");
        Stream<Integer> result = stream.map(String::length);
        Object[] output = result.toArray();
        assert output.length == 3 : "Expected 3 elements";
        assert output[0].equals(1) : "First element should be 1";
        assert output[1].equals(2) : "Second element should be 2";
        assert output[2].equals(3) : "Third element should be 3";
    }

    static void testMapWithNullFunction() {
        try {
            Stream<Integer> stream = Stream.of(1, 2, 3);
            stream.map(null).toArray();
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
