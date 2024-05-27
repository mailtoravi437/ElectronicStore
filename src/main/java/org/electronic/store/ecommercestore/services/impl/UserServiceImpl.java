package org.electronic.store.ecommercestore.services.impl;

import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.dtos.UserDTO;
import org.electronic.store.ecommercestore.entities.User;
import org.electronic.store.ecommercestore.exceptions.ResourceNotFoundException;
import org.electronic.store.ecommercestore.helper.Helper;
import org.electronic.store.ecommercestore.repositories.UserRepository;
import org.electronic.store.ecommercestore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDTO createUser(UserDTO userDto) {
        String userId = UUID.randomUUID().toString();
    //   User user = dtoToEntity(userDto);
        User user = modelMapper.map(userDto, User.class);
       user.setUserId(userId);
       User savedUser = userRepository.save(user);
 //      UserDTO newDto = entityToDto(savedUser);
        UserDTO newDto = modelMapper.map(savedUser, UserDTO.class);
       return newDto;
    }


    @Override
    public UserDTO updateUser(UserDTO userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
        UserDTO updatedUser = entityToDto(user);
        return updatedUser;
    }

    @Override
    public void deleteUser(String userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));


        String fullPath = imagePath+user.getImageName();
        Path path = Path.of(fullPath);
        Files.delete(path);
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDTO> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equals("ASC")? Sort.Direction.ASC:Sort.Direction.DESC,sortBy);
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDTO> response = Helper.getPageableResponse(page, UserDTO.class);
        return response;
    }

    @Override
    public UserDTO getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        UserDTO userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        UserDTO userDto = entityToDto(user);
        return userDto;
    }

    @Override
    public List<UserDTO> searchUser(String keyword) {
        Optional<List<User>> user = userRepository.findByNameContaining(keyword);
        List<UserDTO> users = user.stream().map(u->entityToDto((User) u)).collect(Collectors.toList());
        return users;
    }


    private UserDTO entityToDto(User savedUser) {
        UserDTO userDto = modelMapper.map(savedUser, UserDTO.class);
        return userDto;
    }

    private User dtoToEntity(UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
