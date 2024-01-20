package jejuOseyo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
   private static Connection con;

   private DBConn() {
   }// 1. 외부에서 접근할 수 없는 기본생성자 작성

   public static Connection getConnection() {// 2. Connection 객체가 null인 경우에만 객체를 생성하고 반환하는 공유 메서드 getConnection()
            
      if  (con==null) {
         
         String driver = "oracle.jdbc.OracleDriver";
         String url = "jdbc:oracle:thin:@localhost:1521:xe";
         String username = "jejuOseyo";
         String password = "1111";
   
         try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("con ok");
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         } 
          catch (SQLException e) {
            e.printStackTrace();
         }
         
      }
      return con;
      
      
   }
   

   public static void close(Statement stmt) {// 3. Statement, Connection 객체를 매개변수로 받아서 닫는 메서드
      try {
         if (stmt != null)
            stmt.close();
         
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public static void close(PreparedStatement pstmt) {// 4. PreparedStatement, Connection 객체를 매개변수로 받아서 닫는 메서드

      try {
         if (pstmt != null)
            pstmt.close();
         
      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }

//   5. pstmt, rs 받는 close 메서드 구현
   public static void close(PreparedStatement pstmt, ResultSet rset) {
      try {
         if (rset != null)
            rset.close();
         if (pstmt != null)
            pstmt.close();
         
         
      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }
//   6. 매개변수가 없는 colse 메서드 추가하여 connection 닫는 메서드 구현
   public static void close() {
      if (con != null)
         try {
            con.close();
         } catch (SQLException e) {
            
            e.printStackTrace();
         }
   }


}