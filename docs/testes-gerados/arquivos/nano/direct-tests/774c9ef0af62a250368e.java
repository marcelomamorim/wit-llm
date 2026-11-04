/* @test    @summary Test ArrayList.subList for valid and invalid ranges    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSubListValidRange();
        testSubListInvalidRange();
    }

    static void testSubListValidRange() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        List<Integer> sub = list.subList(1, 4);
        assert sub.size() == 3 : "Expected size 3";
        assert sub.get(0) == 2 : "Expected first element 2";
        assert sub.get(2) == 4 : "Expected last element 4";
    }

    static void testSubListInvalidRange() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        try {
            list.subList(-1, 2);
            assert false : "Expected IndexOutOfBoundsException for negative fromIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            list.subList(1, 4);
            assert false : "Expected IndexOutOfBoundsException for toIndex > size";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            list.subList(2, 1);
            // According to implementation, this should throw an exception
            assert false : "Expected IllegalArgumentException for fromIndex > toIndex";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
