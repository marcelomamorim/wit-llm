/* @test    @summary Test for Arrays.asList method to ensure it returns a list view of the array.    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.List;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testAsListWithIntegerArray();
        testAsListWithStringArray();
        testAsListWithEmptyArray();
        testAsListWithNullArray();
    }

    static void testAsListWithIntegerArray() {
        Integer[] array = {1, 2, 3};
        List<Integer> list = Arrays.asList(array);
        assert list.size() == 3 : "Size should be 3";
        assert list.get(0) == 1 : "First element should be 1";
        assert list.get(1) == 2 : "Second element should be 2";
        assert list.get(2) == 3 : "Third element should be 3";
    }

    static void testAsListWithStringArray() {
        String[] array = {"a", "b", "c"};
        List<String> list = Arrays.asList(array);
        assert list.size() == 3 : "Size should be 3";
        assert list.get(0).equals("a") : "First element should be 'a'";
        assert list.get(1).equals("b") : "Second element should be 'b'";
        assert list.get(2).equals("c") : "Third element should be 'c'";
    }

    static void testAsListWithEmptyArray() {
        String[] emptyArray = {};
        List<String> list = Arrays.asList(emptyArray);
        assert list.size() == 0 : "Size should be 0";
    }

    static void testAsListWithNullArray() {
        try {
            List<Object> list = Arrays.asList((Object[]) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
