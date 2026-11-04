/*
 * @test
 * @summary White-box-free checks of Locale.getISOLanguages() public contract: non-null, non-empty, contains known codes, defensive copy
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {

    public static void main(String[] args) {
        String[] first = Locale.getISOLanguages();
        if (first == null) {
            throw new RuntimeException("getISOLanguages() returned null");
        }
        if (first.length == 0) {
            throw new RuntimeException("getISOLanguages() returned empty array");
        }

        boolean foundEn = false;
        boolean foundPt = false;
        for (String s : first) {
            if (s == null) {
                throw new RuntimeException("null element in ISO languages array");
            }
            if ("en".equals(s)) {
                foundEn = true;
            }
            if ("pt".equals(s)) {
                foundPt = true;
            }
        }
        if (!foundEn) {
            throw new RuntimeException("expected \"en\" in ISO languages");
        }
        if (!foundPt) {
            throw new RuntimeException("expected \"pt\" in ISO languages");
        }

        String marker = "zz-mutated";
        first[0] = marker;

        String[] second = Locale.getISOLanguages();
        if (second == first) {
            throw new RuntimeException("getISOLanguages() must return a fresh array");
        }
        for (String s : second) {
            if (marker.equals(s)) {
                throw new RuntimeException("caller mutation leaked into subsequent result");
            }
        }

        String[] third = Locale.getISOLanguages();
        if (third.length != second.length) {
            throw new RuntimeException("inconsistent length across calls");
        }
        for (int i = 0; i < third.length; i++) {
            if (third[i] == null ? second[i] != null : !third[i].equals(second[i])) {
                throw new RuntimeException("inconsistent content at index " + i);
            }
        }
    }
}
