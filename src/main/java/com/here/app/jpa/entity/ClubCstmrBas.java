package com.here.app.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "club_cstmr_bas")
@NamedQuery(name = "ClubCstmrBas.findAll", query = "SELECT c FROM ClubCstmrBas c")
@Data
public class ClubCstmrBas implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CSTMR_SNO")
    private Integer cstmrSno;

    @Column(name="AUTH_ID")
    private String authId;

    @Column(name="CSTMR_STATUS_CD")
    private String cstmrStatusCd;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CSTMR_STATUS_CNG_DT")
    private Date cstmrStatusCngDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="JOIN_DT")
    private Date joinDt;

    @Column(name="USER_ID")
    private String userId;

    @Column(name="USER_PSWD")
    private String userPswd;
}
