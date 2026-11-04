/* @test    @summary Test delete method of PolicyInformation class    @run main PolicyInformationWitupTest */

import java.io.IOException;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import sun.security.util.DerValue;
import sun.security.x509.PolicyInformation;

public class PolicyInformationWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteQualifiers();
        testDeleteId();
        testDeleteUnrecognized();
    }

    static void testDeleteQualifiers() throws Exception {
        Set<PolicyQualifierInfo> qualifiers = new HashSet<>();
        PolicyQualifierInfo pqInfo = new PolicyQualifierInfo(new DerValue(new byte[] {0x05, 0x00}));
        qualifiers.add(pqInfo);
        PolicyInformation pi = new PolicyInformation(null, qualifiers);
        pi.delete("qualifiers");
        assert pi.policyQualifiers.isEmpty() : "Qualifiers should be empty after delete";
    }

    static void testDeleteId() throws Exception {
        Set<PolicyQualifierInfo> qualifiers = new HashSet<>();
        PolicyQualifierInfo pqInfo = new PolicyQualifierInfo(new DerValue(new byte[] {0x05, 0x00}));
        qualifiers.add(pqInfo);
        PolicyInformation pi = new PolicyInformation(null, qualifiers);
        try {
            pi.delete("id");
            assert false : "Expected IOException when deleting id";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute ID may not be deleted") : "Unexpected exception message";
        }
    }

    static void testDeleteUnrecognized() throws Exception {
        Set<PolicyQualifierInfo> qualifiers = new HashSet<>();
        PolicyQualifierInfo pqInfo = new PolicyQualifierInfo(new DerValue(new byte[] {0x05, 0x00}));
        qualifiers.add(pqInfo);
        PolicyInformation pi = new PolicyInformation(null, qualifiers);
        try {
            pi.delete("unknown");
            assert false : "Expected IOException for unrecognized attribute";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name [") : "Unexpected exception message";
        }
    }
}
