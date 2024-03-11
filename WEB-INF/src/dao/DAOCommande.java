package dao;

import java.sql.Date;
import java.util.ArrayList;

import dto.Commande;
import dto.Pizza;

public interface DAOCommande {

    public ArrayList<Commande> findAll();

    public Commande findById(int id);

    public boolean save(int id, String name, Date date, ArrayList<Pizza> commande);

    public boolean delete(int id);
}
