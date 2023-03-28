package de.zuse.hotel.db;

import de.zuse.hotel.core.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class AdressConnecter implements DatabaseOperations
{

    @Override
    public void dbCreate(Object object)
    {
        if (object instanceof Address)
        {
            EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
            Address address = (Address) object;
            manager.getTransaction().begin();
            manager.persist(address);
            manager.getTransaction().commit();
            manager.close();
        }
    }

    @Override
    public List<?> dbsearchAll()
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        List<Address> oneAddresses = manager.createNativeQuery("SELECT id FROM address", Address.class).getResultList();
        manager.getTransaction().commit();
        manager.close();
        return oneAddresses;
    }

    @Override
    public <T> T dbsearchById(int id)
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        Address address = manager.find(Address.class, id);
        manager.getTransaction().commit();
        manager.close();
        return (T) address;
        // TODO: this need to be changed latter pls by Basel
    }

    @Override
    public void dbRemoveAll()
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.createNativeQuery("INSERT INTO address_trash_collection SELECT * FROM address").executeUpdate();
        manager.createNativeQuery("DELETE FROM address").executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void dbRemoveById(int id)
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.createNativeQuery("INSERT INTO address_trash_collection SELECT * FROM address WHERE Id = :id")
                .setParameter("id", id)
                .executeUpdate();
        manager.createNativeQuery("DELETE FROM address WHERE Id = :id")
                .setParameter("id", id)
                .executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void dbUpdate(Object object)
    {
        if (object instanceof Address)
        {
            EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
            Address address = (Address) object;
            System.out.println((Address) dbsearchById(address.getId()));
            manager.getTransaction().begin();
            manager.merge(address);
            manager.getTransaction().commit();
            manager.close();
        }
    }

    @Override
    public List<?> dbSerscheforanythinhg(String searchTerm)
    { // jan
        String query = "SELECT * FROM address WHERE ";
        query += "Id = ?1 OR ";
        query += "Address LIKE ?2 OR ";
        query += "City LIKE ?2 OR ";
        query += "State LIKE ?2 OR ";
        query += "PLZ = ?3 OR "; // ZIP = PLZ

        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        Query nativeQuery = manager.createNativeQuery(query, Address.class);
        nativeQuery.setParameter(1, Integer.parseInt(searchTerm));
        nativeQuery.setParameter(2, "%" + searchTerm + "%");
        nativeQuery.setParameter(3, Integer.parseInt(searchTerm));
        List<Address> result = nativeQuery.getResultList();
        return result;
    }

}
