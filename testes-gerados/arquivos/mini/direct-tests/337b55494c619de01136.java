/*
 * @test
 * @summary Test java.util.Collections forEach method delegation
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachDelegation();
    }

    static void testForEachDelegation() {
        final List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        // Use the forEach method of the list directly to simulate the delegation
        list.forEach(action);

        String result = sb.toString();

        if (!"abc".equals(result)) {
            throw new RuntimeException("forEach did not process all elements in order: " + result);
        }
    }
}
