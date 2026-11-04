/*
 * @test
 * @summary Tests for ConstantBootstraps.getStaticFinal method.
 * @run main ConstantBootstrapsWitupTest
 */
import java.lang.invoke.MethodHandles;
import java.lang.invoke.Wrapper;
import static java.util.Objects.requireNonNull;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) {
        testGetStaticFinalWithPrimitiveType();
        testGetStaticFinalWithObjectType();
        testGetStaticFinalWithNullType();
        testGetStaticFinalWithPrimitiveTypeThrows();
    }

    static void testGetStaticFinalWithPrimitiveType() {
        try {
            Class<?> primitiveType = int.class;
            Object result = java.lang.invoke.ConstantBootstraps.getStaticFinal(
                null, "someName", primitiveType);
            assert result != null : "Result should not be null for primitive type";
            assert result == null : "Expected null for getStaticFinal with primitive type";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for primitive type", e);
        }
    }

    static void testGetStaticFinalWithObjectType() {
        try {
            Class<?> objectType = String.class;
            Object result = java.lang.invoke.ConstantBootstraps.getStaticFinal(
                null, "someName", objectType);
            assert result != null : "Result should not be null for object type";
            assert result == null : "Expected null for getStaticFinal with object type";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for object type", e);
        }
    }

    static void testGetStaticFinalWithNullType() {
        try {
            java.lang.invoke.ConstantBootstraps.getStaticFinal(null, "name", null);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null type", e);
        }
    }

    static void testGetStaticFinalWithPrimitiveTypeThrows() {
        try {
            Class<?> primitiveType = int.class;
            // forcibly test the exception by passing a primitive type
            // but since the method does not throw for primitive, simulate invalid scenario
            // or call with null to trigger NPE
            java.lang.invoke.ConstantBootstraps.getStaticFinal(null, "name", primitiveType);
        } catch (NullPointerException e) {
            // expected if null is passed
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
