/* @test    @summary Test CRLNumberExtension constructor with int parameter    @run main CRLNumberExtensionWitupTest */

import sun.security.x509.CRLNumberExtension;
import java.io.IOException;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithInt();
        testConstructorWithLargeInt();
    }

    private static void testConstructorWithInt() throws IOException {
        int crlNum = 12345;
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        assert ext != null : "Extension should not be null";
    }

    private static void testConstructorWithLargeInt() throws IOException {
        int crlNum = Integer.MAX_VALUE;
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        assert ext != null : "Extension with max int should not be null";
    }
}
