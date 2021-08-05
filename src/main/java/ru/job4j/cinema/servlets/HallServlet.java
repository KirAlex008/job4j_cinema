package ru.job4j.cinema.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cinema.models.Account;
import ru.job4j.cinema.models.Ticket;
import ru.job4j.cinema.stores.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

public class HallServlet extends HttpServlet {
    //private final Collection<Ticket> hall = PsqlStore.instOf().findAllTickets();

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        Collection<Ticket> hall = PsqlStore.instOf().findAllTickets();
        for (var el : hall) {
            System.out.println(el.toString());
        }
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(hall);
        System.out.println(json);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Account account = new Gson().fromJson(req.getReader().readLine(), Account.class);
        PsqlStore.instOf().createAccount(account);
        //resp.getWriter().print(response);
    }


}
