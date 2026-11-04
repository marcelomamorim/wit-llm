/*
 * @test
 * @summary Test Collections.CheckedList.forEachRemaining throws NPE on null action
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
    }

    private static void testForEachRemainingNullAction() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");

        List<String> checkedList = Collections.checkedList(list, String.class);
        Iterator<String> it = checkedList.iterator();

        // The iterator returned by checkedList.iterator() is a CheckedList.Anonymous class
        // which implements forEachRemaining(Consumer<? super E>) that should throw NPE if action is null

        try {
            it.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Also test normal usage with a non-null Consumer
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it = checkedList.iterator();
        it.forEachRemaining(consumer);

        if (!sb.toString().equals("onetwo")) {
            throw new RuntimeException("forEachRemaining did not process all elements correctly");
        }
    }
}
