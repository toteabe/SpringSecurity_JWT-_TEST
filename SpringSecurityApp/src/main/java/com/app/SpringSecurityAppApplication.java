package com.app;

import com.app.persistence.entity.PermisionEntity;
import com.app.persistence.entity.RoleEntity;
import com.app.persistence.entity.RoleEnum;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.entity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class SpringSecurityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAppApplication.class, args);
    }

    @Bean
    CommandLineRunner intit(UserRepository userRepository) {
        return args -> {

            // Creando Permisos
            PermisionEntity createPermission = PermisionEntity.builder()
                    .name("CREATE").build();

            PermisionEntity deletePermission = PermisionEntity.builder()
                    .name("DELETE").build();

            PermisionEntity updatePermission = PermisionEntity.builder()
                    .name("UPDATE").build();

            PermisionEntity readPermission = PermisionEntity.builder()
                    .name("READ").build();

            PermisionEntity reafactorPermission = PermisionEntity.builder()
                    .name("REFACTOR").build();

            //Create Roles
            RoleEntity roleAdmin = RoleEntity.builder().roleEnum(RoleEnum.ADMIN)
                    .permisionList(Set.of(createPermission, readPermission, deletePermission, updatePermission)).build();

            RoleEntity roleUser = RoleEntity.builder()
                    .roleEnum(RoleEnum.USER)
                    .permisionList(Set.of(createPermission, readPermission))
                    .build();

            RoleEntity roleInvited = RoleEntity.builder()
                    .roleEnum(RoleEnum.INVITED)
                    .permisionList(Set.of(readPermission))
                    .build();

            RoleEntity roleDeveloper = RoleEntity.builder()
                    .roleEnum(RoleEnum.DEVELOPER)
                    .permisionList(Set.of(readPermission, createPermission, reafactorPermission, updatePermission, deletePermission))
                    .build();


            //Creando  Usuarios

            UserEntity userCesar = UserEntity.builder()
                    .ussername("Cesar")
                    .password("1234")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .roles(Set.of(roleDeveloper))
                    .build();

            UserEntity userGabriel = UserEntity.builder()
                    .ussername("Gabriel")
                    .password("1234")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .roles(Set.of(roleUser))
                    .build();

            UserEntity userInvitado = UserEntity.builder()
                    .ussername("Invitado")
                    .password("12345")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .roles(Set.of(roleInvited))
                    .build();

            userRepository.saveAll(Set.of(userCesar, userGabriel, userInvitado));
        };


    }
}


