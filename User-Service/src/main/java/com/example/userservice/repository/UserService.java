package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        user.setStatus("");
        return userRepository.save(user);
    }


    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(String id){
        return userRepository.findById(id);
    }

    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }

    public void change(String bank, String id, MultipartFile file) throws IOException
    {
        User user = new User();
        user = userRepository.findById(id).get();
        user.setBank(bank);
        user.setSlip( new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        userRepository.save(user);
    }

    public User getId(String id) {
        return userRepository.findById(id).get();
    }
    public User getUserById(String id){
        Optional<User> optional = userRepository.findById(id);
        User user = null;
        if(optional.isPresent()){
            user = optional.get();
        }else{
            throw new RuntimeException("Tour not found for id ::"+ id);
        }
        return user;
    }

    public void updateStateTrue(String id)
    {
        User t = new User();
        t = userRepository.findById(id).get();
        String state = "true";
        t.setStatus(state);
        userRepository.save(t);
    }

    public Iterable<User> findByNameTour(String name){
        return userRepository.findByNameTour(name);
    }


    public Integer getCount(String tour_name) {
        return userRepository.countUser(tour_name);
    }

    public void updateStateFalse(String id)
    {
        User t = new User();
        t = userRepository.findById(id).get();
        String state = "false";
        t.setStatus(state);
        userRepository.save(t);
    }

    public void changeStateFalse(String id)
    {
        User t = new User();
        t = userRepository.findById(id).get();
        String state = "false";
        t.setStatus(state);
        userRepository.save(t);
    }

    public void changeStateTrue(String id)
    {
        User t = new User();
        t = userRepository.findById(id).get();
        String state = "true";
        t.setStatus(state);
        userRepository.save(t);
    }
}
