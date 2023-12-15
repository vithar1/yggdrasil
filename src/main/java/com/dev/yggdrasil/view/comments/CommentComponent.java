package com.dev.yggdrasil.view.comments;

import com.dev.yggdrasil.domain.User;
import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.model.dto.CommentDTO;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.service.CommentService;
import com.dev.yggdrasil.service.UserService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentComponent extends VerticalLayout {
    private TextField commentField;
    private long articleId;

    private final ArticleService articleService;

    private final CommentService commentService;

    private final UserService userService;
    private List<Div> commentsDiv = new ArrayList<>();

    @Autowired
    public CommentComponent(ArticleService articleService, CommentService commentService, UserService userService) {
        this.articleService = articleService;

        this.commentService = commentService;
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        this.articleId = articleId;

        commentField = new TextField("Оставить комментарий");
        Button sendButton = new Button("Отправить", this::sendHandler);
        add(commentField, sendButton);
    }

    private void sendHandler(ClickEvent<Button> buttonClickEvent) {
        User currentUser = userService.getCurrentUser();
        addComment(commentField.getValue(), currentUser.getUsername(), currentUser.getId());
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        renderComments();
    }

    private void renderComments() {
        commentsDiv.forEach(this::remove);
        commentsDiv.clear();
        VaadinSession currentSession = VaadinSession.getCurrent();
        commentsDiv = new ArrayList<>();
        if(currentSession != null) {
            long articleId = (long) VaadinSession.getCurrent().getAttribute("selectedItem");
            ArticleDTO articleDTO = articleService.get(articleId);
            articleDTO.getComments().forEach(item -> {
                Div commentDiv = new Div();
                commentDiv.add(new Span(item.getUsername() + ": " + item.getText()));
                commentsDiv.add(commentDiv);
            });
        }
        commentsDiv.forEach(this::add);
    }

    private void addComment(String text, String username, Long userId) {
        VaadinSession currentSession = VaadinSession.getCurrent();
        if(currentSession != null) {
            long articleId = (long) VaadinSession.getCurrent().getAttribute("selectedItem");

            commentService.create(CommentDTO.builder()
                    .text(text)
                    .user(userId)
                    .username(username)
                    .article(ArticleDTO.builder()
                            .id(articleId)
                            .build())
                    .build());

            renderComments();
        }

    }



    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}

