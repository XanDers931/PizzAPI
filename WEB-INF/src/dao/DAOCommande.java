package dao;

import java.util.ArrayList;

import dto.Commande;

public interface DAOCommande {

    public ArrayList<Commande> findAll();

    public Commande findById(int id);

    public boolean save( Commande commande);

    public boolean delete(int id);
}
