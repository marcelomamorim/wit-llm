/*
 * @test
 * @summary Test for Collections.forEach with null Consumer
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        List<String> list = List.of("a", "b", "c");
        try {
            Collections.forEach(list, null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null action");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testForEachWithValidAction() {
        List<String> list = List.of("a", "b", "c");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        try {
            Collections.forEach(list, consumer);
            String result = sb.toString();
            if ("abc".equals(result)) {
                System.out.println("PASS: forEach applied correctly, result: " + result);
            } else {
                System.out.println("FAIL: Unexpected result: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
