/*
 * @test
 * @summary Verify SignerInfo.encode produces deterministic DER that round-trips through the DER constructor
 * @modules java.base/sun.security.pkcs java.base/sun.security.x509 java.base/sun.security.util
 * @run main SignerInfoWitupTest
 */

import java.math.BigInteger;
import java.util.Arrays;

import sun.security.pkcs.SignerInfo;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;

public class SignerInfoWitupTest {

    private static SignerInfo newSignerInfo() throws Exception {
        X500Name issuer = new X500Name("CN=Test");
        AlgorithmId digestAlg = AlgorithmId.get("SHA-256");
        AlgorithmId sigAlg = AlgorithmId.get("RSA");
        byte[] encryptedDigest = new byte[] {1, 2, 3, 4};
        return new SignerInfo(issuer, BigInteger.ONE, digestAlg, sigAlg, encryptedDigest);
    }

    private static byte[] encodeToBytes(SignerInfo si) throws Exception {
        DerOutputStream out = new DerOutputStream();
        si.encode(out);
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        SignerInfo si = newSignerInfo();

        byte[] first = encodeToBytes(si);
        if (first.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (first[0] != 0x30) {
            throw new RuntimeException("encoding must start with SEQUENCE tag, got " + first[0]);
        }

        byte[] second = encodeToBytes(si);
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("encode is not deterministic across calls");
        }

        SignerInfo parsed = new SignerInfo(new DerInputStream(first));
        byte[] roundTrip = encodeToBytes(parsed);
        if (!Arrays.equals(first, roundTrip)) {
            throw new RuntimeException("round-trip encoding differs from original");
        }
    }
}
