package lambda;

import java.util.function.*;

/**
 *      接口            输入参数     返回类型      说明
 *
 *  Predicate<T>          T        boolean   断言
 *  Consumer<T>           T          /       消费一个数据
 *  Function<T, R>        T          R       输入T输出R的函数
 *  Supplier<T>           /          T       提供一个数据
 *  UnaryOperator<T>      T          T       一元函数（输入输出类型相同）
 *  BiFunction<T, U, R> (T, U)       R       2个输入的函数
 *  BinaryOperator<T>   (T, T)       T       二元函数（输入输出类型相同）
 */
public class FunctionDemo {

    public static void main(String[] args) {
        //断言函数接口
        Predicate<Integer> predicate = i -> i > 0;
        IntPredicate intPredicate = i -> i > 0;
        System.out.println("Predicate: " + predicate.test(-9));

        //消费函数接口
        //IntConsumer
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("Consumer: 输入的数据");

        //二元函数
        //IntBinaryOperator
        BinaryOperator<Integer> binaryOperator = (i, j) -> i + j;
        System.out.println("BinaryOperator: " + binaryOperator.apply(5,7));

        //两个输入的函数
        BiFunction<Integer, Integer, String> biFunction = (i, j) -> String.valueOf(i * j);
        System.out.println("BiFunction: " + biFunction.apply(8, 4));
    }
}
