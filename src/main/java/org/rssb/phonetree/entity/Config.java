
package org.rssb.phonetree.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Config")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "Prop_Key")
    private String propKey;
    @Column(name = "Prop_Value")
    private String propValue;

    public Config() {
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPropKey() {
        return propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("id=").append(id);
        sb.append(", propKey='").append(propKey).append('\'');
        sb.append(", propValue='").append(propValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
