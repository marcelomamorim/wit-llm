/* @test    @summary Test for Collections.forEach to verify NullPointerException when action is null    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    static void testForEachWithNullAction() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            Collections.forEach(list, null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testForEachWithValidAction() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        StringBuilder sb = new StringBuilder();
        java.util.function.Consumer<String> consumer = s -> sb.append(s);
        Collections.forEach(list, consumer);
        if (!"ab".equals(sb.toString())) {
            System.err.println("forEach did not process list correctly");
            System.exit(1);
        }
    }
}
