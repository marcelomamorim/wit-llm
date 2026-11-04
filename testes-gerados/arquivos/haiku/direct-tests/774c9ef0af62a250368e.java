/*
 * @test
 * @summary Verifies ArrayList.subList range checks and view semantics
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        List<String> sub = list.subList(1, 3);
        check(sub.size() == 2, "subList(1,3) size should be 2");
        check("b".equals(sub.get(0)), "subList(1,3).get(0) should be b");
        check("c".equals(sub.get(1)), "subList(1,3).get(1) should be c");

        List<String> emptyAtStart = list.subList(0, 0);
        check(emptyAtStart.isEmpty(), "subList(0,0) should be empty");

        List<String> emptyAtEnd = list.subList(3, 3);
        check(emptyAtEnd.isEmpty(), "subList(3,3) should be empty");

        sub.set(0, "B");
        check("B".equals(list.get(1)), "write through subList view should update backing list");

        expectFailure(list, -1, 2, IndexOutOfBoundsException.class);
        expectFailure(list, 0, 4, IndexOutOfBoundsException.class);
        expectFailure(list, 4, 4, IndexOutOfBoundsException.class);
        expectFailure(list, 2, 1, IllegalArgumentException.class);
    }

    private static void expectFailure(ArrayList<String> list, int from, int to,
                                      Class<? extends Exception> expected) {
        try {
            list.subList(from, to);
        } catch (Exception e) {
            if (expected.isInstance(e)) {
                return;
            }
            throw new RuntimeException("subList(" + from + "," + to + ") threw unexpected " + e, e);
        }
        throw new RuntimeException("subList(" + from + "," + to + ") should have thrown " + expected.getName());
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
