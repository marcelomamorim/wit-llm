/* @test
   @summary Testa o método getInteger() de DerInputStream para comportamento esperado.
   @run main DerInputStreamWitupTest_P4eda73210fe9
 */
import java.io.IOException;
import sun.security.util.DerInputStream;
import java.util.Objects;

public class DerInputStreamWitupTest_P4eda73210fe9 {
    public static void main(String[] args) throws Exception {
        testGetIntegerWithValidData();
        testGetIntegerWithInvalidTag();
    }

    private static void testGetIntegerWithValidData() throws IOException {
        byte[] data = new byte[] { (byte)0x02, 0x01, 0x05 }; // tag=0x02 (INTEGER), length=1, value=5
        DerInputStream dis = new DerInputStream(data);
        int result = dis.getInteger();
        if (result != 5) {
            throw new AssertionError("Expected 5, got " + result);
        }
    }

    private static void testGetIntegerWithInvalidTag() throws IOException {
        byte[] data = new byte[] { (byte)0x03, 0x01, 0x05 }; // tag=0x03 (not INTEGER)
        DerInputStream dis = new DerInputStream(data);
        try {
            dis.getInteger();
            throw new AssertionError("Expected IOException due to invalid tag");
        } catch (IOException e) {
            // expected
            if (!e.getMessage().contains("DER input, Integer tag error")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
