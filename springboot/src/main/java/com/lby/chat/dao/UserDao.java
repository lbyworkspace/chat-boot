package com.lby.chat.dao;

import com.lby.chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: laishao
 * Date: 2022/5/20
 */
@Repository
public interface UserDao extends JpaRepository<User,Long> {

    Optional<User> findByAccount(@Param("account")String account);
}
