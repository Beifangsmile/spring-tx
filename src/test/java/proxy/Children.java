package proxy;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/25 23:45
 */
public class Children implements Parent{
    @Override
    public void sysHello() {
        System.out.println("children hello method");
    }
}
