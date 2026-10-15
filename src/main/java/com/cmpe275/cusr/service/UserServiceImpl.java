package com.cmpe275.cusr.service;

import com.cmpe275.cusr.dao.UserDao;
import com.cmpe275.cusr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) throws Exception {
        // TODO Auto-generated method stub
        try {

            userDao.save(user);

        } catch (DataIntegrityViolationException e) {

            throw new DataIntegrityViolationException(null, null);

        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public void deleteUser(long id) throws Exception {
        // TODO Auto-generated method stub
        try {

            userDao.deleteById(id);

        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public User updateUser(User user) throws Exception {
        // TODO Auto-generated method stub
        try {

            return userDao.save(user);

        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public User getUser(long id) throws Exception {
        // TODO Auto-generated method stub
        try {
            return userDao.findById(id).orElse(null);

        } catch (Exception e) {

            throw new Exception();
        }
    }

    @Override
    public User findbyemail(String email) throws Exception {
        // TODO Auto-generated method stub
        try {

            User user = userDao.findByemail(email);
            return user;

        } catch (Exception e) {

            throw new Exception();
        }
    }
}
