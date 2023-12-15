package com.dev.yggdrasil.view.articles;

import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.view.MainLayout;
import com.dev.yggdrasil.view.comments.CommentComponent;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@PageTitle("Articles")
@Route(value = "article-view", layout = MainLayout.class)
@AnonymousAllowed
@Component
public class ArticleView extends VerticalLayout {
    H2 pageTitle;
    Paragraph articleParagraph;
    Paragraph readingTimeParagraph;
    VerticalLayout contentLayout;
    Div centeringDiv;
    private final CommentComponent commentComponent;
    private final ArticleService articleService;

    @Autowired
    public ArticleView(CommentComponent commentComponent, ArticleService articleService) {
        this.commentComponent = commentComponent;
        this.articleService = articleService;
    }

    @PostConstruct
    private void init() {
        pageTitle = new H2();;
        articleParagraph = new Paragraph();
        readingTimeParagraph = new Paragraph();
        readingTimeParagraph.getStyle().set("color", "gray"); // Установка цвета текста серым
        contentLayout = new VerticalLayout(pageTitle,readingTimeParagraph, articleParagraph, commentComponent);
        contentLayout.setSizeFull();
        contentLayout.setAlignItems(Alignment.CENTER);

        centeringDiv = new Div(contentLayout);
        centeringDiv.setSizeFull();
        setSizeFull();
        add(centeringDiv);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        VaadinSession currentSession = VaadinSession.getCurrent();
        long selectedItemId = 0L;
        ArticleDTO articleDTO = new ArticleDTO();
        if( currentSession != null) {

            selectedItemId = (long) VaadinSession.getCurrent().getAttribute("selectedItem");
            articleDTO = this.articleService.get(selectedItemId);
            pageTitle.setText(articleDTO.getTitle());
            articleParagraph.getElement().setProperty("innerHTML", articleDTO.getText());

            readingTimeParagraph.setText("Время для прочтения: " + articleDTO.getTimeToUnderstand() + "минут");

            commentComponent.setArticleId(selectedItemId);
        }
//        VaadinSession.getCurrent().setAttribute("selectedItem", null);
    }
}