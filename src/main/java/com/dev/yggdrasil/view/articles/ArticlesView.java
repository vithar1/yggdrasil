package com.dev.yggdrasil.view.articles;

import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.view.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@PageTitle("Articles")
@Route(value = "articles", layout = MainLayout.class)
@AnonymousAllowed
@Component
public class ArticlesView extends Div implements AfterNavigationObserver {

    private final ArticleService articleService;
    Grid<ArticleDTO> grid = new Grid<>();

    @Autowired
    public ArticlesView(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostConstruct
    private void init() {
        addClassName("articles-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(this::createCard);
        grid.addCellFocusListener(item -> {
            getElement().getStyle().set("cursor", "pointer");
        });
        grid.addItemClickListener(item -> {
            VaadinSession.getCurrent().setAttribute("selectedItem", item.getItem().getId());
            UI.getCurrent().navigate("article-view");
        });
        add(grid);
    }

    private HorizontalLayout createCard(ArticleDTO articleDTO) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        Image image = new Image();
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(articleDTO.getTitle());
        name.addClassName("name");
        Span date = new Span(String.valueOf(articleDTO.getCreatedDate()));
        date.addClassName("date");
        header.add(name, date);

        Span post = new Span();
        post.getElement().setProperty("innerHTML", articleDTO.getText().substring(0, Math.min(400, articleDTO.getText().length())) + "...");
        post.addClassName("post");

        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);
        actions.getThemeList().add("spacing-s");

        Icon likeIcon = VaadinIcon.HEART.create();
        likeIcon.addClassName("icon");
        Span likes = new Span("20");
        likes.addClassName("likes");
        Icon commentIcon = VaadinIcon.COMMENT.create();
        commentIcon.addClassName("icon");
        Span comments = new Span("20");
        comments.addClassName("comments");
        Icon shareIcon = VaadinIcon.CONNECT.create();
        shareIcon.addClassName("icon");
        Span shares = new Span("20");
        shares.addClassName("shares");

        actions.add(likeIcon, likes, commentIcon, comments, shareIcon, shares);

        description.add(header, post, actions);
        card.add(description);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        List<ArticleDTO> articleDTOS = articleService.findAll();
        grid.setItems(articleDTOS);
    }

}
