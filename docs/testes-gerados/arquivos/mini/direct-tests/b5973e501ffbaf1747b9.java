/*
 * @test
 * @summary Test java.util.Collections replaceAll method wrapper behavior
 * @run main CollectionsWitupTest
 */

import java.util.*;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllWithValidOperator();
        testReplaceAllWithNullOperator();
    }

    static void testReplaceAllWithValidOperator() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Wrap list with Collections checkedList to get replaceAll method
        List<String> checkedList = Collections.checkedList(list, String.class);

        // Replace all elements with upper case version
        checkedList.replaceAll(new UnaryOperator<String>() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        });

        // Verify all elements replaced correctly
        if (!"A".equals(checkedList.get(0))) throw new RuntimeException("Element 0 not replaced correctly");
        if (!"B".equals(checkedList.get(1))) throw new RuntimeException("Element 1 not replaced correctly");
        if (!"C".equals(checkedList.get(2))) throw new RuntimeException("Element 2 not replaced correctly");
    }

    static void testReplaceAllWithNullOperator() {
        List<String> list = new ArrayList<>();
        list.add("x");

        List<String> checkedList = Collections.checkedList(list, String.class);

        try {
            checkedList.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
