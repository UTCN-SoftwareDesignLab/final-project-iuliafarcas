package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserListDTO createUser(@RequestBody UserListDTO userListDTO){
        return userService.create(userListDTO);
    }

    @PatchMapping(ENTITY2)
    public UserListDTO editUser(@PathVariable Long id, @RequestBody UserListDTO userListDTO){
        return userService.update(id, userListDTO);
    }

    @DeleteMapping(ENTITY2)
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll(){
        userService.deleteAll();
    }

}
