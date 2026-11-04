/*
 * @test
 * @summary Public-API check that MethodType interning (via the internal weak intern set, reached through get) returns canonical instances
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {
    public static void main(String[] args) {
        MethodType a = MethodType.methodType(String.class, int.class, Object.class);
        MethodType b = MethodType.methodType(String.class, int.class, Object.class);
        if (a != b) {
            throw new RuntimeException("expected interned instance while first reference is alive");
        }
        if (!a.equals(b)) {
            throw new RuntimeException("equal method types expected");
        }
        MethodType c = MethodType.methodType(String.class, int.class);
        if (a == c) {
            throw new RuntimeException("different method types must not be the same instance");
        }
        if (a.parameterCount() != 2) {
            throw new RuntimeException("unexpected parameter count");
        }
        if (a.returnType() != String.class) {
            throw new RuntimeException("unexpected return type");
        }
    }
}
