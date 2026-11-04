/* @test    @summary Test for Base64.wrap(InputStream) null input handling    @run main Base64WitupTest */

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testWrapNullInput();
    }

    public static void testWrapNullInput() {
        try {
            InputStream result = Base64.getDecoder().wrap(null);
            // Se não lançar exceção, o teste falha
            System.err.println("Failed: Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Exceção esperada, teste passa
            System.out.println("Passed: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
