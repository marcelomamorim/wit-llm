/* @test    @summary Test for Executable.getParameterCount() method.
 * @run main ExecutableWitupTest
 */

import java.lang.reflect.Executable;

public class ExecutableWitupTest {

    public static void main(String[] args) {
        testGetParameterCountThrows();
    }

    public static void testGetParameterCountThrows() {
        Executable exec = new Executable() {
            @Override
            byte[] getAnnotationBytes() { return null; }
            @Override
            boolean hasGenericInformation() { return false; }
            @Override
            ConstructorRepository getGenericInfo() { return null; }
            @Override
            public int getParameterCount() {
                throw new AbstractMethodError();
            }
            @Override
            public Class<?> getDeclaringClass() { return null; }
            @Override
            public String getName() { return ""; }
            @Override
            public int getModifiers() { return 0; }
            @Override
            public java.lang.reflect.Type[] getGenericParameterTypes() { return null; }
            @Override
            public java.lang.reflect.Type[] getGenericExceptionTypes() { return null; }
            @Override
            public java.lang.reflect.Type getGenericReturnType() { return null; }
            @Override
            public java.lang.annotation.Annotation[][] getParameterAnnotations() { return null; }
            @Override
            public java.lang.reflect.Type[] getParameterTypes() { return null; }
            @Override
            public java.lang.reflect.Type[] getExceptionTypes() { return null; }
            @Override
            public java.lang.reflect.Type getReturnType() { return null; }
            @Override
            public boolean equals(Object obj) { return false; }
            @Override
            public int hashCode() { return 0; }
            @Override
            public String toString() { return ""; }
            @Override
            public String toGenericString() { return ""; }
            @Override
            public java.lang.annotation.Annotation[] getDeclaredAnnotations() { return new java.lang.annotation.Annotation[0]; }
            @Override
            public <T extends java.lang.annotation.Annotation> T getAnnotation(Class<T> annotationClass) { return null; }
        };
        try {
            int count = exec.getParameterCount();
            System.out.println("Unexpected success, got count: " + count);
        } catch (AbstractMethodError e) {
            System.out.println("Caught expected AbstractMethodError");
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception: " + t);
        }
    }
}
