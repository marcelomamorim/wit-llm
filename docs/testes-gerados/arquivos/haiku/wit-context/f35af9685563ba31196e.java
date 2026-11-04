/*
 * @test
 * @summary Witup-derived checks for Arrays.equals(char[], char[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        check(Arrays.equals((char[]) null, (char[]) null), "both null must be equal");
        check(!Arrays.equals((char[]) null, new char[] {'a'}), "null first must be unequal");
        check(!Arrays.equals(new char[] {'a'}, (char[]) null), "null second must be unequal");
        check(!Arrays.equals((char[]) null, new char[0]), "null vs empty must be unequal");

        char[] same = {'x', 'y', 'z'};
        check(Arrays.equals(same, same), "same reference must be equal");

        check(Arrays.equals(new char[0], new char[0]), "two empty arrays must be equal");
        check(Arrays.equals(new char[] {'a', 'b', 'c'}, new char[] {'a', 'b', 'c'}),
              "equal content must be equal");

        check(!Arrays.equals(new char[] {'a', 'b', 'c'}, new char[] {'a', 'b'}),
              "different length (prefix) must be unequal");
        check(!Arrays.equals(new char[] {'a', 'b'}, new char[] {'a', 'b', 'c'}),
              "different length (longer first) must be unequal");
        check(!Arrays.equals(new char[] {'a', 'b', 'c'}, new char[] {'a', 'x', 'c'}),
              "differing middle element must be unequal");
        check(!Arrays.equals(new char[] {'a'}, new char[] {'b'}),
              "differing single element must be unequal");

        char nonAscii = (char) 233;
        check(Arrays.equals(new char[] {nonAscii}, new char[] {nonAscii}),
              "non-ASCII equal content must be equal");
        check(!Arrays.equals(new char[] {nonAscii}, new char[] {'e'}),
              "non-ASCII vs ASCII must be unequal");

        check(Arrays.equals(new char[] {Character.MAX_VALUE}, new char[] {Character.MAX_VALUE}),
              "MAX_VALUE equal content must be equal");
        check(!Arrays.equals(new char[] {Character.MIN_VALUE}, new char[] {Character.MAX_VALUE}),
              "MIN vs MAX must be unequal");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
