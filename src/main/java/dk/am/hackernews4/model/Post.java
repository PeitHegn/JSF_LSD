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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "post", catalog = "postgres", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p"),
    @NamedQuery(name = "Post.findByPostId", query = "SELECT p FROM Post p WHERE p.postId = :postId"),
    @NamedQuery(name = "Post.findByPostTitle", query = "SELECT p FROM Post p WHERE p.postTitle = :postTitle"),
    @NamedQuery(name = "Post.findByPostUrl", query = "SELECT p FROM Post p WHERE p.postUrl = :postUrl"),
    @NamedQuery(name = "Post.findByScore", query = "SELECT p FROM Post p WHERE p.score = :score"),
    @NamedQuery(name = "Post.findByPostText", query = "SELECT p FROM Post p WHERE p.postText = :postText"),
    @NamedQuery(name = "Post.findByPostType", query = "SELECT p FROM Post p WHERE p.postType = :postType"),
    @NamedQuery(name = "Post.findHighestHanessId", query = "SELECT p.hanesstId FROM Post p ORDER BY p.hanesstId desc"),
    @NamedQuery(name = "Post.findRolfHelgePost", query = "SELECT NEW dk.am.hackernews4.model.RolfHelgePost(c.contributorName, p.postType, c.contributorPassword, p.postTitle, p.postUrl, p.parentId, p.postText) from Post p join p.contributorId c "),
    @NamedQuery(name = "Post.findByCreatedDate", query = "SELECT p FROM Post p WHERE p.createdDate = :createdDate")})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "PostSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PostSeq", sequenceName = "POST_SEQ", allocationSize = 1)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "post_id")
    private BigDecimal postId;

    @Size(max = 256)
    @Column(name = "post_title")
    private String postTitle;

    @Size(max = 256)
    @Column(name = "post_url")
    private String postUrl;

    @Column(name = "score")
    private BigInteger score;
    
    @Column(name = "hanesst_id")
    private BigInteger hanesstId;

    @Size(max = 1024)
    @Column(name = "post_text")
    private String postText;

    @Size(max = 8)
    @Column(name = "post_type")
    private String postType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @JoinColumn(name = "contributor_id", referencedColumnName = "contributor_id")
    @ManyToOne(optional = false)
    private Contributor contributorId;

    @OneToMany(mappedBy = "parentId")
    private List<Post> postList;

    @JoinColumn(name = "parent_id", referencedColumnName = "post_id")
    @ManyToOne
    private Post parentId;

    public Post() {
    }

    public Post(BigDecimal postId) {
        this.postId = postId;
    }

    public Post(BigDecimal postId, Date createdDate) {
        this.postId = postId;
        this.createdDate = createdDate;
    }

    public BigDecimal getPostId() {
        return postId;
    }

    public void setPostId(BigDecimal postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public BigInteger getScore() {
        return score;
    }

    public void setScore(BigInteger score) {
        this.score = score;
    }

    public BigInteger getHanesstId() {
        return hanesstId;
    }

    public void setHanesstId(BigInteger hanesstId) {
        this.hanesstId = hanesstId;
    }
    
    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Contributor getContributorId() {
        return contributorId;
    }

    public void setContributorId(Contributor contributorId) {
        this.contributorId = contributorId;
    }

    @XmlTransient
    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public Post getParentId() {
        return parentId;
    }

    public void setParentId(Post parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postId != null ? postId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.postId == null && other.postId != null) || (this.postId != null && !this.postId.equals(other.postId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dk.am.hackernews4.model.Post[ postId=" + postId + " ]";
    }

}
