/*
 * @test
 * @summary Tests Comparator.comparingLong ordering, equal keys, extreme long values, null handling and Serializable result
 * @run main ComparatorWitupTest
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToLongFunction;

public class ComparatorWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        ToLongFunction<String> len = s -> (long) s.length();
        Comparator<String> byLength = Comparator.comparingLong(len);

        check(byLength.compare("a", "abc") < 0, "shorter key should compare less");
        check(byLength.compare("abc", "a") > 0, "longer key should compare greater");
        check(byLength.compare("xy", "ab") == 0, "equal keys should compare as 0");
        check(byLength.compare("", "") == 0, "empty strings should compare as 0");

        Comparator<String> byValue = Comparator.comparingLong(s -> Long.parseLong(s));
        check(byValue.compare("-9223372036854775808", "9223372036854775807") < 0,
              "Long.MIN_VALUE key should be less than Long.MAX_VALUE key");
        check(byValue.compare("9223372036854775807", "-9223372036854775808") > 0,
              "Long.MAX_VALUE key should be greater than Long.MIN_VALUE key");
        check(byValue.compare("5", "5") == 0, "identical long keys should compare as 0");

        String[] arr = {"ccc", "a", "bb"};
        Arrays.sort(arr, byLength);
        check(Arrays.equals(arr, new String[] {"a", "bb", "ccc"}),
              "sorting by comparingLong should order by key");

        check(byLength instanceof Serializable, "result should be Serializable");

        try {
            Comparator.comparingLong((ToLongFunction<Object>) null);
            throw new RuntimeException("expected NullPointerException for null keyExtractor");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
