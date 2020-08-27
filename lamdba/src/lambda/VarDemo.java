package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class VarDemo {

    public static void main(String[] args) {
        //final 或者不能改变
        String str = "sss";
        Consumer<String> consumer = s -> System.out.println(s + str);
        consumer.accept("1211");

        List<String> list = new ArrayList<>();
        Consumer<String> consumer1 = s -> System.out.println(s + list);
        consumer.accept("1211");
    }
}
