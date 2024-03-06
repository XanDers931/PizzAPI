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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pizzas/*")
public class PizzaRestAPI extends doPatch {
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
        if (splits.length > 3) {
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
            if(splits[2].equals("name")){
                String jsonstring = objectMapper.writeValueAsString(e.getNom());
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
        int id_pizza = Integer.parseInt(splits[1]);
        
        return;
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
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
        if (dao.delete(id)) {
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {
        System.out.println("TESTTTTTTTTTTTT");
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

        Map<String, String> jsonData = objectMapper.readValue(payload, Map.class);

        String prixString = jsonData.get("prix");

        int prix = Integer.parseInt(prixString);

        dao.patch(prix, id);

        out.print(objectMapper.writeValueAsString(dao.findById(id)));

        return;
    }
    
}