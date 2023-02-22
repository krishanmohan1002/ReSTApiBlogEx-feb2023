package com.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
