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
        String queryPizza = "SELECT id, nom, pate, prixBase FROM pizza;";
        String queryIngredients = "SELECT id, name, prix FROM ingredients,pizza_ingredient WHERE ingredients.id=pizza_ingredient.ingredient_id AND pizza_ingredient.pizza_id = ? ;";
        int index;
        Ingredient tmpIngredient;
        ArrayList<Ingredient>ingredients;
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(queryPizza);
            ArrayList<Pizza> pizza = new ArrayList<>();
            
            while ((rs.next())) {
                Pizza tmp = new Pizza();
                ingredients = new ArrayList<>();
                tmp.setId(rs.getInt("id"));
                tmp.setNom(rs.getString("nom"));
                tmp.setPate(rs.getString("pate"));
                tmp.setPrixBase(rs.getInt("prixbase"));
                //Récupération des ingrédients de la pizza
                index = tmp.getId();

                try (PreparedStatement ps = con.prepareStatement(queryIngredients)) {
                    ps.setInt(1, index);
                    System.out.println(ps);
                    ResultSet rs2 = ps.executeQuery();
                    while (rs2.next()) {
                        tmpIngredient = new Ingredient();
                        ingredients.add(tmpIngredient.createIngredient(rs2.getInt("id"), rs2.getString("name"), rs2.getInt("prix")));
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
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
        String queryIngredients = "SELECT id, name, prix FROM ingredients,pizza_ingredient WHERE ingredients.id=pizza_ingredient.ingredient_id AND pizza_ingredient.pizza_id = ? ;";
        Ingredient tmpIngredient;
        ArrayList<Ingredient>ingredients;
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Pizza pizza = new Pizza();
                    ingredients = new ArrayList<>();
                    pizza.setId(rs.getInt("id"));
                    pizza.setNom(rs.getString("nom"));
                    pizza.setPate(rs.getString("pate"));
                    pizza.setPrixBase(rs.getInt("prixbase"));
                    
                    try (PreparedStatement ps1 = con.prepareStatement(queryIngredients)) {
                        ps1.setInt(1, pizza.getId());
                        System.out.println(ps1);
                        ResultSet rs2 = ps1.executeQuery();
                        while (rs2.next()) {
                            tmpIngredient = new Ingredient();
                            ingredients.add(tmpIngredient.createIngredient(rs2.getInt("id"), rs2.getString("name"), rs2.getInt("prix")));
                        }
                        pizza.setIngredients(ingredients);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                    
                    return pizza;
                }
                
                return null;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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

    public boolean patch(int id, int new_price) {
        try (Connection con = DriverManager.getConnection(url, nom, mdp)) {
            PreparedStatement stmt = con.prepareStatement("update pizza set prixBase = ? WHERE id = ?");
            System.out.println(stmt);
            stmt.setInt(2, id);
            stmt.setInt(1, new_price);
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
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
