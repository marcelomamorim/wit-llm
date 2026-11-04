/*
 * @test
 * @summary Test that the private default constructor of PSSParameterSpec throws RuntimeException
 * @run main PSSParameterSpecWitupTest
 */

public class PSSParameterSpecWitupTest {
    public static void main(String[] args) {
        try {
            java.lang.reflect.Constructor<java.security.spec.PSSParameterSpec> ctor =
                java.security.spec.PSSParameterSpec.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            try {
                ctor.newInstance();
                throw new RuntimeException("Expected RuntimeException not thrown");
            } catch (java.lang.reflect.InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof RuntimeException)) {
                    throw new RuntimeException("Unexpected exception cause", cause);
                }
                if (!"default constructor not allowed".equals(cause.getMessage())) {
                    throw new RuntimeException("Unexpected exception message: " + cause.getMessage());
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Reflection failure", e);
        }
    }
}
