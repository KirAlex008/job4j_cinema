package ru.job4j.cinema.stores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.cinema.models.Account;
import ru.job4j.cinema.models.Ticket;

public class PsqlStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Ticket> findAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM ticket")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(it.getInt("id"), it.getInt("row"), it.getInt("cell"), it.getInt("account_id")));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return tickets;
    }

    @Override
    public int createAccount(Account account) {
        int rsl = -1;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO account (username, phone) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                    rsl = id.getInt(1);
                    System.out.println(rsl + "Check Id");
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return rsl;
    }

    @Override
    public boolean createTicket(Ticket ticket) {
        boolean rsl = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO ticket (row, cell, account_id ) VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getRow());
            ps.setInt(2, ticket.getCell());
            ps.setInt(3, ticket.getAccountId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                    rsl = true;
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return rsl;
    }

    @Override
    public Account findAccountById(int id) {
        Account account = null;
        try (Connection cn = pool.getConnection();
            PreparedStatement st = cn.prepareStatement("SELECT * FROM account where id=?")) {
            st.setInt(1, id);
            try (ResultSet it = st.executeQuery()) {
                if (it.next()) {
                    account = new Account(it.getInt("id"), it.getString("username"));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return account;
    }

    @Override
    public Ticket findTicketById(int id) {
        Ticket ticket = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement("SELECT * FROM ticket where id=?")) {
            st.setInt(1, id);
            try (ResultSet it = st.executeQuery()) {
                if (it.next()) {
                    ticket = new Ticket(it.getInt("id"), it.getInt("row"), it.getInt("cell"), it.getInt("account_id"));
                }
            }
        } catch (Exception e) {
            LOG.error("Connection is not correct", e);
        }
        return ticket;
    }


}
