package com.dev.yggdrasil.view.note;


import com.dev.yggdrasil.feign.NoteFeign;
import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.model.dto.NoteDTO;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.view.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.vaadin.pekka.WysiwygE;

import java.time.LocalDate;

@PageTitle("Note")
@Route(value = "note", layout = MainLayout.class)
@PermitAll
@Component
public class NoteView extends Main implements BeforeEnterObserver {
    private final WysiwygE editor;
    private final NoteFeign noteFeign;

    @Autowired
    public NoteView(NoteFeign noteFeign) {
        this.noteFeign = noteFeign;


        editor = new WysiwygE("300px", "100vw");
        editor.addValueChangeListener(this::onValueChange);
        Button button = new Button("Сохранить", this::send);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(editor);
        verticalLayout.add(button);
        add(verticalLayout);
    }

    private void send(ClickEvent<Button> buttonClickEvent) {
        noteFeign.createNote(
                NoteDTO.builder()
                        .text(editor.getValue())
                        .build()
        );
    }

    private void onValueChange(AbstractField.ComponentValueChangeEvent<WysiwygE, String> wysiwygEStringComponentValueChangeEvent) {

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        try {
            NoteDTO noteForUser = noteFeign.getNoteForUser().getBody();
            if (noteForUser != null) {
                editor.setValue(noteForUser.getText());
            }
        }catch (Exception e) {

        }
    }
}
