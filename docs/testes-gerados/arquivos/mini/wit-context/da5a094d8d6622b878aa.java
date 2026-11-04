/*
 * @test
 * @summary Test ReferencePipeline.forEachOrdered behavior including IllegalStateException on consumed pipeline
 * @run main ReferencePipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.ReferencePipeline;
import java.util.stream.StreamSupport;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedNormal();
        testForEachOrderedIllegalStateException();
    }

    private static void testForEachOrderedNormal() {
        // Create a simple ReferencePipeline from a Spliterator over a list
        final List<String> data = new ArrayList<>();
        data.add("a");
        data.add("b");
        data.add("c");

        Spliterator<String> spliterator = data.spliterator();

        // Create ReferencePipeline instance via StreamSupport.stream (which returns a ReferencePipeline)
        ReferencePipeline<String, String> pipeline = (ReferencePipeline<String, String>) StreamSupport.stream(spliterator, false);

        final List<String> result = new ArrayList<>();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                result.add(s);
            }
        };

        pipeline.forEachOrdered(action);

        if (result.size() != data.size()) {
            throw new AssertionError("forEachOrdered did not process all elements");
        }
        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).equals(result.get(i))) {
                throw new AssertionError("forEachOrdered processed elements out of order");
            }
        }
    }

    private static void testForEachOrderedIllegalStateException() {
        // Create a simple ReferencePipeline from a Spliterator over a list
        final List<String> data = new ArrayList<>();
        data.add("x");
        data.add("y");

        Spliterator<String> spliterator = data.spliterator();

        ReferencePipeline<String, String> pipeline = (ReferencePipeline<String, String>) StreamSupport.stream(spliterator, false);

        final List<String> result = new ArrayList<>();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                result.add(s);
            }
        };

        // Consume the pipeline once
        pipeline.forEachOrdered(action);

        // Second consumption should throw IllegalStateException
        boolean threw = false;
        try {
            pipeline.forEachOrdered(action);
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second forEachOrdered call");
        }
    }
}
