package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerifierToken {
    public static boolean token(String login, String password){
        String url = "jdbc:postgresql://psqlserv/but2";
        String nom = "alexandremarteletu";
        String mdp = "moi";
        try(Connection con = DriverManager.getConnection(url,nom,mdp);) {
            PreparedStatement ps = con.prepareStatement("select * from users WHERE login=? AND pwd=?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();            
            if(rs.next()){
                //Si on reussi
                return true;
            }
            else{
                //si on reussi pas
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
