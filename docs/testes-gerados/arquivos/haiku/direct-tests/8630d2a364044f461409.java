/*
 * @test
 * @summary Verifies CertificateSerialNumber.delete(String) clears the serial number for "number" (case-insensitive) and throws IOException for unrecognized names.
 * @modules java.base/sun.security.x509
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteNumberClearsSerial();
        testDeleteIsCaseInsensitive();
        testDeleteUnknownNameThrowsIOException();
        testDeleteUnknownNameKeepsSerial();
        testDeleteNumberIsIdempotent();
    }

    static void testDeleteNumberClearsSerial() throws IOException {
        CertificateSerialNumber csn = new CertificateSerialNumber(BigInteger.valueOf(42));
        if (csn.toString().isEmpty()) {
            throw new RuntimeException("Precondition failed: toString should not be empty before delete");
        }
        csn.delete(CertificateSerialNumber.NUMBER);
        String s = csn.toString();
        if (!s.equals("")) {
            throw new RuntimeException("Expected empty string after delete(NUMBER), got: " + s);
        }
    }

    static void testDeleteIsCaseInsensitive() throws IOException {
        CertificateSerialNumber csn = new CertificateSerialNumber(7);
        csn.delete("Number");
        if (!csn.toString().equals("")) {
            throw new RuntimeException("delete(\"Number\") should clear serial");
        }
    }

    static void testDeleteUnknownNameThrowsIOException() throws IOException {
        CertificateSerialNumber csn = new CertificateSerialNumber(1);
        expectIOException(csn, "serialNumber");
        expectIOException(csn, "bogus");
    }

    static void testDeleteUnknownNameKeepsSerial() throws IOException {
        CertificateSerialNumber csn = new CertificateSerialNumber(99);
        String before = csn.toString();
        try {
            csn.delete("bogus");
        } catch (IOException expected) {
            // expected
        }
        String after = csn.toString();
        if (!before.equals(after)) {
            throw new RuntimeException("Serial changed after failed delete: " + before + " -> " + after);
        }
    }

    static void testDeleteNumberIsIdempotent() throws IOException {
        CertificateSerialNumber csn = new CertificateSerialNumber(5);
        csn.delete(CertificateSerialNumber.NUMBER);
        csn.delete(CertificateSerialNumber.NUMBER);
        if (!csn.toString().equals("")) {
            throw new RuntimeException("Expected empty string after repeated delete");
        }
    }

    static void expectIOException(CertificateSerialNumber csn, String name) {
        try {
            csn.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for delete(\"" + name + "\")");
    }
}
