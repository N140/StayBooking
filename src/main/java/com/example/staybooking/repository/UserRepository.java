package com.example.staybooking.repository;

import com.example.staybooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
* spring会自动创建一个userRepository class, 自动生成实现方法，也可以自己override
 * */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
