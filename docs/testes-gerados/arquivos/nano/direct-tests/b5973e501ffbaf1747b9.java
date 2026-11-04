/* @test    @summary Test replaceAll method in Collections for deterministic behavior    @run main CollectionsWitupTest */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithIdentity();
        testReplaceAllWithIncrement();
        testReplaceAllWithNullOperator();
    }

    static void testReplaceAllWithIdentity() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> expected = new ArrayList<>(list);
        UnaryOperator<Integer> identity = e -> e;
        Collections.replaceAll(list, identity);
        assert list.equals(expected) : "replaceAll with identity should not change the list";
    }

    static void testReplaceAllWithIncrement() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        expected.add(4);
        UnaryOperator<Integer> increment = e -> e + 1;
        Collections.replaceAll(list, increment);
        assert list.equals(expected) : "replaceAll with increment should add 1 to each element";
    }

    static void testReplaceAllWithNullOperator() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            Collections.replaceAll(null, null);
            assert false : "Expected NullPointerException when list is null";
        } catch (NullPointerException e) {
            // expected
        }
        try {
            List<Integer> list2 = new ArrayList<>();
            Collections.replaceAll(list2, null);
            assert false : "Expected NullPointerException when operator is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
