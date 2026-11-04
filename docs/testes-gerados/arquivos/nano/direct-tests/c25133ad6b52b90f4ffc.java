/* @test    @summary Test for visitProvide method in Printer class    @run main PrinterWitupTest */

import java.lang.reflect.Method;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitProvideMethod();
    }

    public static void testVisitProvideMethod() {
        try {
            Class<?> printerClass = Class.forName("jdk.internal.org.objectweb.asm.util.Printer");
            Method visitProvideMethod = null;
            for (Method m : printerClass.getDeclaredMethods()) {
                if (m.getName().equals("visitProvide")) {
                    visitProvideMethod = m;
                    break;
                }
            }
            if (visitProvideMethod == null) {
                throw new AssertionError("Method visitProvide not found");
            }
            Object printerInstance = java.lang.reflect.Proxy.newProxyInstance(
                printerClass.getClassLoader(),
                new Class<?>[] { (Class<?>) printerClass },
                (proxy, method, args) -> {
                    if (method.equals(visitProvideMethod)) {
                        return null; // simulate override
                    }
                    throw new UnsupportedOperationException("Unexpected method call: " + method.getName());
                }
            );
            // Invoke visitProvide with sample arguments
            visitProvideMethod.invoke(printerInstance, "com.example.Service", new String[] {"Provider1", "Provider2"});
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Class not found: jdk.internal.org.objectweb.asm.util.Printer", e);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Reflection error during test execution", e);
        }
    }
}
