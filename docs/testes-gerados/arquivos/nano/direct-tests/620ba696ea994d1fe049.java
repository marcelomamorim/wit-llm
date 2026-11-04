/*
 * @test
 * @summary Test MethodHandles.empty(MethodType) for various MethodType inputs.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) {
        testEmptyWithVoidReturn();
        testEmptyWithIntReturn();
        testEmptyWithObjectReturn();
        testEmptyWithArrayParameters();
        testEmptyWithNoParameters();
    }

    static void testEmptyWithVoidReturn() {
        MethodType type = MethodType.methodType(void.class);
        MethodHandle handle = MethodHandles.empty(type);
        assert handle != null : "Handle should not be null for void return type";
        assert handle.type().equals(type) : "Handle type mismatch";
    }

    static void testEmptyWithIntReturn() {
        MethodType type = MethodType.methodType(int.class);
        MethodHandle handle = MethodHandles.empty(type);
        assert handle != null : "Handle should not be null for int return type";
        assert handle.type().equals(type) : "Handle type mismatch";
    }

    static void testEmptyWithObjectReturn() {
        MethodType type = MethodType.methodType(Object.class);
        MethodHandle handle = MethodHandles.empty(type);
        assert handle != null : "Handle should not be null for Object return type";
        assert handle.type().equals(type) : "Handle type mismatch";
    }

    static void testEmptyWithArrayParameters() {
        MethodType type = MethodType.methodType(void.class, int.class, String.class);
        MethodHandle handle = MethodHandles.empty(type);
        assert handle != null : "Handle should not be null for parameterized type";
        assert handle.type().equals(type) : "Handle type mismatch";
    }

    static void testEmptyWithNoParameters() {
        MethodType type = MethodType.methodType(void.class);
        MethodHandle handle = MethodHandles.empty(type);
        assert handle != null : "Handle should not be null for no parameters";
        assert handle.type().equals(type) : "Handle type mismatch";
    }
}
