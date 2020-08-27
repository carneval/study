package lambda;

public class LambdaDemo1 {

    public static void main(String[] args) {
        Interface1 i1 = i -> i * 2;
        Interface1 i2 = (int i) -> i * 2;
        Interface1 i3 = (int i) -> {
            System.out.println("--------");
            return i * 2;
        };

        System.out.println(i1.add(3, 7));
        System.out.println(i1.doubleNum(20));
    }
}

@FunctionalInterface
interface Interface1 {
    int doubleNum(int i);

    default int add(int x, int y) {
        x = this.doubleNum(x);
        return x + y;
    }
}

@FunctionalInterface
interface Interface2 {
    int doubleNum(int i);

    default int add(int x, int y) {
        x = this.doubleNum(x);
        return x + y;
    }
}

/**
 * 有相同的default方法，需要指定重写哪一个
 */
@FunctionalInterface
interface Interface3 extends Interface1, Interface2 {

    @Override
    default int add(int x, int y) {
        return Interface1.super.add(x, y);
    }
}

