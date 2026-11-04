/*
 * @test
 * @summary WITUP-derived check that getDateInstance rejects a null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main DateFormatProviderImplWitupTest
 */

import java.text.DateFormat;
import java.util.Collections;
import sun.util.locale.provider.DateFormatProviderImpl;

public class DateFormatProviderImplWitupTest {
    public static void main(String[] args) {
        DateFormatProviderImpl provider = new DateFormatProviderImpl(null, Collections.<String>emptySet());
        boolean thrown = false;
        try {
            DateFormat df = provider.getDateInstance(DateFormat.SHORT, null);
            if (df == null) {
                throw new RuntimeException("unexpected null DateFormat");
            }
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null locale");
        }
    }
}
