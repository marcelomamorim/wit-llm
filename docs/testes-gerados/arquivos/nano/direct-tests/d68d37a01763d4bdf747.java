/* @test    @summary Test the private constructor of StackTraceElement via reflection to ensure instantiation is possible for internal use.    @run main StackTraceElementWitupTest */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class StackTraceElementWitupTest {

    public static void main(String[] args) {
        try {
            Class<StackTraceElement> clazz = StackTraceElement.class;
            Constructor<StackTraceElement> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            StackTraceElement ste = constructor.newInstance();
            if (ste != null) {
                System.out.println("Instantiation successful: " + ste);
            } else {
                System.out.println("Instantiation returned null");
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Failed to instantiate StackTraceElement: " + e);
            e.printStackTrace();
        }
    }
}
