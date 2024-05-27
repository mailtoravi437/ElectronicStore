package org.electronic.store.ecommercestore.services;

import org.electronic.store.ecommercestore.dtos.ImageResponse;
import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.dtos.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDto);
    UserDTO updateUser(UserDTO userDto,String userId);
    void deleteUser(String userId) throws IOException;
    PageableResponse<UserDTO> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
    UserDTO getUserById(String userId);
    UserDTO getUserByEmail(String email);
    List<UserDTO> searchUser(String keyword);
}
