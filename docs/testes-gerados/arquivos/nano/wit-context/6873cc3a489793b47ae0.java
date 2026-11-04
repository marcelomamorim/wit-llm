/*
 * @test
 * @summary Test for ReferencePipeline.map handling null mapper
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Function;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapWithNullMapper();
        testMapWithValidMapper();
    }

    static void testMapWithNullMapper() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        try {
            stream.map(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null mapper");
        }
    }

    static void testMapWithValidMapper() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> result = stream.map(i -> i * 2);
        StringBuilder sb = new StringBuilder();
        result.forEach(i -> sb.append(i).append(","));
        String output = sb.toString();
        if (output.equals("2,4,6,")) {
            System.out.println("Mapping works as expected");
        } else {
            System.err.println("Mapping did not produce expected output: " + output);
            System.exit(1);
        }
    }
}
