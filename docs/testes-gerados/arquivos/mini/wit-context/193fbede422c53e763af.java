/*
 * @test
 * @summary Test UnsafeQualifiedStaticLongFieldAccessorImpl.getShort throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We cannot instantiate UnsafeQualifiedStaticLongFieldAccessorImpl directly because
        // it has no public constructors and no public factory methods visible.
        // So we test getShort behavior via a subclass with minimal override to allow instantiation.

        class TestAccessor extends UnsafeQualifiedStaticLongFieldAccessorImpl {
            TestAccessor() throws NoSuchFieldException {
                super(TestAccessor.class.getDeclaredField("dummy"), false);
            }
            // dummy field to satisfy constructor
            @SuppressWarnings("unused")
            private static long dummy = 0L;
        }

        UnsafeQualifiedStaticLongFieldAccessorImpl accessor = new TestAccessor();

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
