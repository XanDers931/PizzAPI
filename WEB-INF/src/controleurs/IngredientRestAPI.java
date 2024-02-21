package controleurs;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.IngredientDAODatabase;
import dto.Ingredient;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/ingredients/*", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class IngredientRestAPI extends HttpServlet {
    IngredientDAODatabase dao = new IngredientDAODatabase();

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            Collection<Ingredient> l = dao.findAll();
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
        Ingredient e = dao.findById(id);
        if (e == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if(splits.length==3){
            if(splits[2].equals("name")){
                String jsonstring = objectMapper.writeValueAsString(e.getName());
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
            Ingredient ingredient = objectMapper.readValue(buffer.toString(), Ingredient.class);
            dao.save(ingredient.getId(), ingredient.getName(), ingredient.getPrix());
            out.print(buffer.toString());
            return;
        }

        return;
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
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
}