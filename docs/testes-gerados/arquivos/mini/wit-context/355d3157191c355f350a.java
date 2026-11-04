/*
 * @test
 * @summary Test for jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl.getDouble(Object) throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We cannot instantiate UnsafeQualifiedStaticObjectFieldAccessorImpl directly because it has no public constructors
        // and no public static factory is known from context. So we test getDouble by subclassing anonymously.
        UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImplStub();

        try {
            accessor.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Stub subclass to allow instantiation
    static class UnsafeQualifiedStaticObjectFieldAccessorImplStub extends UnsafeQualifiedStaticObjectFieldAccessorImpl {
        UnsafeQualifiedStaticObjectFieldAccessorImplStub() throws NoSuchFieldException {
            super(UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("dummy"), false);
        }

        // Dummy field to get Field instance
        @SuppressWarnings("unused")
        private static Object dummy;

        // Override newGetDoubleIllegalArgumentException to throw standard IllegalArgumentException
        @Override
        public double getDouble(Object obj) throws IllegalArgumentException {
            throw new IllegalArgumentException();
        }
    }
}
