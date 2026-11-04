/*
 * @test
 * @summary Test getEncodedInternal() method of X509CRLImpl for normal and null CRL data
 * @run main X509CRLImplWitupTest
 */

import java.security.cert.CRLException;
import sun.security.x509.X509CRLImpl;

public class X509CRLImplWitupTest {

    public static void main(String[] args) {
        testGetEncodedInternalWithValidData();
        testGetEncodedInternalWithNullData();
    }

    private static void testGetEncodedInternalWithValidData() {
        byte[] crlData = {0x30, 0x82, 0x01, 0x0a}; // sample valid DER-encoded CRL bytes
        X509CRLImpl crl = new X509CRLImpl();
        // Using reflection to set signedCRL since constructor is not invoked here
        try {
            java.lang.reflect.Field signedCRLField = X509CRLImpl.class.getDeclaredField("signedCRL");
            signedCRLField.setAccessible(true);
            signedCRLField.set(crl, crlData);
            byte[] result = crl.getEncodedInternal();
            assert result != null : "getEncodedInternal() returned null for valid data";
            assert result == crlData || java.util.Arrays.equals(result, crlData) : "Returned data does not match input";
            System.out.println("testGetEncodedInternalWithValidData passed.");
        } catch (NoSuchFieldException | IllegalAccessException | CRLException e) {
            throw new RuntimeException("Unexpected exception in testGetEncodedInternalWithValidData", e);
        }
    }

    private static void testGetEncodedInternalWithNullData() {
        X509CRLImpl crl = new X509CRLImpl();
        try {
            java.lang.reflect.Field signedCRLField = X509CRLImpl.class.getDeclaredField("signedCRL");
            signedCRLField.setAccessible(true);
            signedCRLField.set(crl, null);
            crl.getEncodedInternal();
            throw new AssertionError("Expected CRLException was not thrown for null signedCRL");
        } catch (CRLException e) {
            assert e.getMessage().equals("Null CRL to encode") : "Unexpected exception message: " + e.getMessage();
            System.out.println("testGetEncodedInternalWithNullData passed.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unexpected reflection exception", e);
        }
    }
}
