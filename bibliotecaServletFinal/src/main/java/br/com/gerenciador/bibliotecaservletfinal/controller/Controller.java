package br.com.gerenciador.bibliotecaservletfinal.controller;

import br.com.gerenciador.bibliotecaservletfinal.dao.LivroDao;
import br.com.gerenciador.bibliotecaservletfinal.dao.UsuarioDao;
import br.com.gerenciador.bibliotecaservletfinal.model.Livro;
import br.com.gerenciador.bibliotecaservletfinal.model.Usuario;
import br.com.gerenciador.bibliotecaservletfinal.service.UserAuthenticator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/novoUsuario", "/home", "/cadastrar", "/atualizar", "/excluir"})
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LivroDao livroDao;
    private UsuarioDao usuarioDao;

    public void init() throws ServletException {
        livroDao = new LivroDao();
        usuarioDao = new UsuarioDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        UserAuthenticator authenticator = new UserAuthenticator();
        Usuario usuario;
        switch (action) {
            case "/login": {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                usuario = new Usuario("", email, password);
                var authUsuario = authenticator.authenticate(usuario);
                if (authUsuario != null) {
                    request.getSession().setAttribute("usuario", authUsuario);
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
            case "/novoUsuario": {
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                usuario = new Usuario(nome,email,password);
                Usuario alreadyInUse = usuarioDao.findUsuarioByEmail(usuario.getEmail());
                if(alreadyInUse == null){
                    usuarioDao.cadastrar(usuario);
                    response.sendRedirect("index.jsp");
                }else{
                    request.getRequestDispatcher("newUser.jsp").forward(request, response);
                }
                break;
            }
            case "/cadastrar": {
                String isbn = request.getParameter("isbn");
                String categoria = request.getParameter("categoria");
                String titulo = request.getParameter("titulo");
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));

                Livro livro= new Livro(isbn,categoria,titulo,quantidade);
                Livro alreadyInUse = livroDao.findLivroByIsbn(livro.getIsbn());
                if(alreadyInUse == null){
                    livroDao.cadastrar(livro);
                    response.sendRedirect("home.jsp");
                }else{
                    request.getRequestDispatcher("editBook.jsp").forward(request, response);
                }
                break;
            }
            case "/atualizar": {
                atualizarLivro(request, response);
                break;
            }
            default: {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/excluir": {
                String isbn = request.getParameter("isbn");
                livroDao.remover(isbn);
                response.sendRedirect(request.getContextPath() + "/home.jsp");
                break;
            }
            default: {
                request.getRequestDispatcher("home.jsp").forward(request, response);
                break;
            }
        }
    }

    private void atualizarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String categoria = request.getParameter("categoria");
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));

        Livro livro = new Livro(isbn, titulo, categoria, quantidade);
        livroDao.atualizar(livro);
        response.sendRedirect(request.getContextPath() + "/home.jsp");
    }

}
