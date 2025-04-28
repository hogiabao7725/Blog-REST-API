package com.hgb7725.blog.service;

import com.hgb7725.blog.payload.LoginDTO;
import com.hgb7725.blog.payload.RegisterDTO;

public interface AuthService {

    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);

}
