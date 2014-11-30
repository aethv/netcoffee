package com.clinet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class PeriodObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "INSERT_TIMESTAMP", nullable = false)
    private Date insertTimeStamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFY_TIMESTAMP", nullable = false)
    private Date modifyTimeStamp;

    @PrePersist
    protected void onCreate() {

        insertTimeStamp = modifyTimeStamp = new Date();
    }

    @PreUpdate
    protected void onUpdate() {

        modifyTimeStamp = new Date();
    }

    public Date getModifyTimeStamp() {

        return modifyTimeStamp;
    }

    public void setModifyTimeStamp(Date modifyTimeStamp) {

        this.modifyTimeStamp = modifyTimeStamp;
    }

    public Date getInsertTimeStamp() {

        return insertTimeStamp;
    }

    public void setInsertTimeStamp(Date insertTimeStamp) {

        this.insertTimeStamp = insertTimeStamp;
    }

}
