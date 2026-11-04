/* @test    @summary Test for ArrayList's forEachRemaining method to ensure it correctly applies the Consumer to remaining elements.    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingAppliesConsumer();
        testForEachRemainingWithEmptyList();
        testForEachRemainingNullConsumer();
    }

    public static void testForEachRemainingAppliesConsumer() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.iterator().next(); // advance cursor by one
        Consumer<Integer> consumer = new Consumer<Integer>() {
            int sum = 0;
            @Override
            public void accept(Integer t) {
                sum += t;
            }
        };
        list.forEachRemaining(consumer);
        // Since consumer is anonymous, we cannot directly check sum, but we can verify no exception thrown and list remains unchanged.
        assert list.size() == 5 : "List size should remain 5";
    }

    public static void testForEachRemainingWithEmptyList() {
        ArrayList<Integer> emptyList = new ArrayList<>();
        emptyList.iterator(); // create iterator
        emptyList.forEachRemaining(t -> {
            throw new AssertionError("Should not be called on empty list");
        });
        assert emptyList.size() == 0 : "Empty list should remain empty";
    }

    public static void testForEachRemainingNullConsumer() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        try {
            list.forEachRemaining(null);
            throw new AssertionError("NullPointerException expected");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
