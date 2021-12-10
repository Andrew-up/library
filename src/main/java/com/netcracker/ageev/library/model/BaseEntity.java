package com.netcracker.ageev.library.model;


import com.netcracker.ageev.library.model.enums.Status;
import com.netcracker.ageev.library.model.users.Users;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements AuditorAware<String> {

    @CreatedDate
    @Column(name = "created", nullable = false, updatable = false)
    private Date created;

    @LastModifiedDate
    @Column(name = "updated")
    private Date updated;

    @CreatedBy
    @Column(name = "createdBy")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "lastModifiedBy")
    private String lastModifiedBy;

    @Override
    public Optional<String> getCurrentAuditor() {
        String uname = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(uname);
    }

}


