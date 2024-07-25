package com.app.service;

import com.app.persistence.entity.RoleEntity;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(" El pibe " + username + "  no existe"));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userEntity.getRoles()
                .forEach(roleEntity -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleEnum().name()))));

        // ahora los roles
        userEntity.getRoles().forEach(roleEntity ->
                roleEntity.getPermisionList().forEach(permision ->
                        authorityList.add(new SimpleGrantedAuthority(permision.getName()))
                )
        );

        // otra manera
//        userEntity.getRoles().stream()
//                .flatMap(role -> role.getPermisionList().stream())
//                .forEach(permision -> authorityList.add(new SimpleGrantedAuthority(permision.getName())));


//        al final lo que estamos haciendo es iterar una lista que esta posse otra en ella
//         es igual a la que haciamos anteiormente con los iteradores y while Ej:

//        Iterator<RoleEntity> roleIterator = userEntity.getRoles().iterator();
//        while (roleIterator.hasNext()) {
//            RoleEntity roleEntity = roleIterator.next();
//            authorityList.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleEnum().name()));
//
//            // Aqui creamos el segundo iterador
//            Iterator<Permission> permissionIterator = roleEntity.getPermisionList().iterator();
//            while (permissionIterator.hasNext()) {
//                Permission permission = permissionIterator.next();
//                authorityList.add(new SimpleGrantedAuthority(permission.getName()));
//            }

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }



    // Esto  para hacer entender al User de SpringSecurity
    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     * @param username the username presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param enabled set to <code>true</code> if the user is enabled
     * @param accountNonExpired set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     * expired
     * @param accountNonLocked set to <code>true</code> if the account is not locked
     * @param authorities the authorities that should be granted to the caller if they
     * presented the correct username and password and the user is enabled. Not null.
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     * a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
//    public User(String username, String password, boolean enabled, boolean accountNonExpired,
//                boolean credentialsNonExpired, boolean accountNonLocked,
//                Collection<? extends GrantedAuthority> authorities) {
//        Assert.isTrue(username != null && !"".equals(username) && password != null,
//                "Cannot pass null or empty values to constructor");
//        this.username = username;
//        this.password = password;
//        this.enabled = enabled;
//        this.accountNonExpired = accountNonExpired;
//        this.credentialsNonExpired = credentialsNonExpired;
//        this.accountNonLocked = accountNonLocked;
//        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
//    }
}