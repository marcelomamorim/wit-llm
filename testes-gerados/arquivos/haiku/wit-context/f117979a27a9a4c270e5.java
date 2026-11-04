/*
 * @test
 * @summary Verifies that DateFormatProviderImpl.getTimeInstance rejects a null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main DateFormatProviderImplWitupTest
 */
import java.text.DateFormat;
import java.util.Collections;
import sun.util.locale.provider.DateFormatProviderImpl;

public class DateFormatProviderImplWitupTest {
    public static void main(String[] args) {
        DateFormatProviderImpl provider =
            new DateFormatProviderImpl(null, Collections.<String>emptySet());
        try {
            DateFormat df = provider.getTimeInstance(DateFormat.SHORT, null);
            throw new AssertionError("Expected NullPointerException for null locale, got: " + df);
        } catch (NullPointerException expected) {
        }
    }
}
