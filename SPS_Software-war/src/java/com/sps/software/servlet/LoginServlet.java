package com.sps.software.servlet;

import com.sps.entity.*;
import com.sps.sessionBean.ClienteFacadeLocal;
import com.sps.sessionBean.PersonaFacadeLocal;
import com.sps.sessionBean.UsuarioFacadeLocal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 *
 * @author Juan Pablo
 */
public class LoginServlet extends HttpServlet {

    @EJB
    private PersonaFacadeLocal sessioBeanPersona;
    @EJB
    private UsuarioFacadeLocal sessionBeanUsuario;
    @EJB
    private ClienteFacadeLocal sessionBeanCliente;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email != null || password != null) {

            HttpSession sesion = request.getSession(true);
            Persona persona = sessioBeanPersona.findLogin(email, password);

            if (persona != null) {
                List<Usuario> usuarios = sessionBeanUsuario.findByCedula(persona);
                List<Cliente> clientes = sessionBeanCliente.findByCedula(persona);

                ArrayList<Object> mapPerfiles = new ArrayList<>();

                int total = usuarios.size() + clientes.size();
                if (total > 1) {

                    for (Usuario usuario : usuarios) {
                        mapPerfiles.add(usuario);
                    }

                    for (Cliente cliente : clientes) {
                        mapPerfiles.add(cliente);
                    }

                    sesion.setAttribute("persona", persona);
                    request.setAttribute("perfiles", mapPerfiles);
                    request.getRequestDispatcher("seleccion.jsp").forward(request, response);
                } else {

                    Object perfil = null;

                    if (usuarios.isEmpty()) {
                        perfil = clientes.get(0);
                    } else if (clientes.isEmpty()) {
                        perfil = usuarios.get(0);
                    }

                    sesion.setAttribute("perfil", perfil);

                    response.sendRedirect("InicioServlet");
//            request.getRequestDispatcher("InicioServlet").forward(request, response);
                }
            }
        } else {
            response.sendRedirect("index.jsp");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


/*
PERSON{
    ID - CEDULA
    NAME
    PASSWORD
    EMAIL
    PHONE
}

USUARIO{
    PLACA
    MODELO
    MARCA
    ID-PROPIEDAD
}

CLIENT{
    ADDRESS
    CUPOS
    SCHEDULE START
    SCHEDULE END
}


ADMIN{
    ID-PROPIEDAD
    

}

LD_SPS_DB
ldspsdb
 */
