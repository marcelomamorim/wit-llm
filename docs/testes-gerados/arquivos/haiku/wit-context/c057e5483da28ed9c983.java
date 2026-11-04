/*
 * @test
 * @summary WITUP-derived checks for ArrayList.SubList.size() comodification detection and range size
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSubListSizeReflectsRange();
        testSubListSizeThrowsAfterRootStructuralChange();
        testSubListSizeUnaffectedByRootSet();
    }

    static void testSubListSizeReflectsRange() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        List<Integer> sub = list.subList(1, 4);
        check(sub.size() == 3, "subList(1,4).size() expected 3 but was " + sub.size());
    }

    static void testSubListSizeThrowsAfterRootStructuralChange() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        List<Integer> sub = list.subList(0, 2);
        list.add(6);
        boolean thrown = false;
        try {
            sub.size();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "SubList.size() must throw ConcurrentModificationException after root structural change");
    }

    static void testSubListSizeUnaffectedByRootSet() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        List<Integer> sub = list.subList(0, 2);
        list.set(0, 9);
        check(sub.size() == 2, "set is not structural; subList size expected 2 but was " + sub.size());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
