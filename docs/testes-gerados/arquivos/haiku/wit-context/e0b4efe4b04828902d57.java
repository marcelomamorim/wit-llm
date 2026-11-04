/*
 * @test
 * @summary Verifies LinkedList.get(int) returns elements by index and throws IndexOutOfBoundsException for invalid indices
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testValidIndices();
        testOutOfBoundsIndices();
        testEmptyList();
    }

    static void testValidIndices() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        check("a".equals(list.get(0)), "get(0)");
        check("b".equals(list.get(1)), "get(1)");
        check("c".equals(list.get(2)), "get(2)");
    }

    static void testOutOfBoundsIndices() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        expectIOOBE(list, -1);
        expectIOOBE(list, 2);
        expectIOOBE(list, Integer.MAX_VALUE);
        expectIOOBE(list, Integer.MIN_VALUE);
    }

    static void testEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        expectIOOBE(list, 0);
    }

    static void expectIOOBE(LinkedList<String> list, int index) {
        try {
            list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
