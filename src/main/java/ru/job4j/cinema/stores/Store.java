package ru.job4j.cinema.stores;

import ru.job4j.cinema.models.Account;
import ru.job4j.cinema.models.Ticket;

import java.util.Collection;

public interface Store {

    Collection<Ticket> findAllTickets();

    int createAccount(Account account);

    boolean createTicket(Ticket ticket);

    Account findAccountById(int id);

    Ticket findTicketById(int id);
}
