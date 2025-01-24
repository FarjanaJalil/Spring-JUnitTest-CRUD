package com.example.springboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    // JUnit test for saveUser
    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUserTest(){
        // create user object
        User user = User.builder()
                .firstName("Jessica")
                .lastName("Peter")
                .email("jessica@gmail.com")
                .build();

        userRepository.save(user); // call save method from JpaRepository

        // check condition
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    // JUnit test for getUser
    @Test
    @Order(2)
    public void getUserTest(){
        // get user info by id
        User user = userRepository.findById(1L).get();

        // check condition
        Assertions.assertThat(user.getId()).isEqualTo(1L);
    }

    // JUnit test for getAllUser
    @Test
    @Order(3)
    public void getAllUsersTest(){
        // get all users as a list
        List<User> users = userRepository.findAll();

        // check condition
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }

    // JUnit test for updateUser
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest(){
        // find user to be updated by id
        User user = userRepository.findById(1L).get();

        // update user info
        user.setLastName("Peterson");

        // save in DB table through repository
        User userUpdated = userRepository.save(user);

        // check condition
        Assertions.assertThat(user.getLastName()).isEqualTo("Peterson");
    }

    // JUnit test for deleteUser
    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserTest(){
        // find user to be deleted by id
        User user = userRepository.findById(1L).get();

        // delete user from DB
        userRepository.delete(user);

        // check whether user is deleted or not

        User user1 = null;

        Optional<User> optionalUser = userRepository.findByLastName("Peterson");

        if(optionalUser.isPresent()){
            user1 = optionalUser.get();
        }

        // check condition
        Assertions.assertThat(user1).isNull();
    }

}
