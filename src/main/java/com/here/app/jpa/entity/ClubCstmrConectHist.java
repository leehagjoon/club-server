package com.here.app.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the PTY_CSTMR_CONECT_HIST database table.
 * 
 */
@Entity
@Table(name="club_cstmr_conect_hist")
@NamedQuery(name = "ClubCstmrConectHist.findAll",query = "SELECT c FROM ClubCstmrConectHist c")
public class ClubCstmrConectHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CSTMR_CONECT_HIST_SNO")
	private int cstmrConectHistSno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CONECT_DT")
	private Date conectDt;

	@Column(name="CONECT_ERROR_CD")
	private String conectErrorCd;

	@Column(name = "CONECT_IP")
	private String conectIp;


	@Column(name="CONECT_SUCES_YN")
	private String conectSucesYn;

	@Column(name="CSTMR_SNO")
	private int cstmrSno;

	public ClubCstmrConectHist() {
	}

	public int getCstmrConectHistSno() {
		return this.cstmrConectHistSno;
	}

	public void setCstmrConectHistSno(int cstmrConectHistSno) {
		this.cstmrConectHistSno = cstmrConectHistSno;
	}

	public Date getConectDt() {
		return this.conectDt;
	}

	public void setConectDt(Date conectDt) {
		this.conectDt = conectDt;
	}

	public String getConectErrorCd() {
		return this.conectErrorCd;
	}

	public String getConectIp() {
		return this.conectIp;
	}

	public void setConectIp(String conectIp) {
		this.conectIp = conectIp;
	}

	public void setConectErrorCd(String conectErrorCd) {
		this.conectErrorCd = conectErrorCd;
	}

	public String getConectSucesYn() {
		return this.conectSucesYn;
	}

	public void setConectSucesYn(String conectSucesYn) {
		this.conectSucesYn = conectSucesYn;
	}

	public int getCstmrSno() {
		return this.cstmrSno;
	}

	public void setCstmrSno(int cstmrSno) {
		this.cstmrSno = cstmrSno;
	}

}