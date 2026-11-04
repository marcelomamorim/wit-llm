/*
 * @test
 * @summary Test InvalidityDateExtension constructors and encoding behavior
 * @run main InvalidityDateExtensionWitupTest
 */

import sun.security.x509.InvalidityDateExtension;
import java.util.Date;
import java.io.IOException;
import java.util.Calendar;

public class InvalidityDateExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithDate();
        testConstructorWithNullDate();
    }

    private static void testConstructorWithDate() throws IOException {
        // Create a specific date
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.JANUARY, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();

        InvalidityDateExtension ext = new InvalidityDateExtension(date);

        // The extensionValue should not be null
        if (ext.extensionValue == null) {
            throw new RuntimeException("extensionValue should not be null when date is set");
        }

        // The date field should be equal to the input date
        Date extDate = (Date) ext.get("date");
        if (!date.equals(extDate)) {
            throw new RuntimeException("Stored date does not match input date");
        }

        // The critical flag should be false by default
        Boolean critical = (Boolean) ext.get("critical");
        if (critical == null || critical.booleanValue() != false) {
            throw new RuntimeException("Critical flag should be false by default");
        }
    }

    private static void testConstructorWithNullDate() throws IOException {
        // Construct with null date
        InvalidityDateExtension ext = new InvalidityDateExtension((Date) null);

        // The extensionValue should be null when date is null
        if (ext.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null when date is null");
        }

        // The date field should be null
        Object date = ext.get("date");
        if (date != null) {
            throw new RuntimeException("Date attribute should be null when constructed with null date");
        }
    }
}