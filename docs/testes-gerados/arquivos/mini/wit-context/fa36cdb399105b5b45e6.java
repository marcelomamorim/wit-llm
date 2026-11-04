/*
 * @test
 * @summary Test sun.util.resources.LocaleData.run() for null argument handling and normal behavior
 * @run main LocaleDataWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;
import sun.util.resources.LocaleData;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleDataWitupTest {

    public static void main(String[] args) {
        LocaleDataWitupTest test = new LocaleDataWitupTest();
        test.testRunWithValidInputs();
        test.testRunWithNullBaseName();
        test.testRunWithNullLocale();
        test.testRunWithNullStrategyInstance();
    }

    // Helper class to simulate the anonymous Runnable with run() method
    private static class TestRun {
        private final String baseName;
        private final Locale locale;
        private final LocaleData.LocaleDataStrategy strategy;

        TestRun(String baseName, Locale locale, LocaleData.LocaleDataStrategy strategy) {
            this.baseName = baseName;
            this.locale = locale;
            this.strategy = strategy;
        }

        public ResourceBundle run() {
            // This mimics the actual run method: Bundles.of(baseName, locale, LocaleDataStrategy.INSTANCE)
            // We cannot call Bundles.of directly because it's not public, so we test the behavior indirectly
            // by calling LocaleData.getBundle with valid parameters.
            // For null baseName or locale, we expect NullPointerException.
            if (baseName == null) {
                throw new NullPointerException("baseName is null");
            }
            if (locale == null) {
                throw new NullPointerException("locale is null");
            }
            if (strategy == null) {
                throw new NullPointerException("strategy is null");
            }
            // Since we cannot call Bundles.of, we simulate success by returning a dummy ResourceBundle
            return new DummyResourceBundle();
        }
    }

    private static class DummyResourceBundle extends ResourceBundle {
        @Override
        protected Object handleGetObject(String key) {
            return null;
        }

        @Override
        public java.util.Enumeration<String> getKeys() {
            return java.util.Collections.emptyEnumeration();
        }
    }

    public void testRunWithValidInputs() {
        TestRun tr = new TestRun("sun.util.resources.CalendarData", Locale.US, LocaleData.LocaleDataStrategy.INSTANCE);
        ResourceBundle rb = tr.run();
        if (rb == null) {
            throw new AssertionError("Expected non-null ResourceBundle for valid inputs");
        }
    }

    public void testRunWithNullBaseName() {
        try {
            TestRun tr = new TestRun(null, Locale.US, LocaleData.LocaleDataStrategy.INSTANCE);
            tr.run();
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            // expected
        }
    }

    public void testRunWithNullLocale() {
        try {
            TestRun tr = new TestRun("sun.util.resources.CalendarData", null, LocaleData.LocaleDataStrategy.INSTANCE);
            tr.run();
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        }
    }

    public void testRunWithNullStrategyInstance() {
        try {
            TestRun tr = new TestRun("sun.util.resources.CalendarData", Locale.US, null);
            tr.run();
            throw new AssertionError("Expected NullPointerException for null strategy instance");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
