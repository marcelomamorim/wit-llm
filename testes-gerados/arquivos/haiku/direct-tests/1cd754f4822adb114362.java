/*
 * @test
 * @summary Black-box tests for JapaneseEra.valueOf(String) name lookup, null handling and invalid names
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;

public class JapaneseEraWitupTest {

    public static void main(String[] args) throws Exception {
        testKnownNameReturnsEraWithSameName();
        testLookupReturnsSameInstanceForSameName();
        testUnknownNameThrowsIllegalArgument();
        testEmptyNameThrowsIllegalArgument();
        testNameIsCaseSensitive();
        testNullThrowsNullPointerException();
    }

    static void testKnownNameReturnsEraWithSameName() {
        JapaneseEra era = JapaneseEra.valueOf("Meiji");
        check(era != null, "valueOf(\"Meiji\") returned null");
        check("Meiji".equals(era.getName()), "name mismatch for Meiji: " + era.getName());

        JapaneseEra heisei = JapaneseEra.valueOf("Heisei");
        check(heisei != null, "valueOf(\"Heisei\") returned null");
        check("Heisei".equals(heisei.getName()), "name mismatch for Heisei: " + heisei.getName());
    }

    static void testLookupReturnsSameInstanceForSameName() {
        JapaneseEra a = JapaneseEra.valueOf("Showa");
        JapaneseEra b = JapaneseEra.valueOf("Showa");
        check(a == b, "valueOf should return the same known era instance");
    }

    static void testUnknownNameThrowsIllegalArgument() {
        expectIllegalArgument("NoSuchEra");
    }

    static void testEmptyNameThrowsIllegalArgument() {
        expectIllegalArgument("");
    }

    static void testNameIsCaseSensitive() {
        expectIllegalArgument("meiji");
        expectIllegalArgument("MEIJI");
    }

    static void testNullThrowsNullPointerException() {
        boolean thrown = false;
        try {
            JapaneseEra.valueOf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "valueOf(null) should throw NullPointerException");
    }

    static void expectIllegalArgument(String name) {
        boolean thrown = false;
        try {
            JapaneseEra.valueOf(name);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "valueOf(\"" + name + "\") should throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
