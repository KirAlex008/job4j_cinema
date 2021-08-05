package ru.job4j.cinema.servlets;

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
import java.util.Collection;

public class AccountSaveServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Account account = GSON.fromJson(req.getReader(), Account.class);
        System.out.println(account.toString() + "Chek");
        req.setCharacterEncoding("UTF-8");
        int response  = PsqlStore.instOf().createAccount(account);
        resp.getWriter().print(response);
    }
}
