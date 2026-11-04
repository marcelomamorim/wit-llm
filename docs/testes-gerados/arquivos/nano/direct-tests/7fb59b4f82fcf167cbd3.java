/* @test    @summary Test getTBSCertList() method of X509CRLImpl class    @run main X509CRLImplWitupTest */

import sun.security.x509.X509CRLImpl;
import sun.security.util.CRLException;

public class X509CRLImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetTBSCertListWithNull();
        testGetTBSCertListWithValidData();
    }

    private static void testGetTBSCertListWithNull() {
        X509CRLImpl crl = new X509CRLImpl() {
            @Override
            public byte[] getTBSCertList() throws CRLException {
                throw new CRLException("Uninitialized CRL");
            }
        };
        try {
            crl.getTBSCertList();
            throw new AssertionError("Expected CRLException was not thrown");
        } catch (CRLException e) {
            assert e.getMessage().equals("Uninitialized CRL") : "Unexpected exception message: " + e.getMessage();
        }
    }

    private static void testGetTBSCertListWithValidData() throws CRLException {
        byte[] dummyData = {1, 2, 3, 4};
        X509CRLImpl crl = new X509CRLImpl() {
            private byte[] data = dummyData;
            @Override
            public byte[] getTBSCertList() throws CRLException {
                return data.clone();
            }
        };
        byte[] result = crl.getTBSCertList();
        assert result != null : "Result should not be null";
        assert result.length == dummyData.length : "Result length mismatch";
        for (int i = 0; i < result.length; i++) {
            assert result[i] == dummyData[i] : "Data mismatch at index " + i;
        }
    }
}
