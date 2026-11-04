/* @test    @summary Test for sun.security.x509.OtherName.subtreeDepth() method
 * @run main OtherNameWitupTest
 */

import sun.security.x509.OtherName;
import java.io.IOException;

public class OtherNameWitupTest {

    public static void main(String[] args) throws Exception {
        testSubtreeDepthThrowsException();
    }

    public static void testSubtreeDepthThrowsException() {
        try {
            // Cria um objeto OtherName com valores fictícios, pois o método lança exceção sempre
            ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
            byte[] value = {0x01, 0x02};
            OtherName oname = new OtherName(oid, value);
            int result = oname.subtreeDepth();
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Esperado
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
