/* @test    @summary Test for Arrays.copyOfRange method with valid and invalid inputs    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeInvalid();
    }

    static void testCopyOfRangeValid() {
        char[] original = {'a', 'b', 'c', 'd', 'e'};
        char[] result = Arrays.copyOfRange(original, 1, 4);
        assert result.length == 3 : "Expected length 3";
        assert result[0] == 'b' : "Expected 'b'";
        assert result[1] == 'c' : "Expected 'c'";
        assert result[2] == 'd' : "Expected 'd'";
    }

    static void testCopyOfRangeInvalid() {
        char[] original = {'a', 'b', 'c'};
        try {
            Arrays.copyOfRange(original, 2, 1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
