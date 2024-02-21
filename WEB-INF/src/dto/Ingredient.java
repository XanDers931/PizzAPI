package dto;

/**
 * Ingredients
 */
public class Ingredient {

    int id, prix;
    String name;

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPrix() {
        return prix;
    }
    public void setPrix(int prix) {
        this.prix = prix;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Ingredient createIngredient(int id, String name, int prix) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName(name);
        ingredient.setPrix(prix);
        return ingredient;
    }

}