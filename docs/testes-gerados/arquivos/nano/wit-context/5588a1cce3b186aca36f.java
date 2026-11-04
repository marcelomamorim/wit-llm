/*
 * @test
 * @summary Test for getFloat method in UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatWithInvalidObject();
        testGetFloatWithValidObject();
    }

    static void testGetFloatWithInvalidObject() throws Exception {
        Field field = null; // não é necessário criar um campo real, apenas simular o método
        // Criar uma instância do objeto que simula o método
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        Object invalidObj = new Object();
        try {
            accessor.getFloat(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid object");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException");
        }
    }

    static void testGetFloatWithValidObject() throws Exception {
        // Para testar com um objeto válido, é necessário criar uma classe que contenha um campo char
        class Dummy {
            public char c = 'a';
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("c");
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        Object obj = dummy;
        try {
            float result = accessor.getFloat(obj);
            if (result == (float) dummy.c) {
                System.out.println("PASS: getFloat returned expected value");
            } else {
                System.out.println("FAIL: getFloat returned unexpected value: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
