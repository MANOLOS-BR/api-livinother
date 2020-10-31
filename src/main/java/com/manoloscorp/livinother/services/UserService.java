package com.manoloscorp.livinother.services;

import com.manoloscorp.livinother.entities.User;
import com.manoloscorp.livinother.entities.UserType;
import com.manoloscorp.livinother.resources.exceptions.NotFoundException;

import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User getUserById(Long id) throws NotFoundException;

  User saveUser(User user);

  User updateUser(Long id, User user);

  Boolean existsUserByEmail(String email);

  void deleteUser(Long id);

  Boolean existsUserById(Long id);

  Long countUserByUserType(UserType value);

}
