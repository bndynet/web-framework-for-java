package net.bndy.wf.lib;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.bndy.wf.Application;

@MappedSuperclass()
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public abstract class _BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
	protected Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = Application.DATETIE_FORMAT, timezone = Application.TIMEZONE)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = Application.DATETIE_FORMAT, timezone = Application.TIMEZONE)
	private Date lastUpdate;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.getId() != null ? this.getId().hashCode() : 0);
		return hash;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		_BaseEntity other = (_BaseEntity) obj;
		if (this.getId() != other.getId() && (this.getId() == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " [ID=" + id + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrePersist
	public void setCreateDate() {
		this.lastUpdate = this.createDate = new Date();
	}

	@PreUpdate
	public void setLastUpdate() {
		this.lastUpdate = new Date();
	}
}
