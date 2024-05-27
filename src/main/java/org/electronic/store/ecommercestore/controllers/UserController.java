package org.electronic.store.ecommercestore.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.electronic.store.ecommercestore.dtos.ApiResponseMessage;
import org.electronic.store.ecommercestore.dtos.ImageResponse;
import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.dtos.UserDTO;
import org.electronic.store.ecommercestore.services.FileService;
import org.electronic.store.ecommercestore.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto){
        UserDTO userDTO = userService.createUser(userDto);
        logger.info("User created successfully. {}",userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @PathVariable("userId") String userId, @RequestBody UserDTO userDto){
        UserDTO userDTO = userService.updateUser(userDto,userDto.getUserId());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) throws IOException {
        userService.deleteUser(userId);
        ApiResponseMessage message =  ApiResponseMessage.builder().message("User deleted successfully").success(true).status(HttpStatus.NO_CONTENT).build();
        return new ResponseEntity<>(message,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<PageableResponse<UserDTO>> getAllUsers(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                                              @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                                              @RequestParam(value = "sortBy",defaultValue = "ma,e",required = false) String sortBy,
                                                              @RequestParam(value = "sortDir",defaultValue = "ASC",required = false) String sortDir){


        return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("email") String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @GetMapping("search/{keywords}")
    public ResponseEntity<List<UserDTO>> searchUsers(@PathVariable("keywords") String keywords){
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile file,@PathVariable String userId){
        String imageName = fileService.uploadUserImage( file,imageUploadPath);
        UserDTO user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDTO updateUserImage = userService.updateUser(user,userId);
        ImageResponse response = ImageResponse.builder().imageName(imageName).message("Image uploaded successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDTO user = userService.getUserById(userId);
        logger.info("User Image Name -: {}",user.getImageName());
        InputStream resource =  fileService.getResource(imageUploadPath,user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());
    }

}
