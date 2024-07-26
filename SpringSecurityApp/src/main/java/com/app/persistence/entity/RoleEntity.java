package com.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//creando la tabla roles
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //creando la columna role_name
    @Column(name = "role_name")

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    //relacion muchos a muchos con la tabla permissions , con la tabla intermedia role_permissions
    //fetch = FetchType.EAGER, para que traiga los permisos al momento de traer el rol
    //cascade = CascadeType.ALL, para que se hagan las operaciones de guardado, actualizacion y eliminacion en cascada
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    //creado la tabla intermedia role_permissions con las claves foraneas role_id y permission_id
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    //creando la lista de permisos

    private Set<PermissionEntity> permissionList = new HashSet<>();
}
