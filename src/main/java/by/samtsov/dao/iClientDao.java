package by.samtsov.dao;

import by.samtsov.model.Client;

import java.util.List;

public interface iClientDao {

    Client findById(int clientId);

    List<Client> GetAll();
}
