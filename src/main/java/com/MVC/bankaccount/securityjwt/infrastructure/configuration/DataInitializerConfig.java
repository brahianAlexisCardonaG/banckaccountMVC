package com.MVC.bankaccount.securityjwt.infrastructure.configuration;

import com.MVC.bankaccount.securityjwt.domain.enums.RoleEnum;
import com.MVC.bankaccount.securityjwt.infrastructure.entities.PermissionEntity;
import com.MVC.bankaccount.securityjwt.infrastructure.entities.RoleEntity;
import com.MVC.bankaccount.securityjwt.infrastructure.entities.UserEntity;
import com.MVC.bankaccount.securityjwt.infrastructure.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializerConfig implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializerConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Si ya hay usuarios, no se inicializan de nuevo.
        }

        /* Create PERMISSIONS */
        PermissionEntity createPermission = new PermissionEntity();
        createPermission.setName("CREATE");

        PermissionEntity readPermission = new PermissionEntity();
        readPermission.setName("READ");

        PermissionEntity updatePermission = new PermissionEntity();
        updatePermission.setName("UPDATE");

        PermissionEntity deletePermission = new PermissionEntity();
        deletePermission.setName("DELETE");

        PermissionEntity refactorPermission = new PermissionEntity();
        refactorPermission.setName("REFACTOR");

        /* Create ROLES */
        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setRoleEnum(RoleEnum.ADMIN);
        roleAdmin.setPermissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission));

        RoleEntity roleUser = new RoleEntity();
        roleUser.setRoleEnum(RoleEnum.USER);
        roleUser.setPermissionList(Set.of(createPermission, readPermission));

        RoleEntity roleInvited = new RoleEntity();
        roleInvited.setRoleEnum(RoleEnum.INVITED);
        roleInvited.setPermissionList(Set.of(readPermission));

        RoleEntity roleDeveloper = new RoleEntity();
        roleDeveloper.setRoleEnum(RoleEnum.DEVELOPER);
        roleDeveloper.setPermissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission));

        /* CREATE USERS */
        /* password = 1234 */
        UserEntity userBrahian = new UserEntity();
        userBrahian.setUsername("brahian");
        userBrahian.setPassword("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6");
        userBrahian.setEnabled(true);
        userBrahian.setAccountNoExpired(true);
        userBrahian.setAccountNoLocked(true);
        userBrahian.setCredentialNoExpired(true);
        userBrahian.setRoles(Set.of(roleAdmin));

        userRepository.saveAll(List.of(userBrahian));
    }
}
