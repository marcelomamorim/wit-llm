/*
 * @test
 * @summary IsoFields.WEEK_OF_WEEK_BASED_YEAR.getDisplayName(Locale) rejects null and returns a stable non-empty name
 * @modules java.base/java.time.temporal
 * @run main/othervm --add-opens java.base/java.time.temporal=ALL-UNNAMED IsoFieldsWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.temporal.IsoFields;
import java.util.Locale;

public class IsoFieldsWitupTest {

    public static void main(String[] args) throws Exception {
        Object field = IsoFields.WEEK_OF_WEEK_BASED_YEAR;
        Method m = field.getClass().getMethod("getDisplayName", Locale.class);
        m.setAccessible(true);

        String first = (String) m.invoke(field, Locale.US);
        if (first == null || first.isEmpty()) {
            throw new AssertionError("display name must be non-empty");
        }
        String second = (String) m.invoke(field, Locale.US);
        if (!first.equals(second)) {
            throw new AssertionError("display name must be deterministic: " + first + " vs " + second);
        }

        String root = (String) m.invoke(field, Locale.ROOT);
        if (root == null || root.isEmpty()) {
            throw new AssertionError("root locale display name must be non-empty");
        }

        try {
            m.invoke(field, (Object) null);
            throw new AssertionError("null locale must be rejected");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof NullPointerException)) {
                throw new AssertionError("expected NullPointerException but got " + e.getCause(), e);
            }
        }
    }
}
