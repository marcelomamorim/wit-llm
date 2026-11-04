/* @test    @summary Test for StringUTF16 forEachRemaining method behavior    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithEmpty();
    }

    static void testForEachRemainingWithValidConsumer() {
        StringUTF16 utf16 = new StringUTF16();
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        // Using reflection to invoke forEachRemaining
        try {
            java.lang.reflect.Method method = StringUTF16.class.getDeclaredMethod("forEachRemaining", Consumer.class);
            method.setAccessible(true);
            // Create an instance with index and fence set to simulate iteration
            Object instance = java.lang.reflect.Proxy.newProxyInstance(
                StringUTF16.class.getClassLoader(),
                new Class<?>[]{},
                (proxy, methodProxy, argsProxy) -> null
            );
            // Since the method is not static, create an actual instance
            // but StringUTF16 is package-private and has no public constructor, so we simulate
            // Instead, directly call the method via reflection on a dummy object
            // For simplicity, we invoke the method directly with a dummy object
            // Note: In real scenario, we'd need an actual StringUTF16 object, but it's package-private
            // Here, we assume the method is static for test purposes, or we test the method logic directly
            // So, for demonstration, we invoke the method directly
            // But since it's an instance method, we need an instance; however, StringUTF16 is package-private
            // For the purpose of this test, we simulate the call by invoking the method directly
            // with a dummy object
            // Alternatively, test the method logic directly
            // So, we call the method directly
            // But the method is not static, so we need an instance
            // Since we can't instantiate StringUTF16 directly, we skip reflection and call the method directly
            // as a workaround for this test
            // Create a dummy subclass to access the method
            class Dummy extends StringUTF16 {
                void test() {
                    this.forEachRemaining(consumer);
                }
            }
            Dummy dummy = new Dummy();
            dummy.index = 0;
            dummy.fence = 1;
            dummy.next = () -> "test";
            dummy.forEachRemaining(consumer);
            if (!sb.toString().equals("test")) {
                throw new AssertionError("Expected 'test', got: " + sb.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Reflection test failed", e);
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        try {
            StringUTF16 utf16 = new StringUTF16();
            // Call with null should throw NullPointerException
            utf16.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testForEachRemainingWithEmpty() {
        StringUTF16 utf16 = new StringUTF16();
        utf16.index = utf16.fence; // simulate end of iteration
        final StringBuilder sb = new StringBuilder();
        utf16.forEachRemaining(s -> sb.append(s));
        if (sb.length() != 0) {
            throw new AssertionError("Expected empty iteration, got: " + sb.toString());
        }
    }
}
