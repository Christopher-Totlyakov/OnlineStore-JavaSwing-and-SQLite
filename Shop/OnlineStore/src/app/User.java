package app;

import java.sql.*;

public class User {
    private int userId;
    private String username;
    private String password;
    private String type;
    
   public User( String username,
      String password, String type)
    {
        this.username = username;
        this.password = password;
        this.type = type;
    }
    
    
    public User(int id, String username,
      String password, String type)
    {
        userId = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }
    
     public int getUserId(){
        return userId;
    }
        
     public String getUsername(){
        return username;
    }
     
      public String getPassword(){
        return password;
    }
     
    public String getType(){
        return type;
    }
    
    public boolean insert(Connect query){
        boolean connectionOpenedInside = false;
        if(query.conn == null){   
            query = new Connect();
            connectionOpenedInside = true;
        }
        String sql = "insert into users(username, password, role) values(?,?,?)";
        int rows = 0;
        try {
            PreparedStatement stmt = query.conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, "Customer");
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Rows affected: " + rows);
        if(connectionOpenedInside)
            query.close();
        return (rows > 0);
    }
    
}
