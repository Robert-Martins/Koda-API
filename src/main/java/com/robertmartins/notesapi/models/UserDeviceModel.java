package com.robertmartins.notesapi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_USER_DEVICE")
@Getter
@Setter
@NoArgsConstructor
public class UserDeviceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ip", length = 25)
    private String ip;

    @Column(name = "os")
    private String os;

    @Column(name = "browser")
    private String browser;

    @Column(name = "appVersion")
    private String appVersion;

    @Column(name = "browserLanguage")
    private String browserLanguage;

    @Column(name = "isDeviceAccessBlocked")
    private boolean isDeviceAccessBlocked;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

}
