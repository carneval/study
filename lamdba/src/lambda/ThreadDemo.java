package lambda;

public class ThreadDemo {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ok");
            }
        }).start();

        new Thread (() -> System.out.println("ok")).start();
    }
}
