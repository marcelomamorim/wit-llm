/*
 * @test
 * @summary Verifies TimeZoneNamesBundle.handleGetObject inserts the key into array values and passes other values through
 * @modules java.base/sun.util.resources
 * @run main TimeZoneNamesBundleWitupTest
 */

import sun.util.resources.TimeZoneNamesBundle;

public class TimeZoneNamesBundleWitupTest {

    static class TestBundle extends TimeZoneNamesBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][] {
                {"America/Test", new String[] {"LS", "S", "LD", "SD", "LG", "SG"}},
                {"Plain", "value"},
                {"Empty", new String[0]}
            };
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        TestBundle bundle = new TestBundle();

        Object o = bundle.handleGetObject("America/Test");
        check(o instanceof String[], "array value expected");
        String[] arr = (String[]) o;
        check(arr.length == 7, "expected length 7 but was " + arr.length);
        check("America/Test".equals(arr[0]), "key must be inserted at index 0");
        check("LS".equals(arr[1]), "index 1 mismatch");
        check("S".equals(arr[2]), "index 2 mismatch");
        check("LD".equals(arr[3]), "index 3 mismatch");
        check("SD".equals(arr[4]), "index 4 mismatch");
        check("LG".equals(arr[5]), "index 5 mismatch");
        check("SG".equals(arr[6]), "index 6 mismatch");

        Object again = bundle.handleGetObject("America/Test");
        check(again != o, "a new array should be returned on each call");
        check("America/Test".equals(((String[]) again)[0]), "key missing on second call");

        Object plain = bundle.handleGetObject("Plain");
        check("value".equals(plain), "non-array value must be returned unchanged");

        Object missing = bundle.handleGetObject("NoSuchKey");
        check(missing == null, "missing key must yield null");

        Object empty = bundle.handleGetObject("Empty");
        check(empty instanceof String[], "empty array value expected");
        String[] emptyArr = (String[]) empty;
        check(emptyArr.length == 7, "empty contents still yield length 7");
        check("Empty".equals(emptyArr[0]), "key must be inserted for empty contents");
        for (int i = 1; i < 7; i++) {
            check(emptyArr[i] == null, "unfilled slot must be null at " + i);
        }
    }
}
