/*
 * @test
 * @summary Test java.util.Collections.forEach method delegation
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
        // Create a list with known elements
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Wrap the list with Collections.checkedList to get a Collections instance with forEach
        List<String> checkedList = Collections.checkedList(list, String.class);

        final StringBuilder sb = new StringBuilder();

        // Use forEach to append elements to StringBuilder
        checkedList.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        // Verify that all elements were visited in order
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("forEach did not visit all elements in order, got: " + result);
        }
    }
}
