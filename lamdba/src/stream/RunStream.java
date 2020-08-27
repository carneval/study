package stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 * @author chenjingyi
 *
 * 验证Stream运行机制
 * 1、所有操作是链式调用，一个元素只迭代一次
 * 2、每一个中间操作返回一个新的流
 *    流里面有一个属性sourceStage执行同一个地方，就是Head
 * 3、Head -> nextStage -> nextStage -> ... -> null
 * 4、有状态操作会把无状态操作截断，单独处理
 * 5、并行环境下，有状态的中间操作不一定能够并行操作
 * 6、parallel / sequential 这两个也是中间操作（返回Stream）
 *    但是他们不创建流，只修改Head的并行标准
 */
public class RunStream {

    public static void main(String[] args) {
        Random random = new Random();

        //随即产生数据
        Stream.generate(() -> random.nextInt())
          //产生500个（无限流需要短路操作）
          .limit(5)
          //第一个无状态操作
          .peek( s -> System.out.println("peek: " + s))
          //第二个无状态操作
          .filter( s -> {
              System.out.println("filter: " + s);
              return s > 1000000;
          })
          //有状态操作
          .sorted((i1, i2) -> {
              System.out.println("sort: " + i1  + ", " + i2);
              return i1.compareTo(i2);
          })
          //又一个无状态操作
          .peek( s -> System.out.println("peek2: " + s))
          //终止操作
          .count();
    }
}
