/* @test    @summary Test for Collections.forEachRemaining method to ensure it executes the consumer on all remaining elements.    @run main CollectionsWitupTest */

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingExecutesConsumer();
        testForEachRemainingWithEmptyIterator();
    }

    private static void testForEachRemainingExecutesConsumer() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        List<Integer> result = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        java.util.Collections.forEachRemaining(iterator, new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                result.add(t);
            }
        });
        assert result.size() == 5 : "Expected 5 elements, got " + result.size();
        for (int i = 1; i <= 5; i++) {
            assert result.contains(i) : "Result does not contain " + i;
        }
    }

    private static void testForEachRemainingWithEmptyIterator() {
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        List<Integer> result = new ArrayList<>();
        java.util.Collections.forEachRemaining(iterator, new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                result.add(t);
            }
        });
        assert result.isEmpty() : "Result should be empty for empty iterator";
    }
}
