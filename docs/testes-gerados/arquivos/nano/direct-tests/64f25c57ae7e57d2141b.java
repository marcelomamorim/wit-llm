/* @test    @summary Test getParameterCount method of Executable class    @run main ExecutableWitupTest */

import java.lang.reflect.Executable;

public class ExecutableWitupTest {

    public static void main(String[] args) {
        testGetParameterCount();
    }

    public static void testGetParameterCount() {
        class DummyExecutable extends Executable {
            @Override
            byte[] getAnnotationBytes() { return null; }
            @Override
            boolean hasGenericInformation() { return false; }
            @Override
            ConstructorRepository getGenericInfo() { return null; }
        }
        
        DummyExecutable de = new DummyExecutable();
        int count = de.getParameterCount();
        if (count != 0) {
            throw new AssertionError("Expected getParameterCount() to throw AbstractMethodError, but got: " + count);
        }
    }
}
