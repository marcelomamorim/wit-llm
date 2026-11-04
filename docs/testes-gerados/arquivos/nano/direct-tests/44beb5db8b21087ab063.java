/* @test    @summary Test java.util.Arrays.fill for char[] with valid indices    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillValidRange();
        testFillFullArray();
        testFillEmptyRange();
        testFillSingleElement();
    }

    static void testFillValidRange() {
        char[] array = {'a', 'b', 'c', 'd', 'e'};
        Arrays.fill(array, 1, 4, 'x');
        assert array[0] == 'a' : "First element should remain 'a'";
        assert array[1] == 'x' : "Second element should be 'x'";
        assert array[2] == 'x' : "Third element should be 'x'";
        assert array[3] == 'x' : "Fourth element should be 'x'";
        assert array[4] == 'e' : "Fifth element should remain 'e'";
    }

    static void testFillFullArray() {
        char[] array = {'a', 'b', 'c'};
        Arrays.fill(array, 0, array.length, 'z');
        for (char c : array) {
            assert c == 'z' : "All elements should be 'z'";
        }
    }

    static void testFillEmptyRange() {
        char[] array = {'a', 'b', 'c'};
        Arrays.fill(array, 2, 2, 'y'); // should do nothing
        assert array[0] == 'a' : "First element should remain 'a'";
        assert array[1] == 'b' : "Second element should remain 'b'";
        assert array[2] == 'c' : "Third element should remain 'c'";
    }

    static void testFillSingleElement() {
        char[] array = {'a', 'b', 'c'};
        Arrays.fill(array, 1, 2, 'm');
        assert array[0] == 'a' : "First element should remain 'a'";
        assert array[1] == 'm' : "Second element should be 'm'";
        assert array[2] == 'c' : "Third element should remain 'c'";
    }
}
