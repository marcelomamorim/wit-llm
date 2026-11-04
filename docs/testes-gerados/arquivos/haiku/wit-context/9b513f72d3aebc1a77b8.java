/*
 * @test
 * @summary Witup-generated checks for CertificateSerialNumber.get(String)
 * @modules java.base/sun.security.x509
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateSerialNumber csn = new CertificateSerialNumber(42);

        Object n1 = csn.get("number");
        Object n2 = csn.get("NUMBER");
        if (n1 == null || n2 == null) {
            throw new RuntimeException("get(number) returned null");
        }
        if (!n1.toString().equals(n2.toString())) {
            throw new RuntimeException("case-insensitive lookup returned a different value");
        }

        checkRejected(csn, "serial");
        checkRejected(csn, "");
        checkRejected(csn, "numbers");

        System.out.println("PASS");
    }

    private static void checkRejected(CertificateSerialNumber csn, String name) {
        try {
            csn.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for attribute name: " + name);
    }
}
