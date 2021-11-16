package uaic.info.csft.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    private Language language;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void addComment(Comment comment)
    {
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment)
    {
        this.comments.remove(comment);
        comment.setPost(null);
    }

    public void addLanguage(Language language)
    {
        this.language = language;
        language.getPosts().add(this);
    }

    public void addUser(User user)
    {
        user.addPost(this);
    }

    public static class PostBuilder {

        private final Post post;

        public PostBuilder() {
            this.post = new Post();
        }

        public Post.PostBuilder id(final Long id)
        {
            this.post.setId(id);
            return this;
        }

        public Post.PostBuilder title(final String title)
        {
            this.post.setTitle(title);
            return this;
        }

        public Post.PostBuilder message(final String message)
        {
            this.post.setMessage(message);
            return this;
        }

        public Post.PostBuilder comment(final Comment comment)
        {
            this.post.addComment(comment);
            return this;
        }

        public Post.PostBuilder language(final Language language)
        {
            this.post.addLanguage(language);
            return this;
        }

        public Post.PostBuilder user(final User user)
        {
            this.post.addUser(user);
            return this;
        }

        public Post build()
        {
            return this.post;
        }
    }
}
