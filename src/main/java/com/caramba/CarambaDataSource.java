package com.caramba;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

@Slf4j
public class CarambaDataSource implements DataSource {

    private final String url;
    private final String user;
    private final String password;

    private int defaultConnectionsNum = 10;
    private Queue<Connection> connectionPool;

    @SneakyThrows
    public CarambaDataSource(String url, String user, String password, String dbDriverName) {
        this.url = url;
        this.user = user;
        this.password = password;

        Class.forName(dbDriverName);
        initializeConnectionPool();
    }

    @SneakyThrows
    private Connection getNewConnection() {
        return DriverManager.getConnection(url, user, password);
    }

    private void initializeConnectionPool() {
        connectionPool = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < defaultConnectionsNum; i++) {
            Connection physicalConnection = getNewConnection();
            ConnectionProxy connectionProxy = new ConnectionProxy(physicalConnection, connectionPool);
            connectionPool.add(connectionProxy);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = connectionPool.poll();
        log.info("Retrieving a connection from the connection pool. Available connection left: " + connectionPool.size());
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
