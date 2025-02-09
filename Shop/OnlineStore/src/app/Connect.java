
package app;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class Connect {
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;
    
    public Connect(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:shop.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }

    /**
     * Metod, koito stroi standartna SQL SELECT zaqvka
     * po dadeni masiv ot koloni za kolonite, za koito
     * iskame da vurne rezultat i ot koq tablica.
     * @param columnsArray kolonite, za koito iskame danni
     * @param table tablicata, ot koqto izvikvame danni
     * @return dinamichen String masiv s dannite ot zaqvkata
     */
    public ArrayList<String> select(String[] columnsArray, String table){
        ArrayList data = new ArrayList<String>();
        String columns = String.join(", ", columnsArray);
        String sql = "SELECT " + columns + " FROM " + table;

        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i])+"---";
                }
                row=row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    /**
     * Metod, koito stroi standartna SQL SELECT WHERE zaqvka
     * po dadeni masiv ot koloni za kolonite, za koito
     * iskame da vurne rezultat, ot koq tablica, po koq
     * kolona da tursi i kakva stoinost.
     * @param columnsArray kolonite, za koito iskame danni
     * @param table tablicata, ot koqto izvikvame danni
     * @param whereColumn v koq kolona da tursi suvpadenie
     * @param whereValue kakva stoinost da tursi v kolonata
     * @return dinamichen String masiv s dannite ot zaqvkata
     */
    //select firstname, lastname from employees where city like '%a%'
    public ArrayList<String> selectWhere(String[] columnsArray, String table, String whereColumn, String whereValue){
        ArrayList data = new ArrayList<String>();
        String columns = String.join(", ", columnsArray);
        String sql = "SELECT " + columns + " FROM " + table 
                + " WHERE " + whereColumn + " LIKE '" + whereValue+"'";

        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i])+"---";
                }
                row=row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    /**
     * Metod, koito stroi SQL SELECT WHERE zaqvka
     * po dadeni masiv ot koloni za kolonite, za koito
     * iskame da vurne rezultat, ot koq tablica i po koi
     * koloni da tursi suvpadenie, kato e neobhodimo samo
     * ednoto uslovie da e uspylneno.Dannite se podavat
     * v dva masiva, kato vseki element na ediniq suotvetstva
     * na elementiq sus sushtiq index ot drugiq.
     * @param columnsArray kolonite, za koito iskame danni
     * @param table tablicata, ot koqto izvikvame danni
     * @param whereColumns po koi koloni da tursi
     * @param whereValues kakvi stoinosti da tursi
     * @return dinamichen String masiv s dannite ot zaqvkata
     */
    public ArrayList<String> selectWhereOr(String[] columnsArray, String table, String[] whereColumns, String[] whereValues){
        ArrayList data = new ArrayList<String>();
        String columns = String.join(", ", columnsArray);
        
        String sql = "SELECT " + columns + " FROM " + table + " WHERE ";
        
        for (int i = 0; i < whereColumns.length; i++) {
            sql=sql+whereColumns[i]+" LIKE '" + whereValues[i]+"' OR ";
        }
        sql=sql.substring(0,sql.length()-4);
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i])+"---";
                }
                row=row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    /**
     * Metod, koito stroi SQL SELECT WHERE zaqvka
     * po dadeni masiv ot koloni za kolonite, za koito
     * iskame da vurne rezultat, ot koq tablica i po koi
     * koloni da tursi suvpadenie, kato e neobhodimo VSICHKI
     * usloviq da sa uspylneni.Dannite se podavat
     * v dva masiva, kato vseki element na ediniq suotvetstva
     * na elementiq sus sushtiq index ot drugiq.
     * @param columnsArray kolonite, za koito iskame danni
     * @param table tablicata, ot koqto izvikvame danni
     * @param whereCols v koi koloni tursi stoinost
     * @param whereValues kakvi stoinosti da tursi
     * @return dinamichen masiv s dannite ot zaqvkata
     */
    public ArrayList<String> selectWhereAnd(String[] columnsArray, String table, String[] whereCols, String[] whereValues){
        ArrayList data = new ArrayList<String>();
        String columns = String.join(", ",columnsArray);

        String sql = "SELECT " + columns + " FROM " + table + " WHERE ";
        
        for(int i=0; i<whereCols.length; i++){
            sql = sql + whereCols[i] + " LIKE '" + whereValues[i] + "' AND ";
        }
        sql = sql.substring(0,sql.length()-5);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println(sql);
            while(rs.next()){
                String row="";
                for (int i = 0; i < columnsArray.length; i++) {
                    row+=rs.getString(columnsArray[i])+"---";
                }
                row=row.substring(0, row.length()-3);
               data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return data;
    }
    
     /**
     * Metod, koito stroi INSERT zaqvka
     * @param table v koq tablica da dobavq
     * @param columnsArray v koi koloni da dobavq
     * @param valuesArray kakvi danni da dobavq
     */
    //insert into users (username, password) values ('ime', 'parola')
    public void insert(String table, String[] columnsArray, String[] valuesArray){
        String columns = String.join(", ", columnsArray);
        String values = String.join("', '", valuesArray);
        String sql = "Insert into " + table + " ("+columns+") values ('"+values+"')";

        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metod, koito stroi zashtitena INSERT zaqvka
     * @param table v koq tablica da dobavq
     * @param columns v koi koloni da dobavq
     * @param values kakvi danni da dobavq
     */
    public void insertP(String table, String[] columns, String[] values){
        String sql = "";
        //columns [0]username,[1]password
        String cols = String.join(", ", columns);
        //cols = username, password
        //values -> [0]aaa,[1]1234
        String placeholders="";
        for (int i = 0; i < values.length; i++) {
            placeholders += "?";
            if(i<values.length-1)
                placeholders+=", ";
        }
        sql = String.format("insert into %s (%s) values (%s)",table,cols,placeholders);
        //insert into users (username, password) values ('aaa', '1234')
        try{
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                stmt.setString(i+1, values[i]);
            }
            int res = stmt.executeUpdate();
            System.out.println("Rows affected: " + res);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Metod, koito stroi UPDATE zaqvka
     * @param table v koq tablica shte promenq danni
     * @param columnsArray za koi koloni shte promenq danni
     * @param valuesArray kakvi stoinosti shte promenq
     * @param whereCol po kakva kolona shte tursi suvpadenie
     * @param whereVal kakvo syvpadenie shte tursi
     */
    //update employees set fn='ime', ln='familiq', title='titla', city='grad', country='durjava' where employeeid='1'
    public void update(String table, String[] columnsArray, String[] valuesArray, String whereCol, String whereVal){
        String sql = "update "+ table + " set ";
        for (int i = 0; i < columnsArray.length; i++) {
            sql=sql+columnsArray[i]+" = '"+valuesArray[i]+"', ";
        }
        sql=sql.substring(0, sql.length()-2);
        sql = sql + " WHERE "+ whereCol + " = '"+whereVal+"'";
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metod, koito stroi zashtitena UPDATE zaqvka
     * @param table v koq tablica shte promenq danni
     * @param columns za koi koloni shte promenq danni
     * @param values kakvi stoinosti shte promenq
     * @param whereCol po kakva kolona shte tursi suvpadenie
     * @param whereVal kakvo syvpadenie shte tursi
     */
    public void updateP(String table, String[] columns, String[] values, String whereCol, String whereVal){
       if (columns.length!=values.length) {
            System.out.println("различен брой колони и стойности!");
            return;
        }
        String body="";
        for (int i = 0; i < columns.length; i++) {
            body+=columns[i] + " = ?, ";
        }
        body=body.substring(0,body.length()-2);
        String sql = String.format("update %s set %s where %s = ?",table,body,whereCol);
        try{
            System.out.println(sql);
            PreparedStatement stmt = 
               conn.prepareStatement(sql);
            int i = 0;
            for (i = 0; i < values.length; i++) {
                stmt.setString(i+1, values[i]);
            }
            //update users set username = "qkuser", password = "qkaparola", status = "admin"
            stmt.setString(i+1,whereVal);
            //... where userid = 3
            int res = stmt.executeUpdate();
            System.out.println("Rows affected: " + res);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Metod, koito stroi DELETE WHERE zaqvka
     * @param table ot koq tablica da trie
     * @param whereCol v koq kolona da tursi stoinost
     * @param whereValue kakva stoinost da tursi
     */
    //delete from employees where employeeid like '9'
    public void delete(String table, String whereCol, String whereValue){
        String sql = "DELETE FROM " + table + " WHERE "+
                whereCol + " LIKE '"+whereValue+"'";
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metod, koito stroi zashtitena DELETE WHERE zaqvka
     * @param table ot koq tablica da trie
     * @param whereCol v koq kolona da tursi stoinost
     * @param whereValue kakva stoinost da tursi
     */
    public void deleteP(String table, String whereCol, String whereValue){
        String sql = 
           "delete from " + table + " where " + whereCol + " = ?";
        try{
            System.out.println(sql);
            PreparedStatement stmt = 
               conn.prepareStatement(sql);
            stmt.setString(1, whereValue);
            //delete from customers where customerid = 77
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
   
    public User authenticateUser(String username, String password) {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            // Създайте обект User с данните от базата
            int id = rs.getInt("UserID"); // Предполага се, че името на колоната е "status"
            String type = rs.getString("role"); // Предполага се, че името на колоната е "status"
            return new User(id, username, password, type);
        }
    } catch (SQLException e) {
        System.err.println("Error authenticating user: " + e.getMessage());
    }
    return null; // Ако няма съвпадение или има грешка
}

    
    /**
     * Извлича стойността на последното добавено ID от AutoNumber на току-що insert-нат запис
     * @return ID-то на новодобавения елемент
     */
    public int getLastInsertedID(){
        try {
            rs = conn.createStatement().executeQuery("SELECT last_insert_rowid() as id;");
            if(rs.next())
                return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    /**
     * Zatvarq vruzkata s bazata.
     */
    public void close(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Throwable ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
