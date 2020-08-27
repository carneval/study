package lambda;

/**
 * @author chenjingyi
 */
public class TypeDemo {

    public static void main(String[] args) {
        //变量类型定义
        IMath lambda = (x, y) -> x + y;
        //数组
        IMath[] lambdas = { (x, y) -> x + y};
        //强转
        Object lambda2 = (IMath) (x, y) -> x + y;
        //通过返回类型
        IMath lambda3 = createLambda();

        TypeDemo demo = new TypeDemo();
        demo.test((x, y) -> x + y);
    }

    public void test(IMath math) {

    }

    public static IMath createLambda() {
        return (x, y) -> x + y;
    }
}

@FunctionalInterface
interface IMath {
    int add(int x, int y);
}
