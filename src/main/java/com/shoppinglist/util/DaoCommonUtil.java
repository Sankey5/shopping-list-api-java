package com.shoppinglist.util;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;

public class DaoCommonUtil {
    //TODO: Combine all DAO related helpers to this class
    public static Session getConnection(EntityManager entityManager) {
        Session session = null;
        if (entityManager == null
                || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException("Unable to get a connection from the entity manager");
        }
        return session;
    }
}
