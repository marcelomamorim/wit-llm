/*
 * @test
 * @summary Checks CertificateSerialNumber.get(String) for the "number" attribute and unrecognized names
 * @modules java.base/sun.security.x509
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.SerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateSerialNumber c = new CertificateSerialNumber(42);

        SerialNumber s = c.get("number");
        check(s != null, "get(\"number\") must not return null");
        check(c.get("number") == s, "repeated get(\"number\") must return the same serial");
        check(c.get("NUMBER") == s, "get must be case-insensitive (NUMBER)");
        check(c.get("Number") == s, "get must be case-insensitive (Number)");

        CertificateSerialNumber b = new CertificateSerialNumber(BigInteger.valueOf(42));
        check(c.toString().equals(b.toString()),
              "int and BigInteger constructors with same value must give same string");
        check(c.get("number").toString().equals(b.get("number").toString()),
              "serial string must match for equal values");

        expectIOException(c, "serial");
        expectIOException(c, "");
        expectIOException(c, "numbers");
        expectIOException(c, "x509.info.serialNumber");
    }

    private static void expectIOException(CertificateSerialNumber c, String name) {
        try {
            c.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: \"" + name + "\"");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
