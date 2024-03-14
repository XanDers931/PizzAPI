package controleurs;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.IngredientDAODatabase;
import dao.VerifierToken;
import dto.Ingredient;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ingredients/*")
public class IngredientRestAPI extends DoPatch {
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
            else if(splits[2].equals("prix")){
                String jsonstring = objectMapper.writeValueAsString(e.getPrix());
                out.print(jsonstring);
                return;
            }
            else{
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        out.print(objectMapper.writeValueAsString(e));
        return;
        
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        String autho = req.getHeader("token");
        if (autho==null || !autho.startsWith("Basic")) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        // on décode le token
        String token = autho.substring("Basic".length()).trim();
        byte[] base64 = Base64.getDecoder().decode(token);
        String[] lm = (new String(base64)).split(":");
        String login = lm[0];
        String pwd = lm[1];
        if(!VerifierToken.token(login,pwd)){
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
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
            if(dao.isAlreadyInTable(ingredient.getId())) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if(dao.save(ingredient.getId(), ingredient.getName(), ingredient.getPrix())){
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            out.print(buffer.toString());
            return;
        }
        return;
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException, java.io.IOException {
            String autho = req.getHeader("token");
            if (autho==null || !autho.startsWith("Basic")) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            // on décode le token
            String token = autho.substring("Basic".length()).trim();
            byte[] base64 = Base64.getDecoder().decode(token);
            String[] lm = (new String(base64)).split(":");
            String login = lm[0];
            String pwd = lm[1];
            if(!VerifierToken.token(login,pwd)){
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            
        res.setContentType("application/json;charset=UTF-8");
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
        if(dao.isAlreadyInTable(id)) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (dao.delete(id)) {
            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {
        String autho = req.getHeader("token");
        if (autho==null || !autho.startsWith("Basic")) {
            System.out.println("Test");
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        // on décode le token
        String token = autho.substring("Basic".length()).trim();
        byte[] base64 = Base64.getDecoder().decode(token);
        String[] lm = (new String(base64)).split(":");
        String login = lm[0];
        String pwd = lm[1];
        if(!VerifierToken.token(login,pwd)){
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        

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
        if(dao.isAlreadyInTable(id)) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        if (dao.findById(id).getId() == 0) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(splits.length ==2){
            String payload = buffer.toString();

            @SuppressWarnings("unchecked")
            Map<String, String> jsonData = objectMapper.readValue(payload, Map.class);
            String prixString = jsonData.get("prix");
            int prix;
            if(prixString==null) {
                prix = -1;
            }
            else {
                prix = Integer.parseInt(prixString);
            }
            String name = jsonData.get("name");
            dao.patch(id, prix, name);
            out.print(objectMapper.writeValueAsString(dao.findById(id)));
            return;
            
        }
        return;
    }    

}