import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description 读写锁实现 缓存 线程安全
 * @Author beifang
 * @Create 2021/11/20 17:41
 */
public class Cache {
    static Map<String, Object> map = new HashMap<String, Object>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();

    // 获取一个值
    public static final Object get(String key) {
        r.lock();
        try{
            return map.get(key);
        }finally {
            r.unlock();
        }
    }

    // 添加一个值
    public static final Object put(String key, Object value) {
        w.lock();
        try{
            return map.put(key,value);
        }finally {
            w.unlock();
        }
    }

    // 清空
    public static final void clear() {
        w.lock();
        try{
            map.clear();
        }finally {
            w.unlock();
        }
    }


}
