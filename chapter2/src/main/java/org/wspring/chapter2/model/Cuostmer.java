package org.wspring.chapter2.model;

/**
 * DESCRIPTION : 客户
 *
 * @author ducf
 * @create 2019-03-05 上午 11:19
 */
public class Cuostmer {

    /*id*/
    private long id;
    /*客户名称*/
    private String name;
    /*联系人*/
    private String contact;
    /*电话号码*/
    private String telePhone;
    /*邮箱*/
    private String email;
    /*备注*/
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cuostmer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", contact='").append(contact).append('\'');
        sb.append(", telePhone='").append(telePhone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
