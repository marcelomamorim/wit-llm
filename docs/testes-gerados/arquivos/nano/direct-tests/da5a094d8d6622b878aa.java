/*
 * @test
 * @summary Test for ReferencePipeline's forEachOrdered method.
 * @run main ReferencePipelineWitupTest
 */
import java.util.Arrays;
import java.util.List;
import java.util.stream.ReferencePipeline;
import java.util.function.Consumer;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedWithElements();
        testForEachOrderedWithEmpty();
    }

    static void testForEachOrderedWithElements() {
        List<String> list = Arrays.asList("a", "b", "c");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = sb::append;
        ReferencePipeline<String, String> stream = list.stream().collect(
            () -> ReferencePipeline.fromSpliterator(list.spliterator(), false),
            (sp, item) -> sp.forEachOrdered(consumer),
            (sp1, sp2) -> {} // no-op combiner for simplicity
        );
        stream.forEachOrdered(consumer);
        assert sb.toString().equals("abc") : "Expected 'abc', got '" + sb.toString() + "'";
    }

    static void testForEachOrderedWithEmpty() {
        List<String> emptyList = Arrays.asList();
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = sb::append;
        ReferencePipeline<String, String> stream = emptyList.stream().collect(
            () -> ReferencePipeline.fromSpliterator(emptyList.spliterator(), false),
            (sp, item) -> sp.forEachOrdered(consumer),
            (sp1, sp2) -> {} // no-op
        );
        stream.forEachOrdered(consumer);
        assert sb.toString().isEmpty() : "Expected empty string, got '" + sb.toString() + "'";
    }
}
