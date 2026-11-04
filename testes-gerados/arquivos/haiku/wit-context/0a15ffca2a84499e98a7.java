/*
 * @test
 * @summary Smoke and contract tests for InvalidityDateExtension(Date) constructor
 * @modules java.base/sun.security.x509
 * @run main InvalidityDateExtensionWitupTest
 */

import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructWithDateDoesNotThrow();
        testConstructWithNullDateDoesNotThrow();
        testConstantsAreStable();
    }

    private static void testConstructWithDateDoesNotThrow() throws Exception {
        Date date = new Date(1500000000000L);
        InvalidityDateExtension ext = new InvalidityDateExtension(date);
        if (ext == null) {
            throw new RuntimeException("constructor returned null");
        }
    }

    private static void testConstructWithNullDateDoesNotThrow() throws Exception {
        InvalidityDateExtension ext = new InvalidityDateExtension((Date) null);
        if (ext == null) {
            throw new RuntimeException("constructor returned null for null date");
        }
    }

    private static void testConstantsAreStable() {
        if (!"InvalidityDate".equals(InvalidityDateExtension.NAME)) {
            throw new RuntimeException("unexpected NAME: " + InvalidityDateExtension.NAME);
        }
        if (!"date".equals(InvalidityDateExtension.DATE)) {
            throw new RuntimeException("unexpected DATE: " + InvalidityDateExtension.DATE);
        }
    }
}
