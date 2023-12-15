package com.dev.yggdrasil.view.comments;

import com.dev.yggdrasil.domain.User;
import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.model.dto.CommentDTO;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.service.CommentService;
import com.dev.yggdrasil.service.impl.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;

public class CommentComponent extends VerticalLayout {
    private final TextField commentField;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private List<Div> commentsDiv = new ArrayList<>();
    private long articleId;


    public CommentComponent(ArticleService articleService, CommentService commentService, UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;

        commentField = new TextField("Оставить комментарий");
        Button sendButton = new Button("Отправить", this::sendHandler);
        add(commentField, sendButton);
    }


    private void sendHandler(ClickEvent<Button> buttonClickEvent) {
        User currentUser = userService.getCurrentUser();
        addComment(commentField.getValue(), currentUser.getUsername(), currentUser.getId());
    }


    public void renderComments(long articleId) {
        this.articleId = articleId;
        commentsDiv.forEach(this::remove);
        commentsDiv.clear();
        commentsDiv = new ArrayList<>();
        ArticleDTO articleDTO = articleService.get(articleId);
        articleDTO.getComments().forEach(item -> {
            Div commentDiv = new Div();
            commentDiv.add(new Span(item.getUsername() + ": " + item.getText()));
            commentsDiv.add(commentDiv);
        });
        commentsDiv.forEach(this::add);
    }

    private void addComment(String text, String username, Long userId) {
        commentService.create(CommentDTO.builder()
                .text(text)
                .user(userId)
                .username(username)
                .article(ArticleDTO.builder()
                        .id(articleId)
                        .build())
                .build());

        renderComments(articleId);
    }
}

