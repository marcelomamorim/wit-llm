/*
 * @test
 * @summary Verifies that X509CertImpl.getEncoded() returns a defensive copy of the encoded certificate on each call
 * @modules java.base/sun.security.x509
 * @run main X509CertImplWitupTest
 */

import java.security.cert.CertificateEncodingException;
import java.util.Arrays;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        X509CertImpl cert = new X509CertImpl();
        byte[] first;
        try {
            first = cert.getEncoded();
        } catch (CertificateEncodingException e) {
            return;
        }
        if (first == null) {
            throw new RuntimeException("getEncoded() returned null");
        }
        byte[] second = cert.getEncoded();
        if (first == second) {
            throw new RuntimeException("getEncoded() must return a new array on each call");
        }
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("Encoded content changed between calls");
        }
        first[0] = (byte) ~first[0];
        byte[] third = cert.getEncoded();
        if (!Arrays.equals(second, third)) {
            throw new RuntimeException("Mutating returned array affected internal encoding");
        }
    }
}
