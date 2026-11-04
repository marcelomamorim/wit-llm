/* @test    @summary Test for CopyOnWriteArrayList forEach method behavior    @run main CopyOnWriteArrayListWitupTest */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyList();
        testForEachNullAction();
    }

    static void testForEachWithElements() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder sb = new StringBuilder();
        list.forEach(e -> sb.append(e));
        if (!"123".equals(sb.toString())) {
            throw new AssertionError("Expected '123', got '" + sb.toString() + "'");
        }
    }

    static void testForEachWithEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        StringBuilder sb = new StringBuilder();
        list.forEach(e -> sb.append(e));
        if (sb.length() != 0) {
            throw new AssertionError("Expected empty string, got '" + sb.toString() + "'");
        }
    }

    static void testForEachNullAction() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        boolean exceptionThrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null action");
        }
    }
}
