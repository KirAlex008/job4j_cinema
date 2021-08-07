package ru.job4j.cinema.servlets;

import javax.servlet.http.HttpServlet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cinema.models.Account;
import ru.job4j.cinema.models.Ticket;
import ru.job4j.cinema.stores.PsqlStore;
import ru.job4j.cinema.stores.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Collection;

public class TicketSaveServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Ticket ticket = GSON.fromJson(req.getReader(), Ticket.class);
        System.out.println(ticket.toString() + "Chek");
        req.setCharacterEncoding("UTF-8");
        String response = null;
        try {
            response = PsqlStore.instOf().createTicket(ticket) ? "success" : "fail";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resp.getWriter().print(response);
    }
}
