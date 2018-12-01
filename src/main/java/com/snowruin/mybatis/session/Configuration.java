package com.snowruin.mybatis.session;

import com.google.common.collect.Lists;
import com.snowruin.mybatis.Mapper.Function;
import com.snowruin.mybatis.Mapper.MapperBean;
import com.snowruin.mybatis.enums.EnumMapper;
import com.snowruin.mybatis.exception.MybatisException;
import com.snowruin.mybatis.jdbc.Jdbc;
import com.snowruin.mybatis.jdbc.JdbcPool;
import com.snowruin.mybatis.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName Configuration
 * @Description TODO  配置
 * @Author zxm
 * @Date 2018/11/27 14:03
 * @Version 1.0
 **/
@Slf4j
public class Configuration {

    private static  final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml信息并处理
     * @param resource
     * @return
     */
    public Connection build(String resource){
        InputStream inputStream =  classLoader.getResourceAsStream(resource);
        SAXReader reader = new SAXReader();
        try {
            Document document =  reader.read(inputStream);
            Element rootElement = document.getRootElement();
            return evalDataSource(rootElement);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析 mybatis-config.xml
     * @param element
     * @return
     */
    private Connection evalDataSource(Element element){

        if(!element.getName() . equals("database")){
            throw  new MybatisException("不是database节点");
        }

        String driverClassName = null,
               url = null,
               username = null,
               poolInitSize = null,
               password = null;

        // 获取属性节点
        List<Element> elements =  element.elements("property");
        for (Element e: elements){
            String value = getValue(e);
            String name = e.attributeValue("name");

            if(StringUtils.isEmpty(value) || StringUtils.isEmpty(name)){
                throw  new MybatisException("配置文件有误，请检查");
            }

            // 赋值
            switch (name){
                case "url" :
                    url = value;
                    break;
                case "username" :
                    username = value;
                    break;
                case "password" :
                    password = value;
                    break;
                case "driverClassName" :
                    driverClassName =  value;
                    break;
                case "poolInitSize" :
                    poolInitSize = value;
                    break;
                    default :
                        throw  new MybatisException("不知道的property属性");
            }
        }


        try {
            return  new JdbcPool(new Jdbc()
                                                    .setDriverClassName(driverClassName)
                                                    .setPassword(password)
                                                    .setUrl(url)
                                                    .setUsername(username)
                                                    .setPoolInitSize(StringUtils.isEmpty(poolInitSize) ? 10 : Integer.parseInt(poolInitSize))
                                                ).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取属性的值
     * @param element
     * @return
     */
    private String getValue(Element element){
        return element.hasContent() ? element.getText() : element . attributeValue("value");
    }


    /**
     * 解析Mapper xml
     * @param path
     * @return
     */
    public MapperBean readMapper(String path){
        MapperBean mapperBean = new MapperBean();

        InputStream inputStream = classLoader.getResourceAsStream(path);
        Document document = null;
        try {
            document = new SAXReader().read(inputStream);
            Element rootElement = document.getRootElement();
            // 把mapper节点的nameSpace 值存储为接口名称
            mapperBean.setInterfaceName(rootElement.attributeValue("nameSpace").trim());

            // 存储方法的list
            List<Function> functions = Lists.newArrayList();
            String interfaceName =  mapperBean.getInterfaceName().trim();
            Class<?> aClass = null;
            try {
                aClass = Class.forName(interfaceName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext();){

                Element  element = (Element) iterator.next();
                String sqlType = element.getName().trim();
                String functionName = element.attributeValue(EnumMapper.SQL_OPTION.getId()).trim();

                String sql = element.getText();
                String parameterType = element.attributeValue(EnumMapper.SQL_OPTION.getParameterType());

                String resultType = element.attributeValue(EnumMapper.SQL_OPTION.getResultType());

                Function function = new Function()
                        .setFuncName(functionName)
                        .setSqlType(sqlType)
                        .setParameterType(parameterType)
                        .setResultType(resultType);

                Method[] methods = aClass.getDeclaredMethods();

                for (Method method : methods){
                    if(method.getName() . equals( functionName )){
                       function.setMethodReturnType(method.getReturnType())
                                .setMethod(method);
                    }
                }

                function.setResultType(resultType)
                        .setSql(sql);
                functions.add(function);
            }
            mapperBean.setList(functions);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return mapperBean;
    }

}
