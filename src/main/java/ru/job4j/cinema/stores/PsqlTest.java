package ru.job4j.cinema.stores;

import ru.job4j.cinema.models.Account;
import ru.job4j.cinema.models.Ticket;

import java.sql.SQLException;

public class PsqlTest {
    public static void main(String[] args) throws SQLException {
        Store store = PsqlStore.instOf();
        //store.createAccount(new Account(1, "Ac1", "8888"));
        //store.createAccount(new Account(1, "A1", "11"));
        //store.createAccount(new Account(2, "A2", "22"));
        //store.createTicket(new Ticket(1, 1, 1, 1));
        //store.createTicket(new Ticket(2, 2, 1, 2));
        boolean rsl = store.createTicket(new Ticket(3, 2, 1, 2));
        //store.createTicket(new Ticket(4, 2, 3, 1));
        //store.createTicket(new Ticket(2, 1, 1, 1));
        //System.out.println(ticket.getId());
        System.out.println(rsl);
        /*Account ac = store.findAccountById(1);
        Ticket tic = store.findTicketById(1);
        System.out.println(ac.getUsername());
        System.out.println(tic.getRow() + " " + tic.getCell());
        for (Ticket ticket1 : store.findAllTickets()) {
            System.out.println(ticket1.getId() + " " + ticket1.getAccountId());
        }*/
    }
}
