/* @test    @summary Test SortedOps.begin with valid and invalid sizes    @run main SortedOpsWitupTest */

import java.util.ArrayList;
import java.util.stream.SortedOps;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testBeginWithValidSize();
        testBeginWithZeroSize();
        testBeginWithLargeSize();
        testBeginWithNegativeSize();
    }

    static void testBeginWithValidSize() {
        SortedOps sortedOps = new SortedOps();
        try {
            // Using reflection to invoke the method since it's package-private
            java.lang.reflect.Method method = SortedOps.class.getDeclaredMethod("begin", long.class);
            method.setAccessible(true);
            Object instance = java.lang.reflect.Proxy.newProxyInstance(
                SortedOps.class.getClassLoader(),
                new Class<?>[] {SortedOps.class},
                (proxy, m, args) -> {
                    if (m.getName().equals("begin")) {
                        return m.invoke(proxy, args);
                    }
                    return null;
                });
            // Since method is static, invoke with null
            method.invoke(null, 10L);
            // No exception expected
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke begin with valid size", e);
        }
    }

    static void testBeginWithZeroSize() {
        try {
            java.lang.reflect.Method method = SortedOps.class.getDeclaredMethod("begin", long.class);
            method.setAccessible(true);
            method.invoke(null, 0L);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke begin with zero size", e);
        }
    }

    static void testBeginWithLargeSize() {
        try {
            java.lang.reflect.Method method = SortedOps.class.getDeclaredMethod("begin", long.class);
            method.setAccessible(true);
            method.invoke(null, (long) Integer.MAX_VALUE + 1L);
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                // Expected exception
            } else {
                throw new RuntimeException("Unexpected exception for large size", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke begin with large size", e);
        }
    }

    static void testBeginWithNegativeSize() {
        try {
            java.lang.reflect.Method method = SortedOps.class.getDeclaredMethod("begin", long.class);
            method.setAccessible(true);
            method.invoke(null, -1L);
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                // Expected exception
            } else {
                throw new RuntimeException("Unexpected exception for negative size", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke begin with negative size", e);
        }
    }
}
