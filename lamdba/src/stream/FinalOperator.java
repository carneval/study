package stream;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *          Stream流编程 - 终止操作
 *                  相关方法
 * 非短路操作    forEach/forEachOrdered
 *             collect/toArray
 *             reduce
 *             min/max/count
 * 短路操作     findFirst/findAny
 *             allMatch/anyMatch/noneMatch
 */
public class FinalOperator {

    public static void main(String[] args) {
        String str = "my name is haruka";

        //使用并行流
        str.chars().parallel()
          .forEach(i -> System.out.println((char)i));
        System.err.println("------------------------------");
        //forEachOrdered 保证顺序
        str.chars().parallel()
          .forEachOrdered(i -> System.out.println((char)i));

        //收集到list
        List<String> list = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(list);
        System.err.println("------------------------------");

        //使用reduce拼接字符串
        Optional<String> letters = Stream.of(str.split(" "))
          .reduce((s1, s2) -> s1 + " | " + s2);
        System.out.println(letters.orElse(""));
        System.err.println("------------------------------");

        //带初始化值的reduce
        String reduce = Stream.of(str.split(" "))
          .reduce("", (s1, s2) -> s1 + " | " + s2);
        System.out.println(reduce);
        System.err.println("------------------------------");

        //计算所有单词总长度
        Integer length = Stream.of(str.split(" ")).map(s -> s.length())
          .reduce(0, (s1, s2) -> s1 + s2);
        System.out.println(length);
        System.err.println("------------------------------");

        //最大值
        Optional<String> max = Stream.of(str.split(" ")).max((s1, s2) -> s1.length() - s2.length());
        System.out.println(max.get());
        System.err.println("------------------------------");

        //使用findFirst短路操作
        OptionalInt first = new Random().ints().findFirst();
        System.out.println(first.getAsInt());
        System.err.println("------------------------------");

    }
}
