/* @test    @summary Test MethodHandle.asVarargsCollector with valid class type    @run main MethodHandleWitupTest */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleWitupTest {

    public static void main(String[] args) {
        testAsVarargsCollectorWithValidClass();
        testAsVarargsCollectorWithNull();
    }

    static void testAsVarargsCollectorWithValidClass() {
        try {
            MethodHandle mh = MethodHandles.lookup().findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class));
            MethodHandle collector = mh.asVarargsCollector(String[].class);
            if (collector == null) {
                throw new AssertionError("asVarargsCollector returned null for valid class");
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static void testAsVarargsCollectorWithNull() {
        try {
            MethodHandle mh = MethodHandles.lookup().findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class));
            mh.asVarargsCollector(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // expected
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
