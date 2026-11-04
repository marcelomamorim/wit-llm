/* @test    @summary Test replaceAll method of ArrayList for various UnaryOperator inputs    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithIdentity();
        testReplaceAllWithIncrement();
        testReplaceAllWithNullOperator();
    }

    static void testReplaceAllWithIdentity() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.replaceAll(UnaryOperator.identity());
        assert list.get(0) == 1 : "Expected 1";
        assert list.get(1) == 2 : "Expected 2";
        assert list.get(2) == 3 : "Expected 3";
    }

    static void testReplaceAllWithIncrement() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.replaceAll(x -> x + 10);
        assert list.get(0) == 11 : "Expected 11";
        assert list.get(1) == 12 : "Expected 12";
        assert list.get(2) == 13 : "Expected 13";
    }

    static void testReplaceAllWithNullOperator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            list.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
