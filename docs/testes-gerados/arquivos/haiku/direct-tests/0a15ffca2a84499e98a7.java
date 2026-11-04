/*
 * @test
 * @summary Verifies that the single-argument InvalidityDateExtension constructor delegates to the non-critical two-argument constructor
 * @modules java.base/sun.security.x509
 * @run main InvalidityDateExtensionWitupTest
 */

import java.util.Arrays;
import java.util.Date;

import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        Date date = new Date(1700000000000L);

        InvalidityDateExtension single = new InvalidityDateExtension(date);
        InvalidityDateExtension explicit = new InvalidityDateExtension(false, date);

        if (single.isCritical()) {
            throw new RuntimeException("Single-argument constructor must produce a non-critical extension");
        }
        if (single.isCritical() != explicit.isCritical()) {
            throw new RuntimeException("Criticality differs from explicit non-critical constructor");
        }
        if (single.getExtensionValue() == null) {
            throw new RuntimeException("Encoded value must not be null for a non-null date");
        }
        if (!Arrays.equals(single.getExtensionValue(), explicit.getExtensionValue())) {
            throw new RuntimeException("Encoding differs from explicit non-critical constructor");
        }

        InvalidityDateExtension again = new InvalidityDateExtension(date);
        if (!Arrays.equals(single.getExtensionValue(), again.getExtensionValue())) {
            throw new RuntimeException("Encoding must be deterministic for the same date");
        }

        InvalidityDateExtension nullDate = new InvalidityDateExtension(null);
        if (nullDate.isCritical()) {
            throw new RuntimeException("Null-date extension must be non-critical");
        }
        if (nullDate.getExtensionValue() != null) {
            throw new RuntimeException("Encoded value must be null when the date is null");
        }
    }
}
