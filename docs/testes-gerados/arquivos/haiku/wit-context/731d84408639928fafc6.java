/*
 * @test
 * @summary replaceAll on immutable lists must throw UnsupportedOperationException and leave contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.function.UnaryOperator;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsForSmallList();
        testReplaceAllThrowsForLargeList();
        testReplaceAllThrowsForEmptyList();
        testContentsUnchangedAfterReplaceAll();
    }

    static void testReplaceAllThrowsForSmallList() {
        List<Integer> list = List.of(1, 2);
        expectUnsupported(list, x -> x + 1);
    }

    static void testReplaceAllThrowsForLargeList() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        expectUnsupported(list, x -> x * 2);
    }

    static void testReplaceAllThrowsForEmptyList() {
        List<Integer> list = List.of();
        expectUnsupported(list, x -> x);
    }

    static void testContentsUnchangedAfterReplaceAll() {
        List<Integer> list = List.of(1, 2, 3);
        expectUnsupported(list, x -> x + 100);
        List<Integer> expected = List.of(1, 2, 3);
        if (!expected.equals(list)) {
            throw new AssertionError("list contents changed after failed replaceAll");
        }
    }

    static void expectUnsupported(List<Integer> list, UnaryOperator<Integer> op) {
        try {
            list.replaceAll(op);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException from replaceAll");
    }
}
