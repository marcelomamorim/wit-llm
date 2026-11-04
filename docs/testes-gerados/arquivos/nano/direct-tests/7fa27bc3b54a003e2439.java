/*
 * @test
 * @summary Test DoubleStream.generate with a simple DoubleSupplier
 * @run main DoubleStreamWitupTest
 */
import java.util.stream.DoubleStream;
import java.util.function.DoubleSupplier;

public class DoubleStreamWitupTest {

    public static void main(String[] args) {
        testGenerateWithConstant();
        testGenerateWithIncrement();
        testGenerateWithNegative();
    }

    static void testGenerateWithConstant() {
        DoubleSupplier supplier = new DoubleSupplier() {
            private double value = 42.0;
            @Override
            public double getAsDouble() {
                return value;
            }
        };
        DoubleStream stream = DoubleStream.generate(supplier);
        double[] result = stream.limit(3).toArray();
        assert result.length == 3 : "Expected length 3";
        for (double d : result) {
            assert d == 42.0 : "Expected 42.0";
        }
    }

    static void testGenerateWithIncrement() {
        DoubleSupplier supplier = new DoubleSupplier() {
            private double current = 0.0;
            @Override
            public double getAsDouble() {
                double val = current;
                current += 1.0;
                return val;
            }
        };
        DoubleStream stream = DoubleStream.generate(supplier);
        double[] result = stream.limit(5).toArray();
        assert result.length == 5 : "Expected length 5";
        for (int i = 0; i < 5; i++) {
            assert result[i] == i : "Expected " + i + " but got " + result[i];
        }
    }

    static void testGenerateWithNegative() {
        DoubleSupplier supplier = new DoubleSupplier() {
            private double current = -1.0;
            @Override
            public double getAsDouble() {
                double val = current;
                current -= 1.0;
                return val;
            }
        };
        DoubleStream stream = DoubleStream.generate(supplier);
        double[] result = stream.limit(4).toArray();
        assert result.length == 4 : "Expected length 4";
        for (int i = 0; i < 4; i++) {
            assert result[i] == -1.0 - i : "Expected " + (-1.0 - i) + " but got " + result[i];
        }
    }
}
