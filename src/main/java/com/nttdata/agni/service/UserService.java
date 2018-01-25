package com.nttdata.agni.service;

import com.nttdata.agni.domain.User;
import com.nttdata.agni.dao.jpa.UserRepository;
import com.nttdata.agni.dao.jpa.MappingListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    
   
    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public UserService() {
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    
    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<User> getAllUsers(Integer page, Integer size) {
        Page pageOfUsers = userRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("nttdata.UserService.getAll.largePayload");
        }
        return pageOfUsers;
    }
}
