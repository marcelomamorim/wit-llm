/*
 * @test
 * @summary Verifies replaceAll through public ArrayList and subList views
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSubListReplacesOnlyRange();
        testEmptySubListNoChange();
        testFullSubListReplacesAll();
        testNestedSubListReplacesOnlyInnerRange();
        testListLevelReplaceAll();
    }

    static void testSubListReplacesOnlyRange() {
        ArrayList<Integer> list = newList(1, 2, 3, 4, 5);
        List<Integer> sub = list.subList(1, 4);
        sub.replaceAll(x -> x * 10);
        check("[1, 20, 30, 40, 5]".equals(list.toString()), "range replace: " + list);
        check("[20, 30, 40]".equals(sub.toString()), "sublist view: " + sub);
    }

    static void testEmptySubListNoChange() {
        ArrayList<Integer> list = newList(1, 2, 3, 4, 5);
        list.subList(2, 2).replaceAll(x -> x + 100);
        check("[1, 2, 3, 4, 5]".equals(list.toString()), "empty range changed list: " + list);
    }

    static void testFullSubListReplacesAll() {
        ArrayList<Integer> list = newList(1, 2, 3, 4, 5);
        list.subList(0, 5).replaceAll(x -> x + 1);
        check("[2, 3, 4, 5, 6]".equals(list.toString()), "full range: " + list);
    }

    static void testNestedSubListReplacesOnlyInnerRange() {
        ArrayList<Integer> list = newList(1, 2, 3, 4, 5);
        list.subList(1, 5).subList(1, 3).replaceAll(x -> -x);
        check("[1, 2, -3, -4, 5]".equals(list.toString()), "nested range: " + list);
    }

    static void testListLevelReplaceAll() {
        ArrayList<Integer> list = newList(1, 2, 3);
        UnaryOperator<Integer> op = x -> x * x;
        list.replaceAll(op);
        check("[1, 4, 9]".equals(list.toString()), "list replaceAll: " + list);
    }

    static ArrayList<Integer> newList(Integer... values) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer v : values) {
            list.add(v);
        }
        return list;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
