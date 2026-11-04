/*
 * @test
 * @summary Witup-derived checks for CertificateSerialNumber.delete(String) exception and observable state behavior
 * @modules java.base/sun.security.x509
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;

import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateSerialNumber withBigInteger = new CertificateSerialNumber(BigInteger.valueOf(42));
        if (withBigInteger.toString().isEmpty()) {
            throw new RuntimeException("serial string unexpectedly empty before delete");
        }
        withBigInteger.delete("number");
        if (!withBigInteger.toString().isEmpty()) {
            throw new RuntimeException("serial string not cleared after delete(\"number\")");
        }

        CertificateSerialNumber withInt = new CertificateSerialNumber(42);
        withInt.delete("NUMBER");
        if (!withInt.toString().isEmpty()) {
            throw new RuntimeException("serial string not cleared after delete(\"NUMBER\")");
        }

        CertificateSerialNumber mixedCase = new CertificateSerialNumber(7);
        mixedCase.delete("Number");
        if (!mixedCase.toString().isEmpty()) {
            throw new RuntimeException("serial string not cleared after delete(\"Number\")");
        }

        expectIOExceptionKeepingState(new CertificateSerialNumber(7), "serialNumber");
        expectIOExceptionKeepingState(new CertificateSerialNumber(7), "other");
        expectIOExceptionKeepingState(new CertificateSerialNumber(7), "");
        expectIOExceptionKeepingState(new CertificateSerialNumber(7), "numbers");
    }

    static void expectIOExceptionKeepingState(CertificateSerialNumber csn, String name) {
        try {
            csn.delete(name);
        } catch (IOException e) {
            if (csn.toString().isEmpty()) {
                throw new RuntimeException("state cleared despite IOException for name: " + name);
            }
            return;
        }
        throw new RuntimeException("expected IOException for name: " + name);
    }
}
