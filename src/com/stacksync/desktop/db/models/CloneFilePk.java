package com.stacksync.desktop.db.models;

import java.io.Serializable;
import javax.persistence.*;


public class CloneFilePk implements Serializable {
    
    private static final long serialVersionUID = 12314234L;

    @Id
    @Column(name = "file_id", nullable = false)
    private Long fileId;
    @Id
    @Column(name = "file_version", nullable = false)
    private long version;
    
    
    public CloneFilePk() { }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileId != null ? fileId.hashCode() : 0);
        hash += version;
        return hash;
    }   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CloneFilePk other = (CloneFilePk) obj;
        if (this.fileId != other.fileId && (this.fileId == null || !this.fileId.equals(other.fileId))) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CloneFile[id=" + fileId + ", version=" + version + "]";
    }
}