/*
 * @test
 * @summary Test ReferencePipeline.filter(Predicate) for null predicate and basic filtering
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Predicate;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFilterNullPredicate();
        testFilterBasicBehavior();
    }

    static void testFilterNullPredicate() {
        try {
            ReferencePipeline<String> pipeline = new ReferencePipeline<String>(
                () -> null, 0, false) {
                @Override
                public Stream<String> filter(Predicate<? super String> predicate) {
                    return super.filter(predicate);
                }
            };
            pipeline.filter(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFilterBasicBehavior() {
        // Create a simple ReferencePipeline from a known source
        ReferencePipeline<String> pipeline = new ReferencePipeline<String>(
            new java.util.Spliterators.AbstractSpliterator<String>(10, 0) {
                int count = 0;
                final String[] data = {"a", "b", "c", "d"};
                public boolean tryAdvance(java.util.function.Consumer<? super String> action) {
                    if (count < data.length) {
                        action.accept(data[count++]);
                        return true;
                    } else {
                        return false;
                    }
                }
            }, 0, false) {
            @Override
            public Stream<String> filter(Predicate<? super String> predicate) {
                return super.filter(predicate);
            }
        };

        Stream<String> filtered = pipeline.filter(new Predicate<String>() {
            public boolean test(String s) {
                return "b".equals(s) || "d".equals(s);
            }
        });

        java.util.Iterator<String> it = filtered.iterator();
        java.util.List<String> results = new java.util.ArrayList<>();
        while (it.hasNext()) {
            results.add(it.next());
        }

        if (results.size() != 2 || !results.contains("b") || !results.contains("d")) {
            throw new AssertionError("Filter did not produce expected elements: " + results);
        }
    }

}