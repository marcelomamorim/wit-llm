/* @test    @summary Test NetscapeCertTypeExtension constructor with boolean array    @run main NetscapeCertTypeExtensionWitupTest */

import sun.security.x509.NetscapeCertTypeExtension;
import java.io.IOException;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        boolean[] bits = {true, false, true, false, true, false, true, false};
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        assert ext != null : "Extension instance should not be null";
    }
}
