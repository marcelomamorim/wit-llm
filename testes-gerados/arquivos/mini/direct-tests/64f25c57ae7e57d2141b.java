/*
 * @test
 * @summary Test that Executable.getParameterCount() throws AbstractMethodError
 * @run main ExecutableWitupTest
 */

public class ExecutableWitupTest {
    static class DummyExecutable extends java.lang.reflect.Executable {
        DummyExecutable() { super(); }
        @Override
        byte[] getAnnotationBytes() { return null; }
        @Override
        boolean hasGenericInformation() { return false; }
        @Override
        java.lang.reflect.ConstructorRepository getGenericInfo() { return null; }
        @Override
        public int getModifiers() { return 0; }
        @Override
        public Class<?> getDeclaringClass() { return DummyExecutable.class; }
        @Override
        public String getName() { return "dummy"; }
        @Override
        public java.lang.Class<?>[] getParameterTypes() { return new Class<?>[0]; }
        @Override
        public java.lang.Class<?>[] getExceptionTypes() { return new Class<?>[0]; }
        @Override
        public java.lang.annotation.Annotation[][] getParameterAnnotations() { return new java.lang.annotation.Annotation[0][]; }
        @Override
        public java.lang.reflect.TypeVariable<?>[] getTypeParameters() { return new java.lang.reflect.TypeVariable<?>[0]; }
        @Override
        public java.lang.reflect.Type[] getGenericParameterTypes() { return new java.lang.reflect.Type[0]; }
        @Override
        public java.lang.reflect.Type[] getGenericExceptionTypes() { return new java.lang.reflect.Type[0]; }
        @Override
        public Object invoke(Object obj, Object... args) throws Exception { throw new UnsupportedOperationException(); }
    }

    public static void main(String[] args) {
        DummyExecutable exec = new DummyExecutable();
        try {
            exec.getParameterCount();
            throw new RuntimeException("Expected AbstractMethodError not thrown");
        } catch (AbstractMethodError e) {
            // expected
        }
    }
}
