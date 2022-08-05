# 线程的正确例用
## 正确处理中断的方法
```java
    Thread#interrupt();
```
响应中断的方法
```java
Object.wait()/wait(long)/wait(long, int)
Thread.sleep(long), Thread.sleep(long, int)
Thread.join()/join(long)/join(long, int)
java.util.concurrent.BlockingQueue.take()/put()
java.util.concurrent.locks.Lock.lockInterruptibly()
java.util.concurrent.CountDownLatch.await()
java.util.concurrent.CyclicBarrier.await()
java.util.concurrent.Exchanger.exchange(V)
java.nio.channels.InterruptibleChannel相关方法
java.nio.channels.Selector的相关方法
```

## 错误处理线程的方法
* stop()
* suspend()
* resume()


## 枚举法实现单例的好处
* 写法简单
* 线程安全有保障，通过反编译查看后是enum类中的一个静态成员类，类似于静态内部类的实现方式，实现了懒加载并且由jvm保证线程安全
* 避免反序列化破坏单例


## 线程运行状态有：
* New
* Runnable
* Blocked 进入同步代码块时且未拿到锁时的状态
* Waiting
* Timed Waiting
* Terminated

### 为什么多线程会带来性能问题
* 调度：上下文切换
    * 什么是上下文？运行时的临时变量等，cpu会规定最小的切换上下文的时间
    * 缓存开销，运行时cpu读取的缓存行们
    * 何时会导致密集的上下文切换？抢锁，io

* 协作：内存同步


## 线程核心基础 
