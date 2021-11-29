package mybatis;

/**
 * @description 目标类接口实现类
 * @Author beifang
 * @Create 2021/11/28 21:55
 */
public class TargetImpl implements Target{
    @Override
    public String execute(String name) {
        System.out.println("targetImpl execute() method run");
        return name;
    }
}
