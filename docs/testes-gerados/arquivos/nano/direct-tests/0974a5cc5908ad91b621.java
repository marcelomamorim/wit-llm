/* @test    @summary Test for AbstractList.subList method to ensure correct sublist behavior and exception handling.    @run main AbstractListWitupTest */

import java.util.*;

public class AbstractListWitupTest {

    public static void main(String[] args) {
        testSubListValidRange();
        testSubListInvalidRange();
    }

    static void testSubListValidRange() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        List<Integer> sublist = list.subList(1, 4);
        assert sublist.size() == 3 : "Expected sublist size 3, got " + sublist.size();
        assert sublist.get(0) == 2 : "Expected first element 2, got " + sublist.get(0);
        assert sublist.get(2) == 4 : "Expected last element 4, got " + sublist.get(2);
        sublist.set(1, 20);
        assert list.get(2) == 20 : "Modification in sublist should reflect in original list";
    }

    static void testSubListInvalidRange() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
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
            // subList allows fromIndex > toIndex, returns empty list
            List<Integer> sublist = list.subList(2, 1);
            assert sublist.isEmpty() : "Sublist with fromIndex > toIndex should be empty";
        } catch (Exception e) {
            assert false : "Did not expect exception when fromIndex > toIndex";
        }
    }
}
