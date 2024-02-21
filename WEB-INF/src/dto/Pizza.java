package dto;

import java.util.ArrayList;

public class Pizza {
    private String nom,pate;
    private int prixBase;
    private ArrayList<Ingredient> ingredients;

    public Pizza createPizza(String nom, String pate, int prixBase, ArrayList<Ingredient> ingredients) {
        Pizza pizza = new Pizza();
        pizza.setNom(nom);
        pizza.setPate(pate);
        pizza.setPrixBase(prixBase);
        pizza.setIngredients(ingredients);
        return pizza;
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
