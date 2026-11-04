/*
 * @test
 * @summary Test String.valueOf(char[], int, int) for various inputs
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValueOfWithValidInputs();
        testValueOfWithEdgeCases();
    }

    static void testValueOfWithValidInputs() {
        char[] data = {'a', 'b', 'c', 'd', 'e'};
        String result = String.valueOf(data, 1, 3);
        if (!"bcd".equals(result)) {
            throw new AssertionError("Expected 'bcd', but got: " + result);
        }
    }

    static void testValueOfWithEdgeCases() {
        char[] empty = {};
        String emptyStr = String.valueOf(empty, 0, 0);
        if (!"".equals(emptyStr)) {
            throw new AssertionError("Expected empty string, but got: " + emptyStr);
        }
        char[] singleChar = {'x'};
        String single = String.valueOf(singleChar, 0, 1);
        if (!"x".equals(single)) {
            throw new AssertionError("Expected 'x', but got: " + single);
        }
    }
}
