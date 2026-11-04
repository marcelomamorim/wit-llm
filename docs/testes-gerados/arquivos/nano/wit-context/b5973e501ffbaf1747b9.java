/*
 * @test
 * @summary Test for Collections.replaceAll to verify behavior with null operator.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullOperator();
        testReplaceAllWithValidOperator();
    }

    public static void testReplaceAllWithNullOperator() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        boolean exceptionThrown = false;
        try {
            java.util.Collections.replaceAll(list, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when operator is null");
        }
    }

    public static void testReplaceAllWithValidOperator() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        java.util.Collections.replaceAll(list, s -> s.toUpperCase());
        if (!list.get(0).equals("A") || !list.get(1).equals("B")) {
            throw new AssertionError("replaceAll did not convert strings to uppercase as expected");
        }
    }
}
