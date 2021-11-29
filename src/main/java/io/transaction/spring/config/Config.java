package io.transaction.spring.config;

import com.rabbitmq.client.impl.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * @description
 * @Author beifang
 * @Create 2021/11/26 20:21
 */
public class Config implements TransactionManagementConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    String[] dataSourcePrefix = {"master", "slave", "wechat", "api"};

//    public DynamicDataSource dataSource() {
//        Map<Object, Object> targetDataSource = new HashMap<>();
//
//        for(String prefix : dataSourcePrefix) {
//            targetDataSource.put(prefix, create);
//        }
//
//
//    }

//    private DataSource createDateSource() {
//        boolean userJndi =
//    }


    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return null;
    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
//        sf.setDataSource(dataSource());
//        sf.setTypeAliasesPackage("io.transaction.spring.**.model.domain");
//        sf.setPlugins();
//        Configuration configuration = new Configuration();
//        configuration.setCallSettersOnNulls(true);
//        configuration.setMapUnderscoreToCamelCase(true);
//        return sf.getObject();
//    }

}
