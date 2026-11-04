/*
 * @test
 * @summary replaceAll on immutable lists must throw UnsupportedOperationException and leave the contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.function.UnaryOperator;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testMultipleElementsList();
        testSingleElementList();
        testEmptyList();
        testCopyOfList();
        System.out.println("PASS");
    }

    private static void testMultipleElementsList() {
        List<Integer> list = List.of(1, 2, 3);
        expectUnsupported(list, x -> x * 2);
        check(list.equals(List.of(1, 2, 3)), "contents changed for List.of(1,2,3)");
    }

    private static void testSingleElementList() {
        List<Integer> list = List.of(7);
        expectUnsupported(list, x -> x + 1);
        check(list.equals(List.of(7)), "contents changed for List.of(7)");
    }

    private static void testEmptyList() {
        List<Integer> list = List.of();
        expectUnsupported(list, x -> x);
        check(list.isEmpty(), "empty immutable list changed");
    }

    private static void testCopyOfList() {
        List<Integer> source = new java.util.ArrayList<>();
        source.add(4);
        source.add(5);
        List<Integer> list = List.copyOf(source);
        expectUnsupported(list, x -> x - 1);
        check(list.equals(List.of(4, 5)), "contents changed for List.copyOf");
    }

    private static void expectUnsupported(List<Integer> list, UnaryOperator<Integer> op) {
        try {
            list.replaceAll(op);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("replaceAll did not throw UnsupportedOperationException for " + list.getClass().getName());
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
