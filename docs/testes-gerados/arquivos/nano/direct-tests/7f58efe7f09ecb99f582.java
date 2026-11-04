/*
 * @test    @summary Test the peek method of ReferencePipeline for correct behavior
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.*;
import java.util.*;
import java.util.function.*;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testPeekWithAction();
        testPeekWithNullAction();
    }

    static void testPeekWithAction() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> result = new ArrayList<>();
        Stream<Integer> stream = list.stream().peek(i -> result.add(i));
        List<Integer> output = stream.toList();
        if (!output.equals(list)) {
            throw new AssertionError("Stream output does not match input list");
        }
        if (!result.equals(list)) {
            throw new AssertionError("peek action did not add all elements to result list");
        }
    }

    static void testPeekWithNullAction() {
        boolean exceptionThrown = false;
        try {
            Stream<Integer> stream = Stream.of(1, 2, 3);
            stream.peek(null).toArray();
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to peek");
        }
    }
}
