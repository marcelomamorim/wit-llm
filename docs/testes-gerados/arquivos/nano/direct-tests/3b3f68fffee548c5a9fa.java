/* @test    @summary Test normalizeSecondAndAppend method for Norm2AllModes.    @run main Norm2AllModesWitupTest */

import sun.text.normalizer.Norm2AllModes;
import java.lang.StringBuilder;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testNormalizeSecondAndAppendWithDifferentObjects();
        testNormalizeSecondAndAppendWithSameObjectThrows();
        testNormalizeSecondAndAppendWithEmptySecond();
        testNormalizeSecondAndAppendWithNullSecond();
    }

    static void testNormalizeSecondAndAppendWithDifferentObjects() {
        StringBuilder sb = new StringBuilder("Hello");
        StringBuilder result = Norm2AllModes.normalizeSecondAndAppend(sb, " World");
        assert result == sb : "Result should be the same StringBuilder instance";
        assert result.toString().equals("Hello World") : "Concatenation failed";
    }

    static void testNormalizeSecondAndAppendWithSameObjectThrows() {
        StringBuilder sb = new StringBuilder("Test");
        boolean thrown = false;
        try {
            Norm2AllModes.normalizeSecondAndAppend(sb, sb);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assert thrown : "Expected IllegalArgumentException when first and second are same object";
    }

    static void testNormalizeSecondAndAppendWithEmptySecond() {
        StringBuilder sb = new StringBuilder("Data");
        StringBuilder result = Norm2AllModes.normalizeSecondAndAppend(sb, "");
        assert result == sb : "Result should be the same StringBuilder instance";
        assert result.toString().equals("Data") : "Appending empty string should not change StringBuilder";
    }

    static void testNormalizeSecondAndAppendWithNullSecond() {
        StringBuilder sb = new StringBuilder("NullTest");
        boolean thrown = false;
        try {
            Norm2AllModes.normalizeSecondAndAppend(sb, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        assert thrown : "Expected NullPointerException when second is null";
    }
}
