/*
 * @test
 * @summary Tests for CopyOnWriteArrayList methods, focusing on forEach behavior.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    public static void testForEachWithNullAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        boolean exceptionThrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    public static void testForEachWithValidAction() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        list.forEach(consumer);
        String result = sb.toString();
        if (!"123".equals(result)) {
            throw new AssertionError("Unexpected forEach result: " + result);
        }
    }
}
