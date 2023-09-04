package by.samtsov.service;

import by.samtsov.dao.postgreSQL.ClientDao;
import by.samtsov.model.Client;

public class ClientService {
    public Client getClientById(Integer clientId) {
        return new ClientDao().findById(clientId);
    }
}
