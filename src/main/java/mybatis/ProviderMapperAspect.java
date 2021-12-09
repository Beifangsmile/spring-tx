package mybatis;

import com.google.common.collect.Maps;
import org.apache.ibatis.binding.MapperProxy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/9 15:14
 */
@Aspect
@Component
public class ProviderMapperAspect {
    private static Map<String, ProviderContext> providerContextMap = Maps.newHashMap();
    private final static Logger logger = LoggerFactory.getLogger(ProviderMapperAspect.class);

    @Pointcut("this(mybatis.MyBatisRepository)")
    public void repositoryExecution() {

    }

    /**
     *
     * @param point
     */
    @Before("repositoryExecution()")
    public void setDynamicDataSource(JoinPoint point) {
        Class entityClass = null;
        Object target = point.getTarget();
        if(BaseRepository.class.isAssignableFrom(target.getClass())) {
            MapperProxy mapperProxy = (MapperProxy) Proxy.getInvocationHandler(target);
            Class mapperInterface = (Class) ReflectionUtils.getFieldValue(mapperProxy, "mapperInterfaces");
            ParameterizedType parameterizedType = (ParameterizedType) mapperInterface.getGenericInterfaces()[0];
            Type[] types = parameterizedType.getActualTypeArguments();
            try {
                entityClass = Class.forName(types[0].getTypeName());
                if (providerContextMap.containsKey(entityClass.getName())) {
                    ProviderContext providerContext = new ProviderContext();
                    providerContext.setEntityClass(entityClass);
                    providerContext.setIdClass(Class.forName(types[1].getTypeName()));
                    providerContextMap.put(entityClass.getName(), providerContext);
                }
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
        if(entityClass != null) {
            MyBatisProviderContext.get().setProviderContext(providerContextMap.get(entityClass.getName()));
        }

    }

    @After("repositoryExecution()")
    public void clearProviderMapper(JoinPoint point) {
        MyBatisProviderContext.get().remove();
    }
}
