/*
 * @test
 * @summary Test sun.util.locale.provider.CollatorProviderImpl.getInstance(Locale) behavior
 * @run main CollatorProviderImplWitupTest
 */

import sun.util.locale.provider.CollatorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CollatorProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        testNullLocaleThrows();
        testGetInstanceReturnsCloneWithNoDecomposition();
    }

    private static void testNullLocaleThrows() {
        Set<String> langtags = new HashSet<>();
        langtags.add("en");
        CollatorProviderImpl provider = new CollatorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        try {
            provider.getInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetInstanceReturnsCloneWithNoDecomposition() throws Exception {
        Set<String> langtags = new HashSet<>();
        langtags.add("en");
        CollatorProviderImpl provider = new CollatorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        Locale locale = Locale.ENGLISH;
        Collator collator1 = provider.getInstance(locale);
        Collator collator2 = provider.getInstance(locale);

        if (collator1 == null) {
            throw new RuntimeException("getInstance returned null");
        }

        if (!(collator1 instanceof RuleBasedCollator)) {
            throw new RuntimeException("getInstance did not return a RuleBasedCollator");
        }

        if (collator1 == collator2) {
            throw new RuntimeException("getInstance returned the same instance instead of a clone");
        }

        if (collator1.getDecomposition() != Collator.NO_DECOMPOSITION) {
            throw new RuntimeException("Returned collator does not have NO_DECOMPOSITION set");
        }

        if (collator2.getDecomposition() != Collator.NO_DECOMPOSITION) {
            throw new RuntimeException("Returned collator clone does not have NO_DECOMPOSITION set");
        }
    }
}
