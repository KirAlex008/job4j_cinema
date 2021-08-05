package ru.job4j.cinema.stores;

import ru.job4j.cinema.models.Account;
import ru.job4j.cinema.models.Ticket;

public class PsqlTest {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.createAccount(new Account(1, "Ac1", "8888"));
        store.createTicket(new Ticket(1, 3,3,4));
        store.createTicket(new Ticket(2, 1,1,1));
        store.createTicket(new Ticket(3, 2,1,1));
        store.createTicket(new Ticket(4, 2,3,1));
        //System.out.println(ticket.getId());
        Account ac = store.findAccountById(1);
        Ticket tic = store.findTicketById(1);
        System.out.println(ac.getUsername());
        System.out.println(tic.getRow() + " " + tic.getCell());
        for (Ticket ticket1 : store.findAllTickets()) {
            System.out.println(ticket1.getId() + " " + ticket1.getAccount_id());
        }
    }
}
