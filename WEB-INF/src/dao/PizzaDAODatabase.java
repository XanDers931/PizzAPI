package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dto.Ingredient;
import dto.Pizza;

public class PizzaDAODatabase implements DAOPizza {

    String url = "jdbc:postgresql://psqlserv/but2";
    String nom = "alexandremarteletu";
    String mdp = "moi";

    @Override
    public ArrayList<Pizza> findAll() {
        String query = "SELECT id, nom, pate, prixBase FROM pizza;";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Pizza> pizza = new ArrayList<>();
            ArrayList<Ingredient>ingredients = new ArrayList<>();
            String queryIngredients = "SELECT * FROM pizza_ingredient";
            while ((rs.next())) {
                Pizza tmp = new Pizza();
                tmp.setId(rs.getInt("id"));
                tmp.setName(rs.getString("nom"));
                tmp.setPate(rs.getString("pate"));
                tmp.setPrixBase(rs.getInt("prixbase"));
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt.executeQuery(queryIngredients);
                while (rs2.next()) {
                    ingredients.add(dao.findById(rs2.getInt("ingredient_id")));
                }
                tmp.setIngredients(ingredients);
                pizza.add(tmp);
            }
            return pizza;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Pizza findById(int id) {
        String query = "SELECT * FROM pizza WHERE id = ? ;";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Pizza tmp = new Pizza();
                    tmp.setId(rs.getInt("id"));
                    tmp.setName(rs.getString("nom"));
                    tmp.setPate(rs.getString("pate"));
                    tmp.setPrixBase(rs.getInt("prixbase"));
                    pizza.add(tmp);
                    return pizza;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean save(int id, String nom, String pate, int prixBase) {
        String query = "Insert into pizza (id, nom, pate, prixBase) VALUES ( ?, ? , ?, ?) ; ";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ps.setString(2, nom);
                ps.setString(3, pate);
                ps.setInt(4, prixBase);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM pizza WHERE id = ?");
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted==1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
}
