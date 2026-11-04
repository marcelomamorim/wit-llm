/*
 * @test
 * @summary Test MethodType.genericMethodType(int) basic behavior
 * @run main MethodTypeWitupTest
 */

public class MethodTypeWitupTest {
    public static void main(String[] args) {
        MethodTypeWitupTest test = new MethodTypeWitupTest();
        test.testGenericMethodTypeZero();
        test.testGenericMethodTypePositive();
    }

    // Test that genericMethodType(0) returns a non-null MethodType
    public void testGenericMethodTypeZero() {
        java.lang.invoke.MethodType mt = genericMethodType(0);
        if (mt == null) {
            throw new AssertionError("genericMethodType(0) returned null");
        }
    }

    // Test that genericMethodType with positive argument returns non-null MethodType
    public void testGenericMethodTypePositive() {
        java.lang.invoke.MethodType mt = genericMethodType(5);
        if (mt == null) {
            throw new AssertionError("genericMethodType(5) returned null");
        }
    }

    // Helper method to call the package-private genericMethodType(int) method
    // Since the method is package-private and in java.lang.invoke, and this test is in unnamed package,
    // we simulate the call by using the public factory MethodType.genericMethodType(int, boolean) if available,
    // but since only genericMethodType(int) is given, we create a dummy MethodType instance to call it.
    // However, as we have no access to MethodType internals, we call the public static MethodType.genericMethodType(int) if it exists.
    // But the provided method is instance method, so we create a dummy subclass to call it.

    private java.lang.invoke.MethodType genericMethodType(int objectArgCount) {
        // We create an anonymous subclass to call the instance method
        return new java.lang.invoke.MethodType() {
            MethodType genericMethodType(int count) {
                return super.genericMethodType(count);
            }
        }.genericMethodType(objectArgCount);
    }
}