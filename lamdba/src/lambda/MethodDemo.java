package lambda;

import stream.pojo.Gender;
import stream.pojo.Grade;
import stream.pojo.Student;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author chenjingyi
 */
public class MethodDemo {

    /**
     * 指定入参类型，返回Boolean值
     */
    public void predicateTest() {
        Predicate<String> predicate = s -> s.length() > 0;

        boolean foo0 = predicate.test("foo");
        //negate() 相当于否定
        boolean foo1 = predicate.negate().test("foo");

        Predicate<Boolean> notNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
    }

    /**
     * 为其提供一个原料，它给生产一个最终的产品
     */
    public void functionTest() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        Function<String, String> upperCase = backToString.andThen(String::toUpperCase);
        String apply = backToString.apply("haruka");
        System.out.println(apply);
    }

    /**
     * 不接受入参，直接生产一个指定的结果 --> 生产者模式
     */
    public void supplierTest() {
        Supplier<Student> studentSupplier = Student::new;
        studentSupplier.get();
    }

    /**
     * 需要入参来消费
     */
    public void consumerTest() {
        Consumer<Student> stu = new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                System.out.println("Hello, " + student.getName());
            }
        };
        Consumer<Student> stu2 = s -> System.out.println("Hello, " + s.getName());
        stu2.accept(new Student("haruka", 20, Gender.FEMALE, Grade.TWO));
    }


}
