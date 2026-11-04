/*
 * @test
 * @summary Test for java.util.Collections.forEach(Consumer<? super E> action)
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            Collections.forEach(null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null action");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachWithValidAction() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        try {
            Collections.forEach(list, consumer);
            if (sb.toString().equals("ab")) {
                System.out.println("PASS: forEach applied correctly");
            } else {
                System.out.println("FAIL: Unexpected result: " + sb.toString());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
