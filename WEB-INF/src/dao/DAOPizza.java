package dao;

import java.util.ArrayList;

import dto.Ingredient;
import dto.Pizza;

public interface DAOPizza {
    public ArrayList<Pizza> findAll();

    public Pizza findById(int id);

    public boolean save(int id, String name, String pate, int prixBase, ArrayList<Ingredient> ingredients);
}
