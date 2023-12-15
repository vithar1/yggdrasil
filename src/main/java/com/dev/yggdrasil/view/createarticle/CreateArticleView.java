package com.dev.yggdrasil.view.createarticle;


import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.view.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.pekka.WysiwygE;

import java.time.LocalDate;

@PageTitle("Create Article")
@Route(value = "create-article", layout = MainLayout.class)
@PermitAll
@Component
public class CreateArticleView extends Main {
    private final WysiwygE editor;
    private final TextField title;
    private final IntegerField timeToUnderstand;

    private final ArticleService articleService;

    private final VerticalLayout verticalLayout;

    @Autowired
    public CreateArticleView(ArticleService articleService) {
        this.articleService = articleService;
        editor = new WysiwygE("300px", "100vw");
        editor.addValueChangeListener(this::onValueChange);
        Button button = new Button("Отправить", this::send);
        title = new TextField("Title");
        timeToUnderstand = new IntegerField("Time to understand");

        verticalLayout = new VerticalLayout();
        verticalLayout.add(title);
        verticalLayout.add(editor);
        verticalLayout.add(timeToUnderstand);
        verticalLayout.add(button);
        add(verticalLayout);
    }

    private void send(ClickEvent<Button> buttonClickEvent) {
        articleService.create(
               ArticleDTO.builder()
                       .createdDate(LocalDate.now())
                       .text(editor.getValue())
                       .title(title.getValue())
                       .timeToUnderstand(timeToUnderstand.getValue())
                       .lastEditTime(LocalDate.now())
                       .build()
        );
        timeToUnderstand.clear();
        title.clear();
        editor.clear();
    }

    private void onValueChange(AbstractField.ComponentValueChangeEvent<WysiwygE, String> wysiwygEStringComponentValueChangeEvent) {

    }
}
