package me.lkh.hometownleague.user.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="user_info")
public class User {

    @Id
    private String id;

    private String name;

    private String password;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "create_timestamp")
    @CreationTimestamp
    private Timestamp createTimestamp;

    @Column(name = "modified_timestamp")
    @UpdateTimestamp
    private Timestamp modifiedTimestamp;

    public User(String id, String name, String password, String useYn) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.useYn = useYn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Timestamp getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

}
