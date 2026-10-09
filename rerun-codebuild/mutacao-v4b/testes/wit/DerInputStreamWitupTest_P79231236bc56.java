/* @test
   @summary Testa o método getEnumerated() de DerInputStream para comportamento esperado.
   @run main DerInputStreamWitupTest_P79231236bc56
 */
import java.util.Objects;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
public class DerInputStreamWitupTest_P79231236bc56 {
    public static void main(String[] args) throws Exception {
        testGetEnumeratedNormal();
        testGetEnumeratedInvalidTag();
    }
    private static void testGetEnumeratedNormal() throws Exception {
        byte[] data = new byte[] { (byte)DerValue.tag_Enumerated, 0x01, 0x02 };
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getEnumerated();
        if (result != 258) {
            throw new AssertionError("Expected 258, got " + result);
        }
    }
    private static void testGetEnumeratedInvalidTag() throws Exception {
        byte[] data = new byte[] { 0x02, 0x01, 0x02 };
        DerInputStream dis = new DerInputStream(data);
        try {
            dis.getEnumerated();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // expected
        }
    }
}
