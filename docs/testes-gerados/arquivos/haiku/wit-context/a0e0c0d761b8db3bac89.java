/*
 * @test
 * @summary Verifies that getDisplayCountry rejects null country code or null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main LocaleNameProviderImplWitupTest
 */
import java.util.HashSet;
import java.util.Locale;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        LocaleNameProviderImpl p = new LocaleNameProviderImpl(null, new HashSet<String>());
        expectNPE(p, null, Locale.US);
        expectNPE(p, "US", null);
        expectNPE(p, null, null);
    }

    private static void expectNPE(LocaleNameProviderImpl p, String ctry, Locale locale) {
        try {
            p.getDisplayCountry(ctry, locale);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for ctry=" + ctry + ", locale=" + locale);
    }
}
