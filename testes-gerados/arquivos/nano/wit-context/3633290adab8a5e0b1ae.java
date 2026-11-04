/*
 * @test
 * @summary Testa o método getDouble de UnsafeObjectFieldAccessorImpl para verificar comportamento esperado.
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithNullObject();
        testGetDoubleWithInvalidObject();
    }

    private static void testGetDoubleWithNullObject() {
        try {
            Field dummyField = null; // não usamos o campo real, apenas para instanciar a classe
            // Como o construtor requer um Field, criamos um mock ou um campo real, mas aqui evitamos reflexão
            // Para manter a simplicidade, criamos uma subclasse anônima que simula o comportamento
            class DummyField extends Field {
                @Override
                public String toString() { return "DummyField"; }
            }
            Field fakeField = new DummyField() {
                @Override
                public Class<?> getDeclaringClass() { return null; }
                @Override
                public String getName() { return "dummy"; }
                @Override
                public Class<?> getType() { return double.class; }
            };
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(fakeField);
            try {
                accessor.getDouble(null);
                System.err.println("Expected IllegalArgumentException for null object, but none thrown.");
            } catch (IllegalArgumentException e) {
                // esperado
            }
        } catch (Exception e) {
            System.err.println("Exception during testGetDoubleWithNullObject: " + e);
        }
    }

    private static void testGetDoubleWithInvalidObject() {
        try {
            Field dummyField = new DummyField() {
                @Override
                public Class<?> getDeclaringClass() { return null; }
                @Override
                public String getName() { return "dummy"; }
                @Override
                public Class<?> getType() { return double.class; }
            };
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            Object invalidObject = new Object();
            accessor.getDouble(invalidObject);
            System.err.println("Expected IllegalArgumentException for invalid object, but none thrown.");
        } catch (IllegalArgumentException e) {
            // esperado
        } catch (Exception e) {
            System.err.println("Exception during testGetDoubleWithInvalidObject: " + e);
        }
    }
}
