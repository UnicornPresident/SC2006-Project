package com.example.application.views.main;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DateTimePicker extends VerticalLayout {
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    
    public DateTimePicker() {
        // Initialize the date and time pickers
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        startTimePicker = new TimePicker();
        endTimePicker = new TimePicker();
        
        // Set labels for the pickers
        startDatePicker.setLabel("Start Date");
        endDatePicker.setLabel("End Date");
        startTimePicker.setLabel("Start Time");
        endTimePicker.setLabel("End Time");
        
        // Add the pickers to the layout
        add(startDatePicker, endDatePicker, startTimePicker, endTimePicker);
    }
    
    // Getter methods for the start and end dates and times
    public DatePicker getStartDatePicker() {
        return startDatePicker;
    }
    
    public DatePicker getEndDatePicker() {
        return endDatePicker;
    }
    
    public TimePicker getStartTimePicker() {
        return startTimePicker;
    }
    
    public TimePicker getEndTimePicker() {
        return endTimePicker;
    }
}
