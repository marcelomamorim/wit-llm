/*
 * @test
 * @summary Tests ArrayList.replaceAll and SubList.replaceAll behavior, including NullPointerException for a null operator
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testReplaceAllTransformsElements();
        testReplaceAllNullOperatorOnNonEmptyList();
        testReplaceAllNullOperatorOnEmptyList();
        testSubListReplaceAllTransformsRangeOnly();
        testSubListReplaceAllNullOperator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNPE(Runnable action, String message) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message + ": expected NullPointerException");
    }

    static void testReplaceAllTransformsElements() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        UnaryOperator<Integer> doubler = x -> x * 2;
        list.replaceAll(doubler);
        check(list.size() == 3, "size changed after replaceAll");
        check(list.get(0) == 2, "element 0 not replaced");
        check(list.get(1) == 4, "element 1 not replaced");
        check(list.get(2) == 6, "element 2 not replaced");
    }

    static void testReplaceAllNullOperatorOnNonEmptyList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        final UnaryOperator<Integer> nullOp = null;
        expectNPE(() -> list.replaceAll(nullOp), "replaceAll(null) on non-empty list");
        check(list.size() == 2, "size changed after failed replaceAll");
        check(list.get(0) == 1 && list.get(1) == 2, "elements changed after failed replaceAll");
    }

    static void testReplaceAllNullOperatorOnEmptyList() {
        List<Integer> list = new ArrayList<>();
        final UnaryOperator<Integer> nullOp = null;
        expectNPE(() -> list.replaceAll(nullOp), "replaceAll(null) on empty list");
        check(list.isEmpty(), "empty list changed after failed replaceAll");
    }

    static void testSubListReplaceAllTransformsRangeOnly() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        List<Integer> sub = list.subList(1, 4);
        UnaryOperator<Integer> times10 = x -> x * 10;
        sub.replaceAll(times10);
        check(list.size() == 5, "parent size changed");
        check(list.get(0) == 1, "element before range changed");
        check(list.get(1) == 20, "range element 1 not replaced");
        check(list.get(2) == 30, "range element 2 not replaced");
        check(list.get(3) == 40, "range element 3 not replaced");
        check(list.get(4) == 5, "element after range changed");
    }

    static void testSubListReplaceAllNullOperator() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        List<Integer> sub = list.subList(1, 4);
        final UnaryOperator<Integer> nullOp = null;
        expectNPE(() -> sub.replaceAll(nullOp), "SubList.replaceAll(null)");
        check(list.get(0) == 1 && list.get(1) == 2 && list.get(2) == 3
                && list.get(3) == 4 && list.get(4) == 5,
                "list changed after failed SubList.replaceAll");
    }
}
