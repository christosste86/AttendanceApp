package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Role;

import java.util.List;

public class RoleService {
    private List<Role> roles = List.of(
            new Role("ROLE_ADMIN"),
            new Role("ROLE_LEVEL1"),
            new Role("ROLE_LEVEL2"),
            new Role("ROLE_LEVEL3"));

    public RoleService() {
    }

    public void addRole(Employee employee , Role role) {
        int index = 0;
        for (int i = 0; i < this.roles.size(); i++) {
            if(this.roles.get(i) == role) {
                index = i ;
            }
            employee.addRole(this.roles.get(index));
            index = index + 1;
        }
    }
}

