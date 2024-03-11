package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dto.Commande;
import dto.Pizza;

public class CommandeDAODatabase implements DAOCommande{

    String url = "jdbc:postgresql://psqlserv/but2";
    String nom = "alexandremarteletu";
    String mdp = "moi";

    @Override
    public ArrayList<Commande> findAll() {
        String query = "SELECT * FROM commande;";
         try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Commande> commandes = new ArrayList<>();
            PizzaDAODatabase pizzaDao = new PizzaDAODatabase();
            while ((rs.next())) {
                Commande cmd = new Commande();
                cmd.setId(rs.getInt("id"));
                cmd.setName(rs.getString("nom"));
                cmd.setDate(rs.getDate("dates"));
                cmd.setCommandes(pizzaDao.findAll());
                commandes.add(cmd);
            }
            return commandes;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Commande findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean save(int id, String name, Date date, ArrayList<Pizza> commande) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
