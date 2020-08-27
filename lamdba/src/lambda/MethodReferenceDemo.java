package lambda;

import java.util.function.*;

public class MethodReferenceDemo {

    public static void main(String[] args) {
        Consumer<String> consumer = s -> System.out.println(s);

        //方法引用
        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("接受的数据");

        //静态方法的方法引用
        Consumer<Dog> dogConsumer = Dog::bark;
        Dog dog = new Dog();
        dogConsumer.accept(dog);

        //非静态方法，使用对象实例引用
        //Function<Integer, Integer> function = dog::eat;
        //IntUnaryOperator
        UnaryOperator<Integer> unaryOperator = dog::eat;
        System.out.println("还剩下" + unaryOperator.apply(2) + "斤");

        //使用类名来方法引用
        BiFunction<Dog, Integer, Integer> biFunction = Dog::eat;
        System.out.println("还剩下" + biFunction.apply(dog, 2) + "斤");

        //构造函数方法引用
        //无构造函数 - 默认空构造 --> 没有输入，只有输出
        Supplier<Dog> supplier = Dog::new;
        System.out.println("创建了新的对象：" + supplier.get());

        //带参数的构造函数
        Function<String, Dog> function = Dog::new;
        System.out.println("创建了新的对象：" + function.apply("吉娃娃"));
    }
}

class Dog {

    private String name = "哈士奇";

    private int food = 10;

    public Dog () {

    }

    public Dog (String name) {
        this.name = name;
    }


    public static void bark(Dog dog) {
        System.out.println(dog + "叫了");
    }

    public int eat(int num) {
        System.out.println("吃了" + num + "斤狗粮");
        this.food -= num;
        return this.food;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
