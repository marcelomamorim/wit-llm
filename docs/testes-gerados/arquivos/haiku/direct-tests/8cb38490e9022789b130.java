/*
 * @test
 * @summary Verify public behavior of Locale.getISOCountries: non-empty result, two-letter uppercase codes, well-known codes present, and defensive copy semantics
 * @run main LocaleWitupTest
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LocaleWitupTest {

    public static void main(String[] args) {
        String[] first = Locale.getISOCountries();
        if (first == null) {
            throw new RuntimeException("getISOCountries returned null");
        }
        if (first.length == 0) {
            throw new RuntimeException("getISOCountries returned an empty array");
        }

        for (String code : first) {
            if (code == null || code.length() != 2) {
                throw new RuntimeException("Invalid country code: " + code);
            }
            for (int i = 0; i < code.length(); i++) {
                char c = code.charAt(i);
                if (c < 'A' || c > 'Z') {
                    throw new RuntimeException("Country code not uppercase ASCII: " + code);
                }
            }
        }

        Set<String> set = new HashSet<>(Arrays.asList(first));
        String[] expected = {"US", "BR", "JP", "DE"};
        for (String e : expected) {
            if (!set.contains(e)) {
                throw new RuntimeException("Missing expected country: " + e);
            }
        }

        String[] second = Locale.getISOCountries();
        if (second == first) {
            throw new RuntimeException("Expected a fresh array on each call");
        }
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("Consecutive calls returned different contents");
        }

        String original = second[0];
        second[0] = "XX";
        String[] third = Locale.getISOCountries();
        if (!third[0].equals(original)) {
            throw new RuntimeException("Mutation of returned array leaked into subsequent result");
        }
        if (Arrays.asList(third).contains("XX")) {
            throw new RuntimeException("Mutated value visible in new result");
        }
    }
}
