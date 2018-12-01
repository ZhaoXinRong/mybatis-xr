package com.snowruin.mybatis.jdbc;

import com.google.common.collect.Lists;
import com.snowruin.mybatis.exception.MybatisException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * @ClassName JdbcPool
 * @Description TODO  连接池
 * @Author zxm
 * @Date 2018/12/1 10:00
 * @Version 1.0
 **/
@Slf4j
public class JdbcPool implements DataSource {

    private static LinkedList<Connection> connections =  Lists.newLinkedList();

    private void initJdbcPool(Jdbc jdbc){
        {
            try {

                Class.forName(jdbc.getDriverClassName());

                for (int i = 0; i < jdbc.getPoolInitSize() ; i++) {
                    Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUsername(), jdbc.getPassword());
                    connections.add(connection);
                }
                log.info("数据库连接池初始化完成，大小是： {}",connections.size());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public JdbcPool(Jdbc jdbc){
        initJdbcPool(jdbc);
    }

    @Override
    public Connection getConnection() throws SQLException {

        if(connections.size() > 0){
            // 获取一个连接
            Connection connection = connections.removeFirst();
            log.info("获取到了一个连接，目前还剩"+connections.size()+"个连接");

            return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), connection.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                   if(!method.getName().equals("close")){
                       return method.invoke(connection,args);
                   }else{
                       // 如果调用的是connection 对象的close 方法,则把conn还给数据库连接池
                       connections.add(connection);
                       log.info("连接已归还连接池了，现在还剩"+connections.size()+"个连接");
                       return null;
                   }
                }
            });
        }else{
            throw  new MybatisException("数据库繁忙");
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
