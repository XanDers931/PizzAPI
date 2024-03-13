package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
            while ((rs.next())) {
                commandes.add(this.findById(rs.getInt("id")));
            }
            return commandes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Commande findById(int id) {
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            Commande commande = new Commande();

            PreparedStatement stmt =  con.prepareStatement("SELECT * FROM commande WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            commande.setId(rs.getInt("id"));
            commande.setName(rs.getString("nom"));
            commande.setDate(rs.getDate("dates"));

            PizzaDAODatabase pizzaDao = new PizzaDAODatabase();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM commande_pizza, pizza WHERE commande_id = ? AND id = commande_id");
            ps.setInt(1, id);
            ResultSet rsPizza = ps.executeQuery();
            ArrayList<Pizza> pizzasCommande = new ArrayList<>();

            while (rsPizza.next()) {
                pizzasCommande.add(pizzaDao.findById(rsPizza.getInt("pizza_id")));
            }
            commande.setCommandes(pizzasCommande);
            return commande;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean save(int id, String name, Date date, ArrayList<Pizza> commande) {
        String query = "Insert into commande (id, nom, dates) VALUES ( ?, ? , ?) ; ";
        String querycommande_pizza = " INSERT INTO commande_pizza (commande_id,pizza_id) VALUES ( ?, ?)";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setDate(3, date);
                ps.executeUpdate();
                try (PreparedStatement ps2 = con.prepareStatement(querycommande_pizza)) {
                    for (Pizza pizza : commande) {
                        ps2.setInt(1, id);
                        ps2.setInt(2, pizza.getId());
                        ps2.executeUpdate();
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
