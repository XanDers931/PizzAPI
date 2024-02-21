package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dto.Ingredient;

public class IngredientDAODatabase implements DAOIngredient {

    String url = "jdbc:postgresql://psqlserv/but2";
    String nom = "alexandremarteletu";
    String mdp = "moi";

    @Override
    public ArrayList<Ingredient> findAll() {
        String query = "SELECT * FROM ingredients;";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            while ((rs.next())) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("id"));
                ingredient.setName(rs.getString("name"));
                ingredient.setPrix(rs.getInt("prix"));
                ingredients.add(ingredient);
            }
            return ingredients;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Ingredient findById(int id) {
        String query = "SELECT * FROM ingredients WHERE id = ? ;";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(rs.getInt("id"));
                    ingredient.setName(rs.getString("name"));
                    ingredient.setPrix(rs.getInt("prix"));
                    return ingredient;
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
    public boolean save(int id, String name, int prix) {
        String query = "Insert into ingredients (id, name, prix) VALUES ( ?, ? , ?) ; ";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setInt(3, prix);
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

    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM ingredients WHERE id = ?");
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
