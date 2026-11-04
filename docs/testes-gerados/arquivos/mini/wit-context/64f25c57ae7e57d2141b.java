/*
 * @test
 * @summary Test Executable.getParameterCount() throws AbstractMethodError as expected
 * @run main java.lang.reflect.ExecutableWitupTest
 */

public class ExecutableWitupTest {
    public static void main(String[] args) {
        // Create anonymous subclass to call getParameterCount()
        Executable exec = new Executable() {
            @Override
            byte[] getAnnotationBytes() { return null; }
            @Override
            boolean hasGenericInformation() { return false; }
            @Override
            ConstructorRepository getGenericInfo() { return null; }
            @Override
            public Class<?> getDeclaringClass() { return Object.class; }
            @Override
            public int getModifiers() { return 0; }
            @Override
            public String getName() { return "test"; }
            @Override
            public java.lang.reflect.TypeVariable<?>[] getTypeParameters() { return new java.lang.reflect.TypeVariable<?>[0]; }
            @Override
            public Class<?>[] getParameterTypes() { return new Class<?>[0]; }
            @Override
            public Class<?>[] getExceptionTypes() { return new Class<?>[0]; }
            @Override
            public Object getDefaultValue() { return null; }
        };

        try {
            exec.getParameterCount();
            throw new RuntimeException("Expected AbstractMethodError not thrown");
        } catch (AbstractMethodError e) {
            // Expected exception
        }
    }
}
