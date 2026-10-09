/* @test
   @summary Testa o método getUTF8String() de DerValue para comportamento esperado.
   @run main DerValueWitupTest_P7874ea918173
 */
import java.io.*;
import sun.security.util.DerValue;
public class DerValueWitupTest_P7874ea918173 {
    public static void main(String[] args) throws Exception {
        testValidUTF8String();
        testInvalidTagThrowsIOException();
    }
    private static void testValidUTF8String() throws Exception {
        byte[] utf8Bytes = "teste".getBytes("UTF8");
        DerValue dv = new DerValue((byte)0x0C, utf8Bytes); // tag UTF8String
        String result = dv.getUTF8String();
        if (!"teste".equals(result)) {
            throw new AssertionError("Expected 'teste', got: " + result);
        }
    }
    private static void testInvalidTagThrowsIOException() throws Exception {
        byte[] data = {0x05, 0x00}; // NULL tag
        DerValue dv = new DerValue(data);
        try {
            dv.getUTF8String();
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!e.getMessage().contains("not UTF-8")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
