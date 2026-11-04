/* @test    @summary Test for NetscapeCertTypeExtension constructor with boolean[] parameter    @run main NetscapeCertTypeExtensionWitupTest */

import sun.security.x509.NetscapeCertTypeExtension;
import java.io.IOException;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testValidBitString();
        testEmptyBitString();
        testNullBitString();
        testLargeBitString();
    }

    static void testValidBitString() throws IOException {
        boolean[] bits = {true, false, true, false, true, false, true, false};
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        if (ext == null) {
            throw new AssertionError("Constructor returned null for valid bit string");
        }
    }

    static void testEmptyBitString() throws IOException {
        boolean[] bits = {};
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        if (ext == null) {
            throw new AssertionError("Constructor returned null for empty bit string");
        }
    }

    static void testNullBitString() {
        try {
            new NetscapeCertTypeExtension((boolean[]) null);
            throw new AssertionError("Expected NullPointerException for null bit string");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testLargeBitString() throws IOException {
        boolean[] bits = new boolean[1000];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = (i % 2 == 0);
        }
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        if (ext == null) {
            throw new AssertionError("Constructor returned null for large bit string");
        }
    }
}
