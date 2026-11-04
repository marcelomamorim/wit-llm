/*
 * @test
 * @summary Verifies ArrayList.get returns stored elements (including null) and rejects out-of-range indices
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(null);
        list.add("c");

        if (!"a".equals(list.get(0))) {
            throw new RuntimeException("get(0) returned unexpected value");
        }
        if (list.get(1) != null) {
            throw new RuntimeException("get(1) should return null");
        }
        if (!"c".equals(list.get(2))) {
            throw new RuntimeException("get(2) returned unexpected value");
        }

        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, 3);
        expectIndexOutOfBounds(list, Integer.MAX_VALUE);
        expectIndexOutOfBounds(new ArrayList<String>(), 0);
    }

    static void expectIndexOutOfBounds(ArrayList<String> list, int index) {
        try {
            list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("get(" + index + ") should throw IndexOutOfBoundsException");
    }
}
