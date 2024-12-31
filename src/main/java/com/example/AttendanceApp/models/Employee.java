package com.example.AttendanceApp.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity implements UserDetails {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Double paymentPerHour;

    @ManyToOne
    @JoinColumn(name = "Separate_id", referencedColumnName = "id")
    Separate separate;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    Position position;

    @ManyToOne
    @JoinColumn(name = "assignment_id", referencedColumnName = "id")
    Assignment assignment;

    @OneToMany(mappedBy = "employee")
    private List<Schedule> schedules = new ArrayList<>();

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name= "benefid_card_id")
    private BenefitCard benefitCard;


    public Employee(String firstName, String lastName, String username, String password, Double paymentPerHour) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.paymentPerHour = paymentPerHour;
    }

    public Employee() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+ ""));
    }


    public String getPassword() {
        return password;
    }

    public Double getPaymentPerHour() {
        return paymentPerHour;
    }

    public Separate getSeparate() {
        return separate;
    }

    public Position getPosition() {
        return position;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPaymentPerHour(Double paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public void setSeparate(Separate separate) {
        this.separate = separate;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public BenefitCard getBenefitCard() {
        return benefitCard;
    }

    public void setBenefitCard(BenefitCard benefitCard) {
        this.benefitCard = benefitCard;
    }

    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

}
