package com.here.app.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="club_cstmr_dtl")
@NamedQuery(name="ClubCstmrDtl.findAll", query="SELECT c FROM ClubCstmrDtl c")
public class ClubCstmrDtl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="CSTMR_SNO")
    private int cstmrSno;

    @Column(name="ADDR")
    private String addr;

    @Column(name="ADDR_DTL")
    private String addrDtl;

    @Temporal(TemporalType.DATE)
    @Column(name="BRTHDY_DATE")
    private Date brthdyDate;

    @Column(name="EMAIL")
    private String email;

    @Column(name="GENDER_CD")
    private String genderCd;

    @Column(name="HPNO")
    private String hpno;

    @Column(name="MEMBER_NAME")
    private String memberName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_DT")
    private Date updateDt;

    @Column(name="UPDATE_USER_ID")
    private String updateUserId;

    @Column(name="ZIP")
    private String zip;

    public ClubCstmrDtl(){
    }

    public int getCstmrSno() {
        return this.cstmrSno;
    }

    public void setCstmrSno(int cstmrSno) {
        this.cstmrSno = cstmrSno;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDtl() {
        return this.addrDtl;
    }

    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
    }

    public Date getBrthdyDate() {
        return this.brthdyDate;
    }

    public void setBrthdyDate(Date brthdyDate) {
        this.brthdyDate = brthdyDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenderCd() {
        return this.genderCd;
    }

    public void setGenderCd(String genderCd) {
        this.genderCd = genderCd;
    }

    public String getHpno() {
        return this.hpno;
    }

    public void setHpno(String hpno) {
        this.hpno = hpno;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getUpdateDt() {
        return this.updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public String getUpdateUserId() {
        return this.updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
