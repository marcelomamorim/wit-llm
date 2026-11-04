/*
 * @test
 * @summary Test PSSParameterSpec private default constructor throws RuntimeException
 * @run main PSSParameterSpecWitupTest
 */

public class PSSParameterSpecWitupTest {
    public static void main(String[] args) {
        try {
            java.security.spec.PSSParameterSpec.class.getDeclaredConstructor().setAccessible(true);
            java.security.spec.PSSParameterSpec.class.getDeclaredConstructor().newInstance();
            throw new RuntimeException("Expected RuntimeException not thrown");
        } catch (ReflectiveOperationException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof RuntimeException)) {
                throw new RuntimeException("Unexpected exception cause", e);
            }
            if (!"default constructor not allowed".equals(cause.getMessage())) {
                throw new RuntimeException("Unexpected exception message", e);
            }
        }
    }
}
