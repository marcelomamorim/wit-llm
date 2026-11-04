/*
 * @test
 * @summary Public Stream behaviour reached through collection-backed pipelines, related to Nodes.forEach null-consumer handling
 * @run main NodesWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodesWitupTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));

        StringBuilder sb = new StringBuilder();
        list.stream().forEach(s -> sb.append(s));
        check(sb.toString().equals("abc"), "forEach must traverse elements in encounter order");

        Object[] arr = list.stream().toArray();
        check(Arrays.equals(arr, new Object[]{"a", "b", "c"}), "toArray must preserve elements");

        List<String> empty = new ArrayList<>();
        check(empty.stream().toArray().length == 0, "empty collection must yield empty array");

        boolean npe = false;
        try {
            list.stream().forEach(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "forEach(null) must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
