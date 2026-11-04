/* @test    @summary Test for Base64.wrap(OutputStream os) null argument handling    @run main Base64WitupTest */

import java.io.OutputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) {
        testWrapNullOutputStream();
    }

    public static void testWrapNullOutputStream() {
        try {
            Base64.getEncoder().wrap(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
