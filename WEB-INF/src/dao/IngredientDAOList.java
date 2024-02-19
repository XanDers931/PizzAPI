package dao;

import java.util.ArrayList;

import dto.Ingredients;

public class IngredientDAOList {
    ArrayList<Ingredients> lstIngredients;

    public IngredientDAOList(){
        this.lstIngredients = new ArrayList<>();
        
        Ingredients poivron = new Ingredients();
        poivron.setId(0);
        poivron.setName("poivron");
        poivron.setPrix(150);

        Ingredients chorizo = new Ingredients();
        poivron.setId(1);
        poivron.setName("chorizo");
        poivron.setPrix(0);

        Ingredients lardons = new Ingredients();
        poivron.setId(2);
        poivron.setName("lardon");
        poivron.setPrix(15);
    }

    public ArrayList<Ingredients> findAll(){
        return this.lstIngredients;
    }

    public Ingredients findById(int index){
        return this.lstIngredients.get(index);
    }

    public void save(Ingredients ingredients){
        this.lstIngredients.add(ingredients);
    }
}
