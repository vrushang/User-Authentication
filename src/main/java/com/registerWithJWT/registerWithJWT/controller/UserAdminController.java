package com.registerWithJWT.registerWithJWT.controller;


import com.registerWithJWT.registerWithJWT.entity.User;
import com.registerWithJWT.registerWithJWT.exception.InvalidInputException;
import com.registerWithJWT.registerWithJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="", allowedHeaders="")
public class UserAdminController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private AppointmentService appointmentService;


    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@RequestHeader("authorization") String accessToken,
                                        @PathVariable("id") final String userUuid) {
        final User User = userService.getUser(userUuid);
        return ResponseEntity.ok(User);
    }

    //create a post method named createUser with return type as ResponseEntity
    //define the method parameter user of type User. Set it final. Use @RequestBody for mapping.
    //declare InvalidInputException using throws keyword

    //register the user

    //return http response with status set to OK

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody final User user) throws InvalidInputException {
        User addUser = userService.register(user);
        return new ResponseEntity(addUser, HttpStatus.OK);
    }



//    @GetMapping("/{userId}/appointments")
//    public ResponseEntity getAppointmentForUser(@PathVariable("userId") String userId) {
//        return ResponseEntity.ok(appointmentService.getAppointmentsForUser(userId));
//    }





}
