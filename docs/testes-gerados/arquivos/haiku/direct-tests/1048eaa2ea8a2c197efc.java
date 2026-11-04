/*
 * @test
 * @summary Verifies observable behavior of CollatorProviderImpl.getInstance(Locale)
 * @modules java.base/sun.util.locale.provider
 * @run main CollatorProviderImplWitupTest
 */
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.CollatorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class CollatorProviderImplWitupTest {
    public static void main(String[] args) {
        CollatorProviderImpl provider = new CollatorProviderImpl(
                LocaleProviderAdapter.Type.JRE, Collections.singleton("en-US"));

        boolean npeThrown = false;
        try {
            provider.getInstance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("NullPointerException expected for null locale");
        }

        Collator c1 = provider.getInstance(Locale.US);
        if (c1 == null) {
            throw new RuntimeException("getInstance returned null");
        }
        if (!(c1 instanceof RuleBasedCollator)) {
            throw new RuntimeException("Expected RuleBasedCollator");
        }
        if (c1.getDecomposition() != Collator.NO_DECOMPOSITION) {
            throw new RuntimeException("Decomposition should be NO_DECOMPOSITION");
        }

        Collator c2 = provider.getInstance(Locale.US);
        if (c1 == c2) {
            throw new RuntimeException("Each call should return a new clone instance");
        }

        if (c1.compare("a", "b") >= 0) {
            throw new RuntimeException("Collator should order 'a' before 'b'");
        }
    }
}
