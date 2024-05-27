package org.electronic.store.ecommercestore.config;

import org.electronic.store.ecommercestore.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig{

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails normal = (UserDetails) User.builder().name("admin").password("admin").build();
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return normal;
            }
        };

    }

}
