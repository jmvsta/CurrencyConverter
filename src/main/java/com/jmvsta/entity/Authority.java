package com.jmvsta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity(name = "authority")
@Data
@EqualsAndHashCode(of = "authority")
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    private String authority;

    @Column(nullable = false)
    private String title;

    @Override
    public String getAuthority() {
        return authority;
    }
}
