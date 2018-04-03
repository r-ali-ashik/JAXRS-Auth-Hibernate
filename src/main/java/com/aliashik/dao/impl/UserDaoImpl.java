package com.aliashik.dao.impl;

import com.aliashik.dao.UserDao;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class UserDaoImpl implements UserDao{

    @Autowired
    private Logger logger;

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public Map getUserByUsername(String username) {
        SQLQuery query = sessionFactory
                .getCurrentSession()
                .createSQLQuery("SELECT U.USER_ID as userId, U.PASSWORD as password FROM tbl_USER U WHERE U.USER_NAME=:username");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        query.setParameter("username", username);
        query.setCacheable(true);
        return (Map) query.uniqueResult();
    }

    @Override
    public List<String> getUserRolesByUserID(Integer userId) {
        SQLQuery query = sessionFactory
                .getCurrentSession()
                .createSQLQuery("SELECT R.ROLE_NAME as role FROM tbl_ROLE R LEFT JOIN tbl_USER_ROLE UR ON R.ROLE_ID=UR.ROLE_ID WHERE UR.USER_ID=:userID");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        query.setParameter("userID",userId);
        query.setCacheable(true);


        List<String> roles = new ArrayList<>();
        // converting List<Map<String>> to List<String>
        query.list().forEach(roleMap -> {
            roles.addAll(((Map)roleMap).values());
        });
        return roles;
    }
}
