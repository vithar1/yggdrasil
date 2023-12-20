package com.dev.yggdrasil.view.articles;

import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.service.CommentService;
import com.dev.yggdrasil.service.impl.UserService;
import com.dev.yggdrasil.view.MainLayout;
import com.dev.yggdrasil.view.comments.CommentComponent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@PageTitle("Articles")
@Route(value = "article/:articleId?", layout = MainLayout.class)
@AnonymousAllowed
public class ArticleView extends VerticalLayout implements BeforeEnterObserver {
    H2 pageTitle;
    Paragraph articleParagraph;
    Paragraph readingTimeParagraph;
    VerticalLayout contentLayout;
    Div centeringDiv;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private CommentComponent commentComponent;


    public ArticleView(@Autowired ArticleService articleService, @Autowired CommentService commentService, @Autowired UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;

        pageTitle = new H2();;
        articleParagraph = new Paragraph();
        readingTimeParagraph = new Paragraph();
        readingTimeParagraph.getStyle().set("color", "gray");
        commentComponent = new CommentComponent(articleService, commentService,userService);
        contentLayout = new VerticalLayout(pageTitle,readingTimeParagraph, articleParagraph, commentComponent);
        contentLayout.setSizeFull();
        contentLayout.setAlignItems(Alignment.CENTER);

        centeringDiv = new Div(contentLayout);
        centeringDiv.setSizeFull();
        setSizeFull();
        add(centeringDiv);
    }

//    @PostConstruct
//    private void init() {
//
//    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        long articleID = beforeEnterEvent.getRouteParameters().getInteger("articleId").get();

        ArticleDTO articleDTO = articleService.get(articleID);
        pageTitle.setText(articleDTO.getTitle());
        articleParagraph.getElement().setProperty("innerHTML", articleDTO.getText());
        readingTimeParagraph.setText("Время для прочтения: " + articleDTO.getTimeToUnderstand() + "минут");

        commentComponent.renderComments(articleID);
    }
}