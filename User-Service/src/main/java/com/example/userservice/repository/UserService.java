package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

@Service
public class UserService {
    private UserRepository userRepository;

    @RabbitListener(queues = "Status")
    public void listener(String id) throws IOException
    {
        System.out.println("Id "+id+ " Can go With PAAPAI Tour");
        User user = new User();
        user = userRepository.findById(id).get();
        String state = "true";
        user.setState(state);
        userRepository.save(user);

    }

    @RabbitListener(queues = "StatusNotpass")
    public void Notpass(String id) {

        System.out.println("Id "+id+ " Can Not go With PAAPAI Tour");
        User user = new User();
        user = userRepository.findById(id).get();
        String state = "false";
        user.setState(state);
        userRepository.save(user);
    }

    @Autowired

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String save(String firstname, String lastname, String phone, String email, String annotation, Integer total_tourist, Double tour_price, String tour_name, String province, LocalDate date) {
        User user = new User();
        String uuid = randomAlphabetic(10);
        user.setUuid(uuid);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAnnotation(annotation);
        user.setTotal_tourist(total_tourist);
        user.setTour_price(tour_price);
        user.setTour_name(tour_name);
        user.setProvince(province);
        user.setDate(date);
        user.setStatus("");
        user.setState("");
        userRepository.save(user);
        return uuid;
    }

    public Iterable<User> findByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
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
