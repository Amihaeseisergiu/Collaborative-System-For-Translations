package uaic.info.csft.userservice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "AppUser")
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String userName;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "app_user_language",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<Language> languages = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    public void addLanguage(Language language)
    {
        this.languages.add(language);
        language.getAppUsers().add(this);
    }

    public void removeLanguage(Language language)
    {
        this.languages.remove(language);
        language.getAppUsers().remove(this);
    }

    public void addPost(Post post)
    {
        this.posts.add(post);
        post.setAppUser(this);
    }

    public void removePost(Post post)
    {
        this.posts.remove(post);
        post.setAppUser(null);
    }

    public void addComment(Comment comment)
    {
        this.comments.add(comment);
        comment.setAppUser(this);
    }

    public void removeComment(Comment comment)
    {
        this.comments.remove(comment);
        comment.setAppUser(null);
    }

    @Getter
    public static class AppUserBuilder {

        private final AppUser appUser;

        public AppUserBuilder() {
            appUser = new AppUser();
        }

        public AppUser.AppUserBuilder id(final Long id)
        {
            this.appUser.setId(id);
            return this;
        }

        public AppUser.AppUserBuilder userName(final String userName)
        {
            this.appUser.setUserName(userName);
            return this;
        }

        public AppUser.AppUserBuilder password(final String password)
        {
            this.appUser.setPassword(password);
            return this;
        }

        public AppUser.AppUserBuilder language(final Language language)
        {
            this.appUser.addLanguage(language);
            return this;
        }

        public AppUser build()
        {
            return this.appUser;
        }
    }
}
