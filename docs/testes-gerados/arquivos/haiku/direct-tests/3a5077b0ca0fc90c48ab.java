/*
 * @test
 * @summary ArrayList get behavior through subList views: index checks and comodification detection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        List<String> sub = list.subList(1, 4);

        if (!"b".equals(sub.get(0))) {
            throw new RuntimeException("sub.get(0) expected b");
        }
        if (!"d".equals(sub.get(2))) {
            throw new RuntimeException("sub.get(2) expected d");
        }
        if (!"a".equals(list.get(0))) {
            throw new RuntimeException("list.get(0) expected a");
        }

        expectIndexOutOfBounds(sub, 3);
        expectIndexOutOfBounds(sub, -1);
        expectIndexOutOfBounds(list, 4);
        expectIndexOutOfBounds(list, -1);

        list.set(1, "x");
        if (!"x".equals(sub.get(0))) {
            throw new RuntimeException("set must be visible through subList without comodification");
        }

        list.add("e");
        expectComodification(sub, 0);
    }

    private static void expectIndexOutOfBounds(List<String> l, int index) {
        try {
            l.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }

    private static void expectComodification(List<String> l, int index) {
        try {
            l.get(index);
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("expected ConcurrentModificationException for index " + index);
    }
}
