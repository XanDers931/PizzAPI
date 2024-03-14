package controleurs;

import java.util.Base64;

import dao.VerifierToken;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users/token")
public class Authent extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        // Récupération du login de la personne
            // Affichage du titre de la page
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Authent</title>");
            out.println("<link rel=\"stylesheet\" href=\"style.css\">");
            out.println("</head>");
            out.println("<body>");

            // Affichage du formulaire
            out.println("<form action=\"\" method=\"post\" required><br>");
            out.println("Login : <input type=\"text\" name=\"login\" required><br>");
            out.println("Mot de passe : <input type=\"text\" name=\"mdp\" required><br>");
            out.println("<input type=\"submit\" value=\"Récupérer mon token\" required><br>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
            String login= request.getParameter("login");
            String password = request.getParameter("mdp");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println( "<head><title>Ton token</title>" );
            out.println( "<META content=\"charset=UTF-8\"></head><body><h1>GENERER TOKEN</h1>" );
            if(VerifierToken.token(login, password)){
                String str = login+":"+password;
                String token = Base64.getEncoder().encodeToString(str.getBytes());
                out.println("<a>Ton token: " + token+"</a>");
            }
            else{
                out.println("<a>Vous etes inconnu à la base</a>");
            }
            out.println("</body></html>");
    }
}
