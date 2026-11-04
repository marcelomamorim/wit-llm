/*
 * @test    @summary Test for CopyOnWriteArrayList.forEach method
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    static void testForEachWithNullAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.forEach(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    static void testForEachWithValidAction() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = e -> sb.append(e);
        list.forEach(consumer);
        String result = sb.toString();
        if ("12".equals(result)) {
            System.out.println("Passed: forEach applied correctly");
        } else {
            System.err.println("Failed: Unexpected result: " + result);
        }
    }
}
