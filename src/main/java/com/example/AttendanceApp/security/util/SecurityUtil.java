package com.example.AttendanceApp.security.util;

import com.example.AttendanceApp.models.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
    private Authentication authentication;

    public SecurityUtil() {
    }

    public Employee getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Employee) {
            return (Employee) authentication.getPrincipal();
        }

        return null; // No user logged in
    }
}
