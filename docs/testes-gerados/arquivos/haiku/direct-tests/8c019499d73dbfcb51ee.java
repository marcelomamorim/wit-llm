/*
 * @test
 * @summary Verify DistributionPointName.encode writes the expected context-specific tag for fullName and relativeName
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main DistributionPointNameWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.x509.DNSName;
import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNames;
import sun.security.x509.RDN;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) throws Exception {
        GeneralNames names = new GeneralNames();
        names.add(new GeneralName(new DNSName("example.com")));
        DistributionPointName fullDpn = new DistributionPointName(names);
        DerOutputStream out = new DerOutputStream();
        fullDpn.encode(out);
        byte[] fullBytes = out.toByteArray();
        if (fullBytes.length <= 2) {
            throw new RuntimeException("fullName encoding too short");
        }
        if ((fullBytes[0] & 0xFF) != 0xA0) {
            throw new RuntimeException("fullName tag mismatch: " + (fullBytes[0] & 0xFF));
        }

        RDN rdn = new RDN("CN=test");
        DistributionPointName relDpn = new DistributionPointName(rdn);
        out = new DerOutputStream();
        relDpn.encode(out);
        byte[] relBytes = out.toByteArray();
        if (relBytes.length <= 2) {
            throw new RuntimeException("relativeName encoding too short");
        }
        if ((relBytes[0] & 0xFF) != 0xA1) {
            throw new RuntimeException("relativeName tag mismatch: " + (relBytes[0] & 0xFF));
        }

        boolean thrown = false;
        try {
            new DistributionPointName((GeneralNames) null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("null fullName should be rejected");
        }
    }
}
