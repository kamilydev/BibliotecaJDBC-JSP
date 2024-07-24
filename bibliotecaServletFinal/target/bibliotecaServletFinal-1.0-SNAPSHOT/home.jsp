<%@ page import="br.com.gerenciador.bibliotecaservletfinal.dao.LivroDao" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.gerenciador.bibliotecaservletfinal.model.Livro" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Central Biblioteca | Home</title>
    <link rel="stylesheet" href="styles/reset.css"/>
    <link rel="stylesheet" href="styles/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./js/deleteBooks.js"></script>
</head>
<body id="body">
<dialog id="modal">
    <h3 id="modal-title">Deseja realmente remover este livro?</h3>
    <button class="button" id="confirm-button" onclick="confirmDelete()">Confirmar</button>
    <button class="button" id="cancel-button" onclick="document.getElementById('modal').close()">Cancelar</button>
</dialog>
<header id="header">
    <a href="#">
        <img id="logo" src="images/logo.png" alt="Logo Biblioteca"/>
    </a>
    <h1 id="header-title">Central Biblioteca</h1>
    <a class="button" id="logout-button" href="index.jsp">Logout</a>
</header>
<section id="section">
    <h1 id="section-title">Lista de Livros</h1>
    <%
        LivroDao livroDao = new LivroDao();
        List<Livro> list = livroDao.listarLivros();
    %>
    <table id="table">
        <tr class="table-row">
            <th class="table-head">ISBN</th>
            <th class="table-head">Título</th>
            <th class="table-head">Categoria</th>
            <th class="table-head">Quantidade</th>
            <th>
                <a class="button" href="newBook.jsp">Novo Livro</a>
            </th>
        </tr>
        <%
            for (Livro livro : list) {
        %>
        <tr class="table-row">
            <td class="table-data"><%= livro.getIsbn() %></td>
            <td class="table-data"><%= livro.getTitulo() %></td>
            <td class="table-data"><%= livro.getCategoria() %></td>
            <td class="table-data"><%= livro.getQuantidade() %></td>
            <td class="table-data">
                <a class="button" href="editBook.jsp?isbn=<%= livro.getIsbn() %>&titulo=<%= livro.getTitulo() %>&categoria=<%= livro.getCategoria() %>&quantidade=<%= livro.getQuantidade() %>">Editar</a>
                <a class="button" href="#" onclick="showModal('<%= livro.getIsbn() %>')">Remover</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</section>
<footer id="footer">
    Danilo Lassabia | Kamily Santos - Projeto Servlet ©️
</footer>
</body>
</html>
