/* @test
   @summary Testa o método get(String name) da classe sun.security.x509.BasicConstraintsExtension.
   @run main BasicConstraintsExtensionWitupTest_P947539111f6c
 */
import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;
public class BasicConstraintsExtensionWitupTest_P947539111f6c {
    public static void main(String[] args) throws Exception {
        testGetWithIsCa();
        testGetWithPathLen();
        testGetWithInvalidName();
    }
    private static void testGetWithIsCa() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        Object result = ext.get("is_ca");
        if (!(result instanceof Boolean)) {
            throw new AssertionError("Expected Boolean for 'is_ca', got: " + result.getClass());
        }
        if (!((Boolean) result).booleanValue()) {
            throw new AssertionError("Expected true for 'is_ca'");
        }
    }
    private static void testGetWithPathLen() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 5);
        Object result = ext.get("path_len");
        if (!(result instanceof Integer)) {
            throw new AssertionError("Expected Integer for 'path_len', got: " + result.getClass());
        }
        if (((Integer) result).intValue() != 5) {
            throw new AssertionError("Expected 5 for 'path_len', got: " + result);
        }
    }
    private static void testGetWithInvalidName() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        boolean exceptionThrown = false;
        try {
            ext.get("unknown_attribute");
            throw new AssertionError("Expected IOException for unknown attribute");
        } catch (IOException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("IOException was not thrown for unknown attribute");
        }
    }
}
