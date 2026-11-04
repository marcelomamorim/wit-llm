/*
 * @test
 * @summary Test sun.security.x509.X500Name.getEncodedInternal() for basic encoding and caching
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import sun.security.x509.RDN;
import sun.security.x509.AttributeTypeAndValue;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testBasicEncoding();
        testEncodingCaching();
    }

    private static void testBasicEncoding() throws IOException {
        // Construct X500Name with commonName, orgUnit, orgName, country
        X500Name name = new X500Name("CN=Test User, OU=Test Unit, O=Test Org, C=US");
        byte[] encoded1 = name.getEncodedInternal();
        if (encoded1 == null || encoded1.length == 0) {
            throw new RuntimeException("Encoded byte array is null or empty");
        }
        // Call again to check caching returns same array (not necessarily same instance but equal content)
        byte[] encoded2 = name.getEncodedInternal();
        if (encoded2 == null || encoded2.length == 0) {
            throw new RuntimeException("Encoded byte array is null or empty on second call");
        }
        if (encoded1.length != encoded2.length) {
            throw new RuntimeException("Encoded byte arrays differ in length between calls");
        }
        for (int i = 0; i < encoded1.length; i++) {
            if (encoded1[i] != encoded2[i]) {
                throw new RuntimeException("Encoded byte arrays differ in content between calls");
            }
        }
    }

    private static void testEncodingCaching() throws IOException {
        // Create X500Name from RDN array to test getEncodedInternal caching
        // Build RDN array manually
        ObjectIdentifier cnOid = new ObjectIdentifier(new int[] {2,5,4,3}); // commonName OID
        AttributeTypeAndValue atv = new AttributeTypeAndValue(cnOid, new DerValue(DerValue.tag_PrintableString, "TestUser".getBytes("UTF8")));
        RDN rdn = new RDN(new AttributeTypeAndValue[] {atv});
        RDN[] rdns = new RDN[] {rdn};
        X500Name name = new X500Name(rdns);

        byte[] encodedFirst = name.getEncodedInternal();
        if (encodedFirst == null || encodedFirst.length == 0) {
            throw new RuntimeException("Encoded byte array is null or empty");
        }

        // Call again to verify cached value returned
        byte[] encodedSecond = name.getEncodedInternal();
        if (encodedSecond == null || encodedSecond.length == 0) {
            throw new RuntimeException("Encoded byte array is null or empty on second call");
        }

        if (encodedFirst.length != encodedSecond.length) {
            throw new RuntimeException("Encoded byte arrays differ in length between calls");
        }

        for (int i = 0; i < encodedFirst.length; i++) {
            if (encodedFirst[i] != encodedSecond[i]) {
                throw new RuntimeException("Encoded byte arrays differ in content between calls");
            }
        }
    }
}
