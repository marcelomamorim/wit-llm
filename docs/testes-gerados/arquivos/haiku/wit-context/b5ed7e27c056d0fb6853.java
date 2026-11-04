/*
 * @test
 * @summary Verify Locale.getISOCountries(null) throws NullPointerException
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        try {
            Locale.getISOCountries(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null type");
    }
}
