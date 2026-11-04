/*
 * @test
 * @summary Test Collections.UnmodifiableCollection.forEach method for null action and normal behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormal();
        System.out.println("All tests passed.");
    }

    static void testForEachNullAction() {
        List<String> list = new ArrayList<>();
        list.add("a");
        List<String> unmodifiable = Collections.unmodifiableList(list);

        try {
            unmodifiable.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachNormal() {
        List<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        List<String> unmodifiable = Collections.unmodifiableList(list);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        unmodifiable.forEach(action);

        String result = sb.toString();
        if (!"xy".equals(result)) {
            throw new RuntimeException("forEach did not process elements correctly: " + result);
        }
    }
}
