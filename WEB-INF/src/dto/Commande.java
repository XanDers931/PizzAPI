package dto;

import java.sql.Date;
import java.util.ArrayList;

public class Commande {
    private int id;
    private String name;
    private Date date;
    private ArrayList<Pizza> commandes;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public ArrayList<Pizza> getCommandes() {
        return commandes;
    }
    public void setCommandes(ArrayList<Pizza> commandes) {
        this.commandes = commandes;
    }

    public Commande createCommande(int id, String name, Date date, ArrayList<Pizza> commandes){
        Commande commande = new Commande();
        commande.setId(id);
        commande.setName(name);
        commande.setDate(date);
        commande.setCommandes(commandes);
        return commande;
    }

}
