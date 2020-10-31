package com.manoloscorp.livinother.resources;

import com.manoloscorp.livinother.entities.User;
import com.manoloscorp.livinother.resources.payload.response.UserResponse;
import com.manoloscorp.livinother.services.UserServiceImpl;
import com.manoloscorp.livinother.shared.RestConstants;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = RestConstants.APPLICATION_API + RestConstants.PROFILE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileResource {

  private final UserServiceImpl userService;
  private final ModelMapper mapper;

  public ProfileResource(UserServiceImpl userService, ModelMapper mapper) {
    this.userService = userService;
    this.mapper = mapper;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProfileById(@PathVariable Long id) {

    User user = userService.getUserById(id);

    UserResponse userResponse = mapper.map(user, UserResponse.class);

    return ResponseEntity.ok().body(userResponse);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<UserResponse> updateProfile(@PathVariable Long id, @RequestBody UserResponse userResquest) {
    User user = userService.getUserById(id);
    mapper.map(userResquest, user);
    userService.updateUser(id, user);
    return ResponseEntity.ok().body(userResquest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProfile(@PathVariable Long id){
    userService.deleteUser(id);
  }
}
