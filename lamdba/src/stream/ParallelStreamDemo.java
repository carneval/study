package stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author chenjingyi
 */
public class ParallelStreamDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        IntStream.range(1, 100).parallel().peek(ParallelStreamDemo::debug);

        // 实现 先并行 再串行
        // 多次调用 串行 和 并行 的函数  以最后一次调用为准
        IntStream.range(1, 100)
          //产生并行流
          .parallel().peek(ParallelStreamDemo::debug)
          //产生串行流
          .sequential().peek(ParallelStreamDemo::debug)
          .count();

        // 并行流使用的线程池： ForkJoinPool.commonPool
        // 默认的线程数是当前机器的cpu个数
        // 使用这个属性可以修改默认的线程数
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");

        // 使用自己的线程池，不使用默认的线程池，防止任务阻塞
        ForkJoinPool pool = new ForkJoinPool(20);
        pool.submit(() -> IntStream.range(1, 100)
          .parallel()
          .peek(ParallelStreamDemo::debug)
          .count());
        pool.shutdown();

        synchronized (pool) {
            pool.wait();
        }
        System.out.println("main end");
    }

    public static void debug(int i) {
        System.out.println("debug " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
