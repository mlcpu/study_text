
import java.util.*;

class Test {
    volatile static int num = 0;

    public static void main(String[] args) {
        System.out.println("hello https://tool.lu/");
        Vector vector = new Vector();
        new Thread(new Producer(vector)).start();
        new Thread(new Customer(vector)).start();
    }

    private static final int maxSize = 20;

    static class Producer implements Runnable {
        private final Vector vector;

        Producer(Vector vector) {
            this.vector = vector;
        }

        public void run() {
            while (true) {
                synchronized (vector) {
                    while (vector.size() == maxSize) {
                        System.out.println("vector is full");
                        try {
                            vector.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (vector) {
                    vector.add(vector.size());
                    System.out.println("vector add：" + vector.size());
                    vector.notifyAll();
                    num++;
                    if (num >= 200) return;
                }
            }
        }
    }

    static class Customer implements Runnable {
        private final Vector vector;

        Customer(Vector vector) {
            this.vector = vector;
        }

        public void run() {
            while (true) {
                synchronized (vector) {
                    while (vector.size() == 0) {
                        System.out.println("vector is enpty()");
                        try {
                            vector.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (vector) {
                    vector.remove(0);
                    System.out.println("vector remove：" + vector.size());
                    vector.notifyAll();
                    num++;
                    if (num >= 200) return;
                }
            }
        }
    }
}