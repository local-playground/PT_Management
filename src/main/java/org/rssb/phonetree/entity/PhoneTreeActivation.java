package org.rssb.phonetree.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "PhoneTreeActivation")
@NamedQueries({
        @NamedQuery(name = "PhoneTreeActivation.getPhoneTreeActivationSummary",
                query="SELECT NEW org.rssb.phonetree.domain.PhoneTreeActivationSummary(" +
                        "pta.phoneTreeActivationDate,pta.totalSangat," +
                        "sum(ptad.totalFamiliesCalled),sum(ptad.totalVMLeft)," +
                        "sum(ptad.totalNotReachableFamilies), min(ptad.totalTimeTaken)," +
                        "max(ptad.totalTimeTaken), avg(ptad.totalTimeTaken)) FROM PhoneTreeActivation pta " +
                        " JOIN pta.phoneTreeActivationDetailList ptad " +
                        " WHERE pta.phoneTreeActivationDate = :activationDate")

})
public class PhoneTreeActivation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhoneTreeActivationId")
    private int phoneTreeActivationId;

    @Column(name = "PhoneTreeActivationDate")
    private String phoneTreeActivationDate;

    @Column(name = "PhoneTreeActivationTime")
    private String phoneTreeActivationTime;

    @OneToMany(mappedBy = "phoneTreeActivation", fetch = FetchType.LAZY)
    private List<PhoneTreeActivationDetail> phoneTreeActivationDetailList;

    @Column(name = "TotalSangat")
    private int totalSangat;

    public int getTotalSangat() {
        return totalSangat;
    }

    public void setTotalSangat(int totalSangat) {
        this.totalSangat = totalSangat;
    }

    public int getPhoneTreeActivationId() {
        return phoneTreeActivationId;
    }

    public void setPhoneTreeActivationId(int phoneTreeActivationId) {
        this.phoneTreeActivationId = phoneTreeActivationId;
    }

    public String getPhoneTreeActivationDate() {
        return phoneTreeActivationDate;
    }

    public void setPhoneTreeActivationDate(String phoneTreeActivationDate) {
        this.phoneTreeActivationDate = phoneTreeActivationDate;
    }

    public String getPhoneTreeActivationTime() {
        return phoneTreeActivationTime;
    }

    public void setPhoneTreeActivationTime(String phoneTreeActivationTime) {
        this.phoneTreeActivationTime = phoneTreeActivationTime;
    }

    public List<PhoneTreeActivationDetail> getPhoneTreeActivationDetailList() {
        return phoneTreeActivationDetailList;
    }

    public void setPhoneTreeActivationDetailList(List<PhoneTreeActivationDetail> phoneTreeActivationDetailList) {
        this.phoneTreeActivationDetailList = phoneTreeActivationDetailList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhoneTreeActivation{");
        sb.append("phoneTreeActivationId=").append(phoneTreeActivationId).append('\'');
        sb.append("totalSangat=").append(totalSangat).append('\'');
        sb.append(", phoneTreeActivationDate='").append(phoneTreeActivationDate).append('\'');
        sb.append(", phoneTreeActivationTime='").append(phoneTreeActivationTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
