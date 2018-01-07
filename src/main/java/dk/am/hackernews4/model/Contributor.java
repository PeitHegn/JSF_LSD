/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.am.hackernews4.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Doctor
 */
@Entity
@Table(name = "contributor", catalog = "postgres", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contributor.findAll", query = "SELECT c FROM Contributor c"),
    @NamedQuery(name = "Contributor.findByContributorId", query = "SELECT c FROM Contributor c WHERE c.contributorId = :contributorId"),
    @NamedQuery(name = "Contributor.findByContributorName", query = "SELECT c FROM Contributor c WHERE c.contributorName = :contributorName"),
    @NamedQuery(name = "Contributor.findByContributorNameAndPassword", query = "SELECT c FROM Contributor c WHERE c.contributorName = :contributorName AND c.contributorPassword = :contributorPassword"),
    @NamedQuery(name = "Contributor.findByContributorPassword", query = "SELECT c FROM Contributor c WHERE c.contributorPassword = :contributorPassword"),
    @NamedQuery(name = "Contributor.findByContributorEmail", query = "SELECT c FROM Contributor c WHERE c.contributorEmail = :contributorEmail"),
    @NamedQuery(name = "Contributor.findByScore", query = "SELECT c FROM Contributor c WHERE c.score = :score"),
    @NamedQuery(name = "Contributor.findByCreatedDate", query = "SELECT c FROM Contributor c WHERE c.createdDate = :createdDate")})
public class Contributor implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "ContributorSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ContributorSeq", sequenceName = "CONTRIBUTOR_SEQ", allocationSize = 1)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "contributor_id")
    private BigDecimal contributorId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "contributor_name")
    private String contributorName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "contributor_password")
    private String contributorPassword;
    
    @Size(max = 256)
    @Column(name = "contributor_email")
    private String contributorEmail;
    
    @Column(name = "score")
    private BigInteger score;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contributorId")
    private List<Post> postList;

    public Contributor() {
    }

    public Contributor(BigDecimal contributorId) {
        this.contributorId = contributorId;
    }

    public Contributor(BigDecimal contributorId, String contributorName, String contributorPassword, Date createdDate) {
        this.contributorId = contributorId;
        this.contributorName = contributorName;
        this.contributorPassword = contributorPassword;
        this.createdDate = createdDate;
    }

    public BigDecimal getContributorId() {
        return contributorId;
    }

    public void setContributorId(BigDecimal contributorId) {
        this.contributorId = contributorId;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public String getContributorPassword() {
        return contributorPassword;
    }

    public void setContributorPassword(String contributorPassword) {
        this.contributorPassword = contributorPassword;
    }

    public String getContributorEmail() {
        return contributorEmail;
    }

    public void setContributorEmail(String contributorEmail) {
        this.contributorEmail = contributorEmail;
    }

    public BigInteger getScore() {
        return score;
    }

    public void setScore(BigInteger score) {
        this.score = score;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @XmlTransient
    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contributorId != null ? contributorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contributor)) {
            return false;
        }
        Contributor other = (Contributor) object;
        if ((this.contributorId == null && other.contributorId != null) || (this.contributorId != null && !this.contributorId.equals(other.contributorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dk.am.hackernews4.model.Contributor[ contributorId=" + contributorId + " ]";
    }

}
