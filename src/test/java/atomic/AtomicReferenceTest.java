package atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/23 21:07
 */
public class AtomicReferenceTest {

    public static AtomicReference<User> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("conan", 15);
        atomicReference.set(user);
        User updateUser = new User("Shinichi", 17);
        atomicReference.compareAndSet(user,updateUser);
        System.out.println(atomicReference.get().getName());
        System.out.println(atomicReference.get().getOld());
    }

    static class User {
        private String name;
        private int old;
        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }
        public String getName() {
            return name;
        }
        public int getOld() {
            return old;
        }
     }

}
