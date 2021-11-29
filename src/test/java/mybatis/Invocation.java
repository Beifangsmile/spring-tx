package mybatis;

import java.lang.reflect.Method;

/**
 * @description 对拦截对象进行封装，作为拦截器拦截方法的参数，把拦截目标对象真正执行方法到Interceptor中完成
 * @Author beifang
 * @Create 2021/11/28 22:20
 */
public class Invocation {
    /**
     * 目标对象
     */
    private Object target;
    /**
     * 目标方法
     */
    private Method method;
    /**
     * 方法的参数
     */
    private Object[] args;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    /**
     * 执行目标对象的方法
     * @return
     * @throws Exception
     */
    public Object process() throws Exception {
        return method.invoke(target,args);
    }
}
