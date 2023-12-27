package com.dev.yggdrasil.view.note;


import com.dev.yggdrasil.feign.NoteFeign;
import com.dev.yggdrasil.model.dto.NoteDTO;
import com.dev.yggdrasil.view.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.pekka.WysiwygE;

import java.time.Duration;
import java.time.LocalTime;

@PageTitle("Note")
@Route(value = "note", layout = MainLayout.class)
@PermitAll
public class NoteView extends Main implements BeforeEnterObserver {
    private final WysiwygE editor;
    private final NoteFeign noteFeign;
    private final TextField key;
    private final TimePicker sch;
    private final Paragraph paragraph;

    public NoteView(@Autowired NoteFeign noteFeign) {
        this.noteFeign = noteFeign;

        key = new TextField();
        sch = new TimePicker();
        sch.setStep(Duration.ofSeconds(1));
        editor = new WysiwygE("300px", "100vw");
        editor.addValueChangeListener(this::onValueChange);
        Button button = new Button("Сохранить", this::send);
        Button buttonCode = new Button("Зашифровать", this::code);
        Button buttonDecode = new Button("Расшифровать", this::decode);
        Button buttonSch = new Button("Запланировать шифрование", this::sch);
        Button decodeRm = new Button("Удалить шифровку", this::devodeRm);

        paragraph = new Paragraph("");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(editor);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        horizontalLayout1.add(buttonCode);
        horizontalLayout1.add(buttonDecode);
        horizontalLayout1.add(buttonSch);
        horizontalLayout1.add(sch);

        verticalLayout.add(button);
        verticalLayout.add(horizontalLayout1);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new Paragraph("ключ:"));
        horizontalLayout.add(key);
        verticalLayout.add(horizontalLayout);
        verticalLayout.add(decodeRm);
        verticalLayout.add(new Paragraph("Зашифрованный текст: "));
        verticalLayout.add(paragraph);
        add(verticalLayout);
    }

    private void devodeRm(ClickEvent<Button> buttonClickEvent) {
        noteFeign.decodeRemove();
        paragraph.setText("");
    }

    private void sch(ClickEvent<Button> buttonClickEvent) {
        noteFeign.schedule(key.getValue(), sch.getValue());
    }

    private void code(ClickEvent<Button> buttonClickEvent) {
        String html = editor.getValue();
        String result = html.replaceAll("<[^>]*>", "");
        paragraph.setText(noteFeign.code(
                        key.getValue(),
                        NoteDTO.builder().text(result).build()
                ).getBody().getEncryptedText());
    }

    private void decode(ClickEvent<Button> buttonClickEvent) {
//        String html = paragraph.getText();
//        String result = html.replaceAll("<[^>]*>", "");
        try {
            editor.setValue(noteFeign.decode(
                    key.getValue(),
                    NoteDTO.builder().text(paragraph.getText()).build()
            ).getBody().getText());
        } catch (Exception i) {
            editor.setValue("Неправильный ключ");
        }

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
            if(paragraph != null) {
                paragraph.setText(noteForUser.getEncryptedText());
            }
        }catch (Exception e) {

        }
    }
}
