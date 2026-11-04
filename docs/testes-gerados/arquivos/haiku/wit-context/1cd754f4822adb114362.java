/*
 * @test
 * @summary WITUP-derived tests for JapaneseEra.valueOf(String)
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;

public class JapaneseEraWitupTest {

    public static void main(String[] args) {
        testNullThrowsNullPointerException();
        testUnknownNamesThrowIllegalArgumentException();
        testKnownNamesReturnMatchingEra();
    }

    static void testNullThrowsNullPointerException() {
        try {
            JapaneseEra.valueOf(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name");
    }

    static void testUnknownNamesThrowIllegalArgumentException() {
        expectIllegalArgument("");
        expectIllegalArgument("meiji");
        expectIllegalArgument("MEIJI");
        expectIllegalArgument(" Meiji");
        expectIllegalArgument("Meiji ");
        expectIllegalArgument("Unknown");
    }

    static void testKnownNamesReturnMatchingEra() {
        checkKnown("Meiji");
        checkKnown("Taisho");
        checkKnown("Showa");
        checkKnown("Heisei");

        JapaneseEra first = JapaneseEra.valueOf("Meiji");
        JapaneseEra second = JapaneseEra.valueOf("Meiji");
        if (first != second) {
            throw new RuntimeException("expected the same instance for repeated lookups");
        }
    }

    static void checkKnown(String name) {
        JapaneseEra era = JapaneseEra.valueOf(name);
        if (era == null) {
            throw new RuntimeException("valueOf returned null for " + name);
        }
        if (!name.equals(era.getName())) {
            throw new RuntimeException("unexpected name for " + name + ": " + era.getName());
        }
    }

    static void expectIllegalArgument(String name) {
        try {
            JapaneseEra.valueOf(name);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for: [" + name + "]");
    }
}
