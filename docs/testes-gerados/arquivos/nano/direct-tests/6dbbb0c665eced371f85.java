/*
 * @test
 * @summary Tests for MethodHandles.explicitCastArguments to verify correct behavior and regressions.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testExplicitCastArgumentsSameType();
        testExplicitCastArgumentsConvertibleType();
        testExplicitCastArgumentsDifferentType();
    }

    static void testExplicitCastArgumentsSameType() {
        MethodType type = MethodType.methodType(String.class, int.class);
        MethodHandle mh = MethodHandles.constant(String.class, "test");
        MethodHandle result = MethodHandles.explicitCastArguments(mh, type);
        assert result != null : "Result should not be null";
        assert result.type().equals(type) : "Type should match the new type";
    }

    static void testExplicitCastArgumentsConvertibleType() {
        MethodType originalType = MethodType.methodType(Object.class, int.class);
        MethodHandle mh = MethodHandles.constant(Object.class, "test");
        MethodType newType = MethodType.methodType(String.class, int.class);
        MethodHandle result = MethodHandles.explicitCastArguments(mh, newType);
        assert result != null : "Result should not be null";
        assert result.type().equals(newType) : "Type should be converted to new type";
    }

    static void testExplicitCastArgumentsDifferentType() {
        MethodType originalType = MethodType.methodType(Integer.class, int.class);
        MethodHandle mh = MethodHandles.constant(Integer.class, 42);
        MethodType newType = MethodType.methodType(String.class, int.class);
        MethodHandle result = MethodHandles.explicitCastArguments(mh, newType);
        assert result != null : "Result should not be null";
        assert result.type().equals(newType) : "Type should be converted to new type";
    }
}
