package stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 *             Stream流编程 - 中间操作
 *                  相关方法
 *  无状态操作       map/mapToXxx
 *                 flatMap/flatMapXxx
 *                 filter
 *                 peek             入参是消费者
 *                 unordered
 *  有状态操作       distinct
 *                 sorted
 *                 limit/skip
 */
public class MiddleOperator {

    public static void main(String[] args) {
        String str = "my name is haruka";

        //打印每个单词的长度
        Stream.of(str.split(" ")).map(s -> s.length())
          .forEach(System.out::println);
        System.err.println("------------------------------");

        Stream.of(str.split(" "))
          .filter(s -> s.length() > 2)
          .map(s -> s.length())
          .forEach(System.out::println);
        System.err.println("------------------------------");

        //flatMap A -> B属性(是个集合)，最终得到所有的A元素里面的所有的B属性集合
        //intStream/longStream并不是Stream的子类，所以要进行装箱 boxed
        Stream.of(str.split(" "))
          .flatMap(s -> s.chars().boxed())
          .forEach(
            i -> System.out.println((char)i.intValue())
          );
        System.err.println("------------------------------");

        //peek 用于debug，是个中间操作
        Stream.of(str.split(" "))
          .peek(System.out::println)
          .forEach(System.out::println);
        System.err.println("------------------------------");

        //limit 主要用于无限流
        new Random().ints()
          .filter(i -> i > 100 && i < 1000)
          .limit(10)
          .forEach(System.out::println);
    }
}
