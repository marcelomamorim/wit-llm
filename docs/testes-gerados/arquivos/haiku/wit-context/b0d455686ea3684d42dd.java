/*
 * @test
 * @summary Witup generated tests for Comparator.comparingLong
 * @run main ComparatorWitupTest
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorWitupTest {

    public static void main(String[] args) {
        testNullKeyExtractorThrowsNPE();
        testOrdersByLongKey();
        testEqualKeysCompareZero();
        testResultIsSerializable();
        testExtremeLongValues();
    }

    static void testNullKeyExtractorThrowsNPE() {
        try {
            Comparator.comparingLong(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOrdersByLongKey() {
        Comparator<String> byLength = Comparator.comparingLong(String::length);
        List<String> list = new ArrayList<>(Arrays.asList("ccc", "a", "bb"));
        Collections.sort(list, byLength);
        if (!list.equals(Arrays.asList("a", "bb", "ccc"))) {
            throw new AssertionError("unexpected order: " + list);
        }
        if (byLength.compare("a", "bb") >= 0) {
            throw new AssertionError("a should be before bb");
        }
        if (byLength.compare("ccc", "bb") <= 0) {
            throw new AssertionError("ccc should be after bb");
        }
    }

    static void testEqualKeysCompareZero() {
        Comparator<String> byLength = Comparator.comparingLong(String::length);
        if (byLength.compare("xy", "ab") != 0) {
            throw new AssertionError("equal keys should compare to zero");
        }
    }

    static void testResultIsSerializable() {
        Comparator<String> c = Comparator.comparingLong(String::length);
        if (!(c instanceof Serializable)) {
            throw new AssertionError("result should be Serializable");
        }
    }

    static void testExtremeLongValues() {
        Comparator<Long> byValue = Comparator.comparingLong(Long::longValue);
        if (byValue.compare(Long.MIN_VALUE, Long.MAX_VALUE) >= 0) {
            throw new AssertionError("MIN_VALUE should be less than MAX_VALUE");
        }
        if (byValue.compare(Long.MAX_VALUE, Long.MIN_VALUE) <= 0) {
            throw new AssertionError("MAX_VALUE should be greater than MIN_VALUE");
        }
        if (byValue.compare(Long.valueOf(7L), Long.valueOf(7L)) != 0) {
            throw new AssertionError("equal values should compare to zero");
        }
    }
}
