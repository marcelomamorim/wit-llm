/* @test    @summary Test getTimeInstance with null locale to verify NullPointerException is thrown    @run main DateFormatProviderImplWitupTest */

import java.text.DateFormat;
import java.util.Locale;

public class DateFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetTimeInstanceWithNullLocale();
    }

    public static void testGetTimeInstanceWithNullLocale() {
        try {
            // Invoca o método com locale null, esperando uma NullPointerException
            DateFormat df = new sun.util.locale.provider.DateFormatProviderImpl(
                sun.util.locale.provider.LocaleProviderAdapter.Type.DEFAULT,
                java.util.Collections.emptySet()
            ).getTimeInstance(java.text.DateFormat.SHORT, null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Exceção esperada, o teste passa
        }
    }
}
