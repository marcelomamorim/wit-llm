/* @test    @summary Test PSSParameterSpec private constructor throws RuntimeException    @run main PSSParameterSpecWitupTest */

import java.security.spec.PSSParameterSpec;

public class PSSParameterSpecWitupTest {

    public static void main(String[] args) {
        boolean exceptionThrown = false;
        try {
            new PSSParameterSpec();
        } catch (RuntimeException e) {
            exceptionThrown = true;
            if (!"default constructor not allowed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected RuntimeException was not thrown");
        }
    }
}
