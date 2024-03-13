package controleurs;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.sql.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.CommandeDAODatabase;
import dto.Commande;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/commande/*")
public class CommandeRestAPI extends HttpServlet {
    CommandeDAODatabase dao = new CommandeDAODatabase();

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, java.io.IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            Collection<Commande> l = dao.findAll();
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
        Commande e = dao.findById(id);
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
            if(splits[2].equals("prixfinal")){
                String jsonstring = objectMapper.writeValueAsString(dao.getPrixFinal(e));
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
            Commande commande = objectMapper.readValue(buffer.toString(), Commande.class);
            commande.setDate(new Date(System.currentTimeMillis()));
            dao.save(commande);
            out.print(buffer.toString());
            return;
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