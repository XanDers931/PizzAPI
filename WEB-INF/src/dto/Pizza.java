package dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Pizza {
    private String nom,pate;
    private int id,prixBase;
    private ArrayList<Ingredient> ingredients;

    public Pizza createPizza(int id, String nom, String pate, int prixBase, ArrayList<Ingredient> ingredients) {
        Pizza pizza = new Pizza();
        pizza.setId(id);
        pizza.setNom(nom);
        pizza.setPate(pate);
        pizza.setPrixBase(prixBase);
        pizza.setIngredients(ingredients);
        return pizza;
    }

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPate() {
        return pate;
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
    public void setPate(String pate) {
        this.pate = pate;
    }
    public int getPrixBase() {
        return prixBase;
    }
    public void setPrixBase(int prixBase) {
        this.prixBase = prixBase;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }    
}