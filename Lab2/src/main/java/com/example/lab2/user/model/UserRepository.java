package com.example.lab2.user.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository

public interface UserRepository extends JpaRepository<UserDTO, Integer > {
    @Query(value = "SELECT * FROM user u WHERE u.name LIKE %:name% or u.email like %:email% limit :offset,:limit", nativeQuery = true)
    List< UserDTO> getListUserByRequest(@Param("name")String name,@Param("email")String email,@Param("limit")Integer limit,@Param("offset")Integer offset);
    @Query(value = "SELECT * FROM user u WHERE u.name LIKE %:name% or u.email like %:email% ", nativeQuery = true)
    List< UserDTO> findListUserByRequest(@Param("name")String name,@Param("email")String email);
    @Query(value = "SELECT * FROM user u WHERE u.username = :username and u.password= :password ", nativeQuery = true)
    List<UserDTO> login(@Param("username")String username,@Param("password")String password);
    List<UserDTO> findByCmnd(String cmnd);
    List<UserDTO> findByUsername(String username);
    List< UserDTO> findAllByEmailContains(String email);
    Page< UserDTO> findAllByNameContains(String name,Pageable pageable);
    Page< UserDTO> findAllByNameOrEmailContains(String name,String email,Pageable pageable);
    @Query(value = "SELECT * FROM user WHERE user.name like %:name% or user.email like %:email% ", nativeQuery = true)
    Page< UserDTO> pageFindListUserByRequest(@Param("name")String name, @Param("email")String email, Pageable pageable);
}
