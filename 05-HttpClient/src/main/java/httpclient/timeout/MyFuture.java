package httpclient.timeout;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.http.util.Args;

public class MyFuture implements Future<String> {

	private final Lock lock;
    private final Condition condition;
    private volatile boolean cancelled;
    private volatile boolean completed;
    private String result;

    MyFuture(final Lock lock) {
        super();
        this.lock = lock;
        this.condition = lock.newCondition();
    }

    public boolean cancel(final boolean mayInterruptIfRunning) {
        this.lock.lock();
        try {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.cancelled = true;
            this.condition.signalAll();
            return true;
        } finally {
            this.lock.unlock();
        }
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isDone() {
        return this.completed;
    }

    public String get() throws InterruptedException, ExecutionException {
        try {
            return get(0, TimeUnit.MILLISECONDS);
        } catch (final TimeoutException ex) {
            throw new ExecutionException(ex);
        }
    }

    public String get(
            final long timeout,
            final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        Args.notNull(unit, "Time unit");
        this.lock.lock();
        try {
            if (this.completed) {
                return this.result;
            }
            this.result = getPoolEntry(timeout, unit);
            this.completed = true;
            return result;
        } catch (final IOException ex) {
            this.completed = true;
            this.result = null;
            throw new ExecutionException(ex);
        } finally {
            this.lock.unlock();
        }
    }

    protected  String getPoolEntry(
            long timeout, TimeUnit unit) throws IOException, InterruptedException, TimeoutException {
    	System.out.println(Thread.currentThread().getName());
    	Thread.sleep(300);
				return "ss";
    	
    }

    public boolean await(final Date deadline) throws InterruptedException {
        this.lock.lock();
        try {
            if (this.cancelled) {
                throw new InterruptedException("Operation interrupted");
            }
            final boolean success;
            if (deadline != null) {
                success = this.condition.awaitUntil(deadline);
            } else {
                this.condition.await();
                success = true;
            }
            if (this.cancelled) {
                throw new InterruptedException("Operation interrupted");
            }
            return success;
        } finally {
            this.lock.unlock();
        }

    }

    public void wakeup() {
        this.lock.lock();
        try {
            this.condition.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

}
