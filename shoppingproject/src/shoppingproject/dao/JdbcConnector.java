package shoppingproject.dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcConnectionPool;

/**
 *
 * @author jamal572
 */
public class JdbcConnector {
    private static final String username = "sa";
    private static final String password = "";
    
    private static JdbcConnectionPool pool;
    
    public static Connection getConnection(String url) {
        if(pool == null){
            pool = JdbcConnectionPool.create(url, username, password);
        }
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            //Throw a DAO exception to be caught at higher levels
            throw new DAOException(e.getMessage(), e);
        }
        
    }
    
}
