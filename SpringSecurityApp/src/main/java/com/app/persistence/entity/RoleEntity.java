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
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  // mepear  la tabla que se creará como (role_permisions ), lla clave (role_id) y su foranea ( permision_id)
    @JoinTable (name =" role_permissions" , joinColumns = @JoinColumn (name = "role_id" ), inverseJoinColumns =  @JoinColumn (name = "permission_id") )
    private Set<PermissionEntity> permisionList = new HashSet<>();
}
