package com.app.service;

import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
/*
* UserDetailServiceImpl, implementa la interfaz UserDetailsService
* para cargar los datos del usuario y sus roles y permisos
* para enviarlos al AuthenticationManager (creo)
* UserEntity, entidad que representa al usuario
* UserRepository, repositorio que se encarga de buscar al usuario
* loadUserByUsername, metodo que carga los datos del usuario
* username, nombre de usuario
* UsernameNotFoundException, excepcion que se lanza si el usuario no existe
* authorityList, lista de roles y permisos
* userEntity, objeto que representa al usuario
*
*
* */
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //cargando los roles
        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        //cargando los permisos
        userEntity.getRoles().forEach(role -> role.getPermissionList()
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName()))));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }
}
