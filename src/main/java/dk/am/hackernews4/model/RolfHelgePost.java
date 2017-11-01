/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.am.hackernews4.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Doctor
 */
@XmlRootElement
public class RolfHelgePost {

    private String username;
    private String post_type;
    private String pwd_hash;
    private String post_title;
    private String post_url;
    private Integer post_parent;
    private String post_text;

    public RolfHelgePost(String username, String post_type, String pwd_hash, String post_title, String post_url, Post post_parent, String post_text) {
        this.username = username;
        this.post_type = post_type;
        this.pwd_hash = pwd_hash;
        this.post_title = post_title;
        this.post_url = post_url;
        if(null!=post_parent){
        this.post_parent = post_parent.getPostId().intValue();
        }
        else
            this.post_parent = null;
        this.post_text = post_text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getPwd_hash() {
        return pwd_hash;
    }

    public void setPwd_hash(String pwd_hash) {
        this.pwd_hash = pwd_hash;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public Integer getPost_parent() {
        return post_parent;
    }

    public void setPost_parent(Integer post_parent) {
        this.post_parent = post_parent;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }
    
    

}
