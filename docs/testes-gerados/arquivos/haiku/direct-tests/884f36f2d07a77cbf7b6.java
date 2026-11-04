/*
 * @test
 * @summary Verifies observable behavior of LocaleNameProviderImpl.getDisplayLanguage delegation
 * @modules java.base/sun.util.locale.provider
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import java.util.HashSet;
import sun.util.locale.provider.LocaleNameProviderImpl;

public class LocaleNameProviderImplWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static LocaleNameProviderImpl newProvider() {
        Set<String> langtags = new HashSet<>();
        langtags.add("en");
        langtags.add("fr");
        return new LocaleNameProviderImpl(null, langtags);
    }

    public static void main(String[] args) throws Exception {
        LocaleNameProviderImpl provider = newProvider();

        String fr = provider.getDisplayLanguage("fr", Locale.US);
        check("French".equals(fr), "expected French but was " + fr);

        String again = provider.getDisplayLanguage("fr", Locale.US);
        check(fr.equals(again), "repeated call must be deterministic");

        String en = provider.getDisplayLanguage("en", Locale.FRANCE);
        check("anglais".equals(en), "expected anglais but was " + en);
    }
}
