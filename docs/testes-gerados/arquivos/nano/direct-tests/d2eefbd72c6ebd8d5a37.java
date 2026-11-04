/* @test    @summary Test UniqueIdentity constructor with DerInputStream input    @run main UniqueIdentityWitupTest */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.UniqueIdentity;

public class UniqueIdentityWitupTest {

    public static void main(String[] args) throws IOException {
        testUniqueIdentityConstructor();
    }

    private static void testUniqueIdentityConstructor() throws IOException {
        byte[] sampleBytes = { (byte)0x80, (byte)0x01, (byte)0x02 };
        DerValue derVal = new DerValue(sampleBytes);
        DerInputStream derIn = new DerInputStream(new byte[] { (byte)0x03, (byte)0x02, (byte)0x80, (byte)0x01, (byte)0x02 });
        UniqueIdentity uid = new UniqueIdentity(derIn);
        String str = uid.toString();
        if (!str.contains("UniqueIdentity")) {
            throw new AssertionError("toString() output does not contain expected string");
        }
        boolean[] idArray = uid.getId();
        if (idArray == null || idArray.length == 0) {
            throw new AssertionError("getId() returned null or empty array");
        }
        if (idArray.length != 8) {
            throw new AssertionError("Unexpected id array length: " + idArray.length);
        }
    }
}
