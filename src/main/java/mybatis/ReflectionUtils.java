package mybatis;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/6 15:06
 */
public class ReflectionUtils {

    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";
    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 未测试
     * @param clazz
     * @param index
     * @return
     */
    @Deprecated
    public static Class getClassGenericType(final Class clazz, final int index) {
        // 获取父类的类型
        Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if((index >= params.length) || index <0) {
            logger.warn("Index: " + index + ",Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }

        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];

    }

    /**
     * 调用 getter 方法
     * @param obj
     * @param propertyName
     * @return
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[]{});
    }

    /**
     * 调用 setter 方法, 更改属性值
     * @param obj
     * @param propertyName
     * @param value
     */
    public static void invokeSetter(Object obj, String propertyName, Object value) {
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
        invokeMethodByName(obj,setterMethodName,new Object[] {value});
    }

    /**
     * 获取字段的值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);
        if(field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj +"]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常", e.getMessage());
        }
        return result;
    }

    /**
     * 设置字段的值
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field =getAccessibleField(obj, fieldName);
        if(field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName +"] on target [" + obj + "]");
        }
        try{
            field.set(obj, value);
        }catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常：{}", e.getMessage());
        }
    }

    public static Field getAccessibleField(Object obj, String fieldName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for(Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *  改变 private/protected 的成员变量为public,
     * @param field
     */
    private static void makeAccessible(Field field) {
        if((!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }

    }

    /**
     * 通过反射 调用方法
     * @param obj   对象
     * @param methodName    方法名
     * @param args  参数数组
     * @return
     */
    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        if(method == null) {
            throw new IllegalArgumentException("Could note find method [ " + methodName + "] on target [" + obj + "]");
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw covertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 通过反射调用指定方法
     * @param obj   对象
     * @param methodName    方法名
     * @param parameterTypes    参数类型数据
     * @param args      参数对象数组
     * @return
     */
    public static Object invokeMethod(final Object obj,
                                      final String methodName,
                                      final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if(method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target" + obj + "]");
        }
        try{
            return method.invoke(obj, args);
        }catch (Exception e) {
            throw covertReflectionExceptionToUnchecked(e);
        }
    }

    public static RuntimeException covertReflectionExceptionToUnchecked(Exception e) {
        if((e instanceof IllegalAccessException)
                || (e instanceof  IllegalArgumentException)
                ||(e instanceof NoSuchMethodException)) {
            return new IllegalArgumentException(e);
        } else if(e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        } else if(e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * 循环向上转型，获取对象 DecalaredMethod,并强制设置为可访问。如向上转型的Object仍无法找到，则返回null,匹配函数名 + 参数类型
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getAccessibleMethod(final Object obj,
                                             final String methodName,
                                             final Class<?>... parameterTypes) {
        Validate.notNull(obj, "object can't be null (对象不能为空)");
        Validate.notBlank(methodName,"methodName can't be blank (方法名不能为null 或者为空)");
        for(Class<?> searchType = obj.getClass(); searchType != Object.class; searchType=searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName,parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            //TODO
        }
        return null;
    }

    /**
     * 将 private / protect 修饰的方法改成 public
     * @param obj
     * @param methodName
     * @return
     */
    public static Method getAccessibleMethodByName(final Object obj,
                                                   final String methodName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 改变 private / protected 的方法为 public,
     * @param method
     */
    public static void makeAccessible(Method method) {
        if((Modifier.isPublic(method.getModifiers())
                || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 将对象转化为 map , key为字段名，value为属性值
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if(key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }

    /**
     * bean 转 map 这个方法适合 bean 的属性值包含list集合的
     * // TODO 未完善测试，未对list集合中的对象类型进行判断
     *
     * @param bean
     * @return
     */
    @Deprecated
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor pd : pds) {
                // 属性名
                String propertyName = pd.getName();
                // 获取get方法
                Method getMethod = pd.getReadMethod();
                // 属性值
                Object properValue = getMethod.invoke(bean);
                // 如果属性值为list
                if(properValue instanceof java.util.List) {
                    List list = (List) properValue;
                    // 如果集合不为空
                    if(!CollectionUtils.isEmpty(list)) {
                        Class childClass = list.get(0).getClass();
                        // 如果是常见类型
                        if(true){

                        }else{
                            List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
                            Object obj;
                            for(int i = 0; i<list.size();i++) {
                                obj = list.get(i);
                                Field[] fieldChilds = obj.getClass().getDeclaredFields();
                                Map<String, Object> resultChild = new HashMap<>();
                                for(Field field : fieldChilds) {
                                    boolean accessible2 = field.isAccessible();
                                    if(!accessible2) {
                                        field.setAccessible(true);
                                    }
                                    String key = field.getName();
                                    Object objVal = field.get(obj);
                                    if(null != objVal && !"".equals(objVal)) {
                                        resultChild.put(key, field.get(obj));
                                    }
                                }
                                mapList.add(resultChild);
                            }
                            map.put(propertyName, mapList);
                        }
                    }
                } else {
                // 如果不是list
                    if(null != properValue && !"".equals(properValue)) {
                        map.put(propertyName, properValue);
                    }
                }

            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }
}
