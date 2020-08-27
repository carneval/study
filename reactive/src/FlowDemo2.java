import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo2 {

    public static void main(String[] args) throws Exception{
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<Integer>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("接收到数据：" + item);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("处理完了！");
            }
        };

        publisher.subscribe(subscriber);

        publisher.submit(111);
        publisher.submit(222);
        publisher.submit(333);

        publisher.close();

        Thread.currentThread().join(1000);
    }
}


