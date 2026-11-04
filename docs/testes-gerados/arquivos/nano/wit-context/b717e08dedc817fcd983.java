/* @test    @summary Test for PropertyResourceBundleCharset.newEncoder() method.
 * @run main PropertyResourceBundleCharsetWitupTest
 */

import sun.util.PropertyResourceBundleCharset;
import java.nio.charset.CharsetEncoder;

public class PropertyResourceBundleCharsetWitupTest {

    public static void main(String[] args) {
        testNewEncoderThrowsException();
    }

    public static void testNewEncoderThrowsException() {
        PropertyResourceBundleCharset charset = new PropertyResourceBundleCharset(false);
        try {
            CharsetEncoder encoder = charset.newEncoder();
            // Se não lançar exceção, o teste falha
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, teste passa
            System.out.println("PASS: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
