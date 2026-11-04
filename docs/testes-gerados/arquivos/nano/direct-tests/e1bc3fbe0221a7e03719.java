/* @test    @summary Test StreamSupport.stream method with various spliterators    @run main StreamSupportWitupTest */

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Arrays;
import java.util.List;

public class StreamSupportWitupTest {

    public static void main(String[] args) {
        testStreamWithArraySpliterator();
        testStreamWithEmptySpliterator();
        testStreamWithNullSpliterator();
        testStreamWithNonSplittable();
    }

    static void testStreamWithArraySpliterator() {
        String[] array = {"a", "b", "c"};
        Spliterator<String> spliterator = Arrays.spliterator(array);
        Stream<String> stream = StreamSupport.stream(spliterator, false);
        List<String> result = stream.toList();
        assert result.size() == 3 : "Expected size 3";
        assert result.containsAll(Arrays.asList("a", "b", "c")) : "Missing elements";
    }

    static void testStreamWithEmptySpliterator() {
        List<Integer> emptyList = List.of();
        Spliterator<Integer> spliterator = emptyList.spliterator();
        Stream<Integer> stream = StreamSupport.stream(spliterator, true);
        List<Integer> result = stream.toList();
        assert result.isEmpty() : "Expected empty list";
    }

    static void testStreamWithNullSpliterator() {
        try {
            StreamSupport.stream(null, false);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testStreamWithNonSplittable() {
        // Create a custom spliterator that does not support splitting
        Spliterator<String> nonSplittable = new Spliterator<String>() {
            private int index = 0;
            private final String[] data = {"x", "y"};
            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super String> action) {
                if (index < data.length) {
                    action.accept(data[index++]);
                    return true;
                }
                return false;
            }
            @Override
            public Spliterator<String> trySplit() {
                return null; // non-splittable
            }
            @Override
            public long estimateSize() {
                return data.length - index;
            }
            @Override
            public int characteristics() {
                return 0;
            }
        };
        Stream<String> stream = StreamSupport.stream(nonSplittable, false);
        List<String> result = stream.toList();
        assert result.containsAll(List.of("x", "y")) : "Missing elements";
        assert result.size() == 2 : "Expected size 2";
    }
}
