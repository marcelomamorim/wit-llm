/* @test    @summary Test setEmptyValue method of StringJoiner for various inputs    @run main StringJoinerWitupTest */

import java.util.StringJoiner;

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testSetEmptyValueWithNull();
        testSetEmptyValueWithEmptyString();
        testSetEmptyValueWithRegularString();
        testSetEmptyValueWithLongString();
    }

    private static void testSetEmptyValueWithNull() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        try {
            // Expect NullPointerException due to Objects.requireNonNull
            sj.setEmptyValue(null);
        } catch (NullPointerException e) {
            System.out.println("Passed null input test");
            return;
        }
        System.out.println("Failed null input test");
    }

    private static void testSetEmptyValueWithEmptyString() {
        StringJoiner sj = new StringJoiner(",", "<", ">");
        String emptyStr = "";
        sj.setEmptyValue(emptyStr);
        // No exception expected, verify that toString returns empty string
        String result = sj.toString();
        if (result.equals(emptyStr)) {
            System.out.println("Passed empty string test");
        } else {
            System.out.println("Failed empty string test");
        }
    }

    private static void testSetEmptyValueWithRegularString() {
        StringJoiner sj = new StringJoiner("-", "{", "}");
        String customEmpty = "EMPTY";
        sj.setEmptyValue(customEmpty);
        String result = sj.toString();
        if (result.equals(customEmpty)) {
            System.out.println("Passed regular string test");
        } else {
            System.out.println("Failed regular string test");
        }
    }

    private static void testSetEmptyValueWithLongString() {
        StringJoiner sj = new StringJoiner("|", "(", ")");
        String longString = "LONG_STRING_" + "X".repeat(1000);
        sj.setEmptyValue(longString);
        String result = sj.toString();
        if (result.equals(longString)) {
            System.out.println("Passed long string test");
        } else {
            System.out.println("Failed long string test");
        }
    }
}
