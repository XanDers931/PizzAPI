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
        String query = "SELECT * FROM pizza;";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Pizza> pizzas = new ArrayList<>();
            while ((rs.next())) {
                pizzas.add(this.findById(rs.getInt("id")));
            }
            return pizzas;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Pizza findById(int id) {
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            Pizza pizza = new Pizza();

            PreparedStatement stmt =  con.prepareStatement("SELECT * FROM pizza WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            pizza.setId(rs.getInt("id"));
            pizza.setNom(rs.getString("nom"));
            pizza.setPrixBase(rs.getInt("prixbase"));
            pizza.setPate(rs.getString("pate"));

            IngredientDAODatabase ingredientDAO = new IngredientDAODatabase();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM pizza_ingredient, ingredients WHERE pizza_id = ? AND id = pizza_id");
            ps.setInt(1, id);
            ResultSet rsIngredient = ps.executeQuery();
            ArrayList<Ingredient> ingredientPizza = new ArrayList<>();
            while (rsIngredient.next()) {
                ingredientPizza.add(ingredientDAO.findById(rsIngredient.getInt("ingredient_id")));
            }
            pizza.setIngredients(ingredientPizza);
            return pizza;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
    
    @Override
    public boolean save(int id, String name, String pate, int prixBase, ArrayList<Ingredient> ingredients) {
        String query = "Insert into pizza (id, nom, pate, prixBase) VALUES ( ?, ? , ?, ?) ; ";
        String querypizza_ingredient = " INSERT INTO pizza_ingredient (pizza_id,ingredient_id) VALUES ( ?, ?)";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, pate);
                ps.setInt(4, prixBase);
                ps.executeUpdate();
                try (PreparedStatement ps2 = con.prepareStatement(querypizza_ingredient)) {
                    for (Ingredient ingredient : ingredients) {
                        ps2.setInt(1, id);
                        ps2.setInt(2, ingredient.getId());
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

    public boolean patchPrix(int id, int new_price) {
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            PreparedStatement stmt = con.prepareStatement("update pizza set prixbase = ? where id = ?;");
            stmt.setInt(1, new_price);
            stmt.setInt(2, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted==1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean patchName(int id, String new_name) {
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            PreparedStatement stmt = con.prepareStatement("update pizza set nom = ? where id = ?;");
            stmt.setString(1, new_name);
            stmt.setInt(2, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted==1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean patchPate(int id, String new_pate) {
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            PreparedStatement stmt = con.prepareStatement("update pizza set pate = ? where id = ?;");
            stmt.setString(1, new_pate);
            stmt.setInt(2, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted==1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean addIngredient(int id_pizza, int id_ingredient) {
        String query = "Insert into pizza_ingredient (pizza_id, ingredient_id) VALUES (?, ?) ; ";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id_pizza);
                ps.setInt(2, id_ingredient);
                return ps.executeUpdate() == 1;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteIngredient(int id_pizza, int id_ingredient) {
        String query = "DELETE FROM pizza_ingredient WHERE pizza_id=? AND ingredient_id=? ; ";
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id_pizza);
                ps.setInt(2, id_ingredient);
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

    public int getPrixFinal(int id_pizza){
        String query = "SELECT prixbase FROM pizza WHERE id = ?;";
        int cpt = 0;
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://psqlserv/but2", "alexandremarteletu", "moi")) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id_pizza);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    cpt+=rs.getInt("prixbase");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            query = "SELECT prix FROM pizza_ingredient AS pi , ingredients AS i WHERE pi.ingredient_id=i.id AND pi.pizza_id=?;";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id_pizza);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    cpt+=rs.getInt("prix");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cpt;
    }
}