package com.conti.webbible.views.home;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.conti.webbible.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "bible", layout = MainView.class)
@PageTitle("Home")
@CssImport("./styles/views/home/home-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class HomeView extends HorizontalLayout {

    private TextField bookName;
    private TextField chapter;
    private TextField verses;
    private Button search;
    private TextField fullSearch;
    private Button advancedSearch;
    private TextArea resultView;

    public HomeView() {
        setId("home-view");
      
        H1 title = new H1("WebBible - Search for passages in the Bible");
        
        HorizontalLayout rowOne = new HorizontalLayout();
        rowOne.setWidth("100%");
        
        bookName = new TextField("Book");
        bookName.setHelperText("e.g. John");
        bookName.setPattern("[A-Z]{1}[a-z]*");
        bookName.setPreventInvalidInput(true);
        
        chapter = new TextField("Chapter");
        chapter.setHelperText("e.g. 3");
        chapter.setPattern("[0-9]+");
        chapter.setPreventInvalidInput(true);
        
        verses = new TextField("Verse(s)");
        verses.setHelperText("e.g. 16 | e.g. 10-20");
        verses.setPattern("[0-9]+-?[0-9]*");
        verses.setPreventInvalidInput(true);
        
        search = new Button("Search");
        rowOne.add(bookName, chapter, verses, search);
        rowOne.setPadding(false);
        rowOne.setSpacing(true);
        rowOne.setMargin(false);
        rowOne.setVerticalComponentAlignment(Alignment.CENTER, bookName, chapter, verses, search);
        rowOne.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        
        HorizontalLayout rowTwo = new HorizontalLayout();
        rowTwo.setWidth("100%");
        fullSearch = new TextField("Advanced Search");
        fullSearch.setHelperText("Input the full set of parameters: Book chapter:verse(s)");
        fullSearch.setPattern("[A-Z]{1}[a-z]*\\s{1}[0-9]+\\:?[0-9]+-?[0-9]*");
        fullSearch.setPreventInvalidInput(true);
        
        advancedSearch = new Button("Advanced Search");
        rowTwo.add(fullSearch, advancedSearch);
        rowTwo.setPadding(false);
        rowTwo.setSpacing(true);
        rowTwo.setMargin(false);
        rowTwo.setVerticalComponentAlignment(Alignment.CENTER, fullSearch, advancedSearch);
        rowTwo.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        
        VerticalLayout rowHolder = new VerticalLayout();
        rowHolder.add(rowOne, rowTwo);
        rowHolder.setPadding(true);
        rowHolder.setSpacing(false);
        rowHolder.setMargin(false);
        rowHolder.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        
        resultView = new TextArea();
        resultView.setWidthFull();
        resultView.setHeight("325px");
        resultView.setReadOnly(true);
        resultView.setValue("API Request Text Goes Here");
        
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();
        layout.setHeightFull();
        layout.add(title, rowHolder, resultView);
        layout.setHorizontalComponentAlignment(Alignment.CENTER, title);
        
        add(layout);
        setVerticalComponentAlignment(Alignment.START, layout);
        search.addClickListener(e -> {
        	resultView.clear();
            resultView.setValue(bookName.getValue() + " " + chapter.getValue() + ":" + verses.getValue());
            clearSearchTextFields();
        });
        
        advancedSearch.addClickListener(e -> {
        	resultView.clear();
        	resultView.setValue(fullSearch.getValue());
        	clearAdvancedSearchTextFields();
        });
    }
    
    private void clearSearchTextFields() {
    	bookName.clear();
    	chapter.clear();
    	verses.clear();
    }
    
    private void clearAdvancedSearchTextFields() {
    	fullSearch.clear();
    }

}