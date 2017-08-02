package TrackMyBus;


import java.sql.*;

public class getUpdates {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/track_my_bus";
    final int datano;
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    
    getUpdates(int i){
        this.datano = i;
    }

    public double[] getData() {
        Connection conn = null;
        Statement stmt = null;
        double[] array = new double[2];
        try{
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT time,latitude,longitude FROM route1bus1 WHERE no="+this.datano;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Time time = rs.getTime("time");
                double la = rs.getDouble("latitude");
                double lo = rs.getDouble("longitude");
                array[0] = la;
                array[1] = lo;
            }

            rs.close();stmt.close();conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        
       //System.out.println(array[0]+"  "+array[1]+"dfghjhgfd"); 
        return array;
    }
}



