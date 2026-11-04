/* @test    @summary Test for CopyOnWriteArrayList forEach method to ensure it applies the action to all elements.    @run main CopyOnWriteArrayListWitupTest */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachAppliesActionToAllElements();
    }

    public static void testForEachAppliesActionToAllElements() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder result = new StringBuilder();
        list.forEach(e -> result.append(e));
        if (!"123".equals(result.toString())) {
            throw new AssertionError("forEach did not apply action to all elements: " + result);
        }
    }
}
