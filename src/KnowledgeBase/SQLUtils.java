package KnowledgeBase;

import java.sql.*;

/**
 * connect to mysql server
 * select, insert, delete, update the database
 * Created by dixonshen on 2017/2/28.
 */
public class SQLUtils {

    private Connection conn = null;
    PreparedStatement statement = null;

    public SQLUtils(){
        connSQL();
    }

    // connect to MySQL
    public void connSQL() {
        String url = "jdbc:mysql://localhost:3306/dblp_data";
        String username = "root";
        String password = "123456";

        // 加载驱动程序以连接
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            System.out.println("connect successfully!");
        }
        // 捕获加载驱动异常
        catch (ClassNotFoundException cnfex) {
            System.err.println("装载JDBC/ODBC 驱动程序失败。");
            cnfex.printStackTrace();
        }
        catch (SQLException sqlex) {
            System.err.println("无法连接数据库");
            sqlex.printStackTrace();
        }
    }

    //disconnect to MySQL
    public void deconnSQL(){
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            System.out.println("关闭数据库问题：");
            e.printStackTrace();
        }
    }

    //execute selection
    public ResultSet selectSQL(String sql) {
        ResultSet resultSet = null;
        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //execute insertion
    public boolean insertSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("插入数据库错误：");
            e.printStackTrace();
        }
        return false;
    }

    //execute delete
    public boolean deleteSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("删除数据错误：");
            e.printStackTrace();
        }
        return false;
    }

    //execute update
    public boolean updateSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("更新数据时出错：");
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SQLUtils mysql = new SQLUtils();
    }
}
