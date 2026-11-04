/*
 * @test
 * @summary Verifies ArrayList.SubList.size() returns the view size and detects structural modification of the backing list
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        List<String> sub = list.subList(0, 2);
        check(sub.size() == 2, "subList size expected 2");

        list.set(1, "y");
        check(sub.size() == 2, "non-structural set must not invalidate subList size");

        list.add("d");
        boolean thrown = false;
        try {
            sub.size();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "size() after structural modification of backing list must throw ConcurrentModificationException");

        List<String> empty = new ArrayList<>().subList(0, 0);
        check(empty.size() == 0, "empty subList size expected 0");

        ArrayList<String> single = new ArrayList<>();
        single.add("x");
        List<String> whole = single.subList(0, 1);
        check(whole.size() == 1, "single-element subList size expected 1");
    }
}
