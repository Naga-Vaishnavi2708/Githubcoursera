class FizzBuzz {
    private int n;
    private int current = 1;
    
    public FizzBuzz(int n) {
        this.n = n;
    }
    
    public synchronized void fizz(Runnable printFizz) throws InterruptedException {
        while (current <= n) {
            if (current % 3 == 0 && current % 5 != 0) {
                printFizz.run();
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
    
    public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
        while (current <= n) {
            if (current % 5 == 0 && current % 3 != 0) {
                printBuzz.run();
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
    
    public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (current <= n) {
            if (current % 3 == 0 && current % 5 == 0) {
                printFizzBuzz.run();
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
    
    public synchronized void number(Runnable printNumber) throws InterruptedException {
        while (current <= n) {
            if (current % 3 != 0 && current % 5 != 0) {
                int num = current;
                printNumber.run();
                current++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
    
    public static void main(String[] args) {
        int n = 15;
        FizzBuzz fizzBuzz = new FizzBuzz(n);
        
        Runnable printFizz = () -> System.out.print("fizz ");
        Runnable printBuzz = () -> System.out.print("buzz ");
        Runnable printFizzBuzz = () -> System.out.print("fizzbuzz ");
        Runnable printNumber = () -> System.out.print(Thread.currentThread().getName() + " ");
        
        Thread t1 = new Thread(() -> {
            try { fizzBuzz.fizz(printFizz); } catch (InterruptedException e) { }
        });
        
        Thread t2 = new Thread(() -> {
            try { fizzBuzz.buzz(printBuzz); } catch (InterruptedException e) { }
        });
        
        Thread t3 = new Thread(() -> {
            try { fizzBuzz.fizzbuzz(printFizzBuzz); } catch (InterruptedException e) { }
        });
        
        Thread t4 = new Thread(() -> {
            try { fizzBuzz.number(() -> System.out.print(fizzBuzz.current + " ")); } catch (InterruptedException e) { }
        });
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
