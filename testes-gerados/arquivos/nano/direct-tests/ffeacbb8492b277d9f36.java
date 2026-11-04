/* @test    @summary Test for ConstantBootstraps.enumConstant method to ensure correct enum constant retrieval and exception handling.    @run main ConstantBootstrapsWitupTest */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) {
        testValidEnumConstant();
        testNullLookup();
        testNullName();
        testNullType();
        testInvalidType();
    }

    static void testValidEnumConstant() {
        try {
            Class<Thread.State> enumType = Thread.State.class;
            String enumName = "RUNNABLE";
            Thread.State result = java.lang.invoke.ConstantBootstraps.enumConstant(
                MethodHandles.lookup(), enumName, enumType);
            assert result == Thread.State.RUNNABLE : "Expected RUNNABLE, got " + result;
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testValidEnumConstant", t);
        }
    }

    static void testNullLookup() {
        try {
            java.lang.invoke.ConstantBootstraps.enumConstant(null, "RUNNABLE", Thread.State.class);
            throw new AssertionError("Expected NullPointerException for null lookup");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testNullLookup", t);
        }
    }

    static void testNullName() {
        try {
            java.lang.invoke.ConstantBootstraps.enumConstant(MethodHandles.lookup(), null, Thread.State.class);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testNullName", t);
        }
    }

    static void testNullType() {
        try {
            java.lang.invoke.ConstantBootstraps.enumConstant(MethodHandles.lookup(), "RUNNABLE", null);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testNullType", t);
        }
    }

    static void testInvalidType() {
        try {
            java.lang.invoke.ConstantBootstraps.enumConstant(MethodHandles.lookup(), "RUNNABLE", String.class);
            throw new AssertionError("Expected IllegalArgumentException for invalid type");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in testInvalidType", t);
        }
    }
}
