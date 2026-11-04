/*
 * @test    @summary Test for ReferencePipeline.forEach method to ensure it executes consumer on each element.
 * @run main ReferencePipelineWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testForEachExecutesConsumer();
    }

    public static void testForEachExecutesConsumer() {
        List<Integer> list = new ArrayList<>();
        Stream<Integer> stream = Stream.of(1, 2, 3);
        stream.forEach(i -> list.add(i));
        assert list.size() == 3 : "Expected list size 3, but got " + list.size();
        assert list.contains(1) : "List does not contain 1";
        assert list.contains(2) : "List does not contain 2";
        assert list.contains(3) : "List does not contain 3";
    }
}
