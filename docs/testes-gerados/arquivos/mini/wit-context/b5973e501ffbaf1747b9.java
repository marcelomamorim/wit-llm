/*
 * @test
 * @summary Test Collections.CheckedList.replaceAll(UnaryOperator) for null operator and normal operation
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullOperator();
        testReplaceAllNormal();
    }

    static void testReplaceAllNullOperator() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> checkedList = Collections.checkedList(list, String.class);
        try {
            checkedList.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceAllNormal() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> checkedList = Collections.checkedList(list, String.class);
        UnaryOperator<String> operator = new UnaryOperator<String>() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        };
        checkedList.replaceAll(operator);
        if (!"A".equals(checkedList.get(0)) || !"B".equals(checkedList.get(1))) {
            throw new RuntimeException("replaceAll did not correctly replace elements");
        }
    }
}
