package controleurs;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.PizzaDAODatabase;
import dto.Ingredient;
import dto.Pizza;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzaRestAPI extends DoPatch {
    PizzaDAODatabase dao = new PizzaDAODatabase();

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            Collection<Pizza> l = dao.findAll();
            String jsonstring = objectMapper.writeValueAsString(l);
            out.print(jsonstring);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length > 4) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int id = Integer.parseInt(splits[1]);
        Pizza e = dao.findById(id);
        if (e == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if(splits.length==3){
            if(splits[2].equals("prixfinal")){
                String jsonstring = objectMapper.writeValueAsString(dao.getPrixFinal(id));
                out.print(jsonstring);
                return;
            }
        }
        out.print(objectMapper.writeValueAsString(e));
        return;
        
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            Pizza pizza = objectMapper.readValue(buffer.toString(), Pizza.class);
            dao.save(pizza.getId(), pizza.getNom(),pizza.getPate(), pizza.getPrixBase(), pizza.getIngredients());
            out.print(buffer.toString());
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        else{
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            int id_pizza = Integer.parseInt(splits[1]);
            Ingredient ingredient = objectMapper.readValue(buffer.toString(), Ingredient.class);
            dao.addIngredient(id_pizza, ingredient.getId());
            out.print(buffer.toString());
        }
        return;
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2 && splits.length != 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int id_pizza = Integer.parseInt(splits[1]);
        if (splits.length == 2){
            if (dao.delete(id_pizza)) {
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else if (splits.length ==3){
            int id_ingredient = Integer.parseInt(splits[2]);
            dao.deleteIngredient(id_pizza, id_ingredient);
        }
    }

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int id = Integer.parseInt(splits[1]);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        if (dao.findById(id).getId() == 0) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        String payload = buffer.toString();

        @SuppressWarnings("unchecked")
        Map<String, String> jsonData = objectMapper.readValue(payload, Map.class);

        String prixString = jsonData.get("prix");

        int prix = Integer.parseInt(prixString);

        dao.patch(id, prix);

        out.print(objectMapper.writeValueAsString(dao.findById(id)));

        return;
    }    
}