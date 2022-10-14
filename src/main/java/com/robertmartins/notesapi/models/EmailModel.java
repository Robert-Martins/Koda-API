package com.robertmartins.notesapi.models;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "TB_EMAILS")
public class EmailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "emailFrom")
    private String emailFrom;

    @Column(name = "emailTo")
    private String emailTo;

    @Column(name = "subject")
    private String subject;

    @Column(name = "emailBody", length = 5000)
    private String text;

    @Column(name = "emailSentDate")
    private LocalDateTime emailSentDate;

    @Column(name = "wasEmailSent")
    private boolean wasEmailSent;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

}
