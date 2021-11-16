package uaic.info.csft.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnoreProperties({"users", "posts"})
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_languages",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = {
                @JoinColumn(name = "language_name"),
                @JoinColumn(name = "language_proficiency")
            }
    )
    private Set<Language> languages = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    public void addLanguage(Language language)
    {
        this.languages.add(language);
        language.getUsers().add(this);
    }

    public void removeLanguage(Language language)
    {
        this.languages.remove(language);
        language.getUsers().remove(this);
    }

    public void addPost(Post post)
    {
        this.posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post)
    {
        this.posts.remove(post);
        post.setUser(null);
    }

    public void addComment(Comment comment)
    {
        this.comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment)
    {
        this.comments.remove(comment);
        comment.setUser(null);
    }

    @Getter
    public static class UserBuilder {

        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder id(final Long id)
        {
            this.user.setId(id);
            return this;
        }

        public UserBuilder userName(final String userName)
        {
            this.user.setUsername(userName);
            return this;
        }

        public UserBuilder password(final String password)
        {
            this.user.setPassword(password);
            return this;
        }

        public UserBuilder language(final Language language)
        {
            this.user.addLanguage(language);
            return this;
        }

        public UserBuilder languages(final List<Language> languages)
        {
            for(Language language : languages)
            {
                this.user.addLanguage(language);
            }
            return this;
        }

        public User build()
        {
            return this.user;
        }
    }
}
