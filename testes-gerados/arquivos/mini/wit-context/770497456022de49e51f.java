/*
 * @test
 * @summary Test CopyOnWriteArrayList.forEach method for null action and normal iteration
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.Objects;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormal();
    }

    private static void testForEachNullAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        boolean npeThrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachNormal() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});
        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        list.forEach(action);
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new AssertionError("forEach did not iterate correctly, got: " + result);
        }
    }
}
