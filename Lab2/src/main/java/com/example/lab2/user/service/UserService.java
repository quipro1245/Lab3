package com.example.lab2.user.service;


import com.example.lab2.user.model.PageUser;
import com.example.lab2.user.model.UserDTO;
import com.example.lab2.user.model.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<UserDTO> listAllUser() {
        return userRepository.findAll();
    }
    public Page<UserDTO> findAll(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(pageable);
    }
    public void saveUser(UserDTO user) {
        userRepository.save(user);
    }

    public UserDTO getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> getListUserByRequest(String name,String email,Integer limit,Integer offset){
        if(limit==null)
            limit =20;
        if (offset==null||offset==1||offset==-1||offset==0)
            offset = 0;
        else {
            offset = (offset-1)*limit;
        }
        if(StringUtils.isBlank(email))
            email=null;
        if(StringUtils.isBlank(name))
            name =null;
        return userRepository.getListUserByRequest(name,email,limit,offset);
    }

    public List<UserDTO> findAllByEmailContains(String email){
        return userRepository.findAllByEmailContains(email);
    }
    public Page<UserDTO> findAllByNameContains(String email){
        Pageable pageable = PageRequest.of(0, 2);
        return userRepository.findAllByNameContains(email,pageable);
    }
    public Page<UserDTO> findAllByNameOrEmailContains(String name,String email){
        Pageable pageable = PageRequest.of(0, 2);
        return userRepository.findAllByNameOrEmailContains(name, email,pageable);
    }
    public List<UserDTO> login(String username, String password){
        return userRepository.login(username,password);
    }

    public List<UserDTO> findListUserByRequest(String name,String email){
        return  userRepository.findListUserByRequest(name,email);
    }
    public List<UserDTO> findByUsername(String username){
        return  userRepository.findByUsername(username);
    }
    public List<UserDTO> findByCmnd(String cmnd){
        return  userRepository.findByCmnd(cmnd);
    }
    public List<UserDTO> loginConfig(String username,String password){
        return  userRepository.login(username, password);
    }
    public Page<UserDTO> paging(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(pageable);
    }
    public Page<UserDTO> pageFindListUserByRequest(String name,String email,Integer pageNumber, Integer pageSize){
        if(StringUtils.isBlank(name))
            name=null;
        if(StringUtils.isBlank(email))
            email=null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return  userRepository.pageFindListUserByRequest(name,email,pageable);
    }
}
