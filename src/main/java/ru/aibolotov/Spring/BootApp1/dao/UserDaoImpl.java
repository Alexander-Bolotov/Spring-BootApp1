package ru.aibolotov.Spring.BootApp1.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aibolotov.Spring.BootApp1.model.User;
import ru.aibolotov.Spring.BootApp1.repository.UserRepository;


import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getListUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    public boolean userIsExist(User user) {
        return user.getName() != null;
    }

    @Override
    public boolean nameIsExist(String name) {
        List<User> userList = getListUsers();
        List<String> nameList = new ArrayList<>();

        for (User user : userList
        ) {
            nameList.add(user.getName());
        }
        return nameList.contains(name);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(String name) {
        return userRepository.findAllByName(name);
    }
}
