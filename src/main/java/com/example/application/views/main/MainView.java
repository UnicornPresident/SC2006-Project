package com.example.application.views.main;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import Boundary.*;
import Controller.*;
import Entity.*;

import org.hibernate.validator.cfg.defs.MaxDef;
import org.json.JSONArray;
import org.json.JSONObject;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;

@Route("")
public class MainView extends VerticalLayout {
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private LocalDate startDateInput;
    private LocalDate endDateInput;
    private LocalTime startTimeInput;
    private LocalTime endTimeInput;
    private double maxDist;
    private String locationInput;
    private JSONObject jsonObjCarparks;
    private List<ParkingData> dataList;

    public LocalDate getStartDate(){
        return this.startDateInput;
    }
    public LocalDate getEndDate(){
        return this.endDateInput;
    }
    public LocalTime getStartTime(){
        return this.startTimeInput;
    }
    public LocalTime getEndTime(){
        return this.endTimeInput;
    }
    
    public MainView() throws IOException, ParseException {
         // Add a label above the input boxes
        Label titleLabel = new Label("Select the start and end dates and times:");

        // Initialize the date and time pickers
        startDatePicker = new DatePicker("Start date", LocalDate.now());
        endDatePicker = new DatePicker("End date", LocalDate.now().plusDays(1));
        startTimePicker = new TimePicker("Start time", LocalTime.now());
        endTimePicker = new TimePicker("End time", LocalTime.now().plusHours(1));
        NumberField doubleField = new NumberField("Enter max dist (in metres)");

        // Set the step size of the time pickers to 1 minute
        startTimePicker.setStep(Duration.ofMinutes(1));
        endTimePicker.setStep(Duration.ofMinutes(1));

        TextField textField = new TextField("Input the street address or postal code");
        textField.setMaxLength(50);
        textField.setReadOnly(false);
        textField.setWidth("400px");

        Grid<ParkingData> grid = new Grid<>();
        grid.addColumn(ParkingData::getAddress).setHeader("Address");
        grid.addColumn(ParkingData::getAvailableLots).setHeader("AvailableLots");
        grid.addColumn(ParkingData::getPrice).setHeader("Price");
        grid.addColumn(ParkingData::getDistance).setHeader("Distance");
        

        // Create a horizontal layout for the date input components
        HorizontalLayout dateInputsLayout = new HorizontalLayout(startDatePicker, endDatePicker);
        dateInputsLayout.setSpacing(true);

        // Create a horizontal layout for the time input components
        HorizontalLayout timeInputsLayout = new HorizontalLayout(startTimePicker, endTimePicker);
        timeInputsLayout.setSpacing(true);
        AvailabilityApi availabilityApi = new AvailabilityApi();

        Button sortByDistanceButton = new Button("Sort by Distance");
        Button sortByAvailabilityButton = new Button("Sort by Availability");
        Button sortByPriceButton = new Button("Sort by Price");

        sortByDistanceButton.addClickListener(event -> {
            // Sort the grid by distance
            availabilityApi.sortByDistance();
            List<ParkingData> dataList = new ArrayList<>();
            JSONArray jsonArr = availabilityApi.getNearbyCarparks().getJSONArray("Carparks");
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String address = jsonObject.getString("Address");
                int availableLots = jsonObject.getInt("Available Lots");
                double price = jsonObject.getDouble("Price");
                double distance = jsonObject.getDouble("Distance From Location (in metres)");
                ParkingData data = new ParkingData(address, availableLots, price, distance);
                dataList.add(data);
            }
            this.dataList = dataList;
            ListDataProvider<ParkingData> dataProvider = new ListDataProvider<>(this.dataList);
            grid.setDataProvider(dataProvider);
        });
        
        sortByAvailabilityButton.addClickListener(event -> {
            // Sort the grid by availability
            availabilityApi.sortByAvailability();
            List<ParkingData> dataList = new ArrayList<>();
            JSONArray jsonArr = availabilityApi.getNearbyCarparks().getJSONArray("Carparks");
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String address = jsonObject.getString("Address");
                int availableLots = jsonObject.getInt("Available Lots");
                double price = jsonObject.getDouble("Price");
                double distance = jsonObject.getDouble("Distance From Location (in metres)");
                ParkingData data = new ParkingData(address, availableLots, price, distance);
                dataList.add(data);
            }
            this.dataList = dataList;
            ListDataProvider<ParkingData> dataProvider = new ListDataProvider<>(this.dataList);
            grid.setDataProvider(dataProvider);
        });
        
        sortByPriceButton.addClickListener(event -> {
            // Sort the grid by price
            availabilityApi.sortByPrice();
            List<ParkingData> dataList = new ArrayList<>();
            JSONArray jsonArr = availabilityApi.getNearbyCarparks().getJSONArray("Carparks");
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String address = jsonObject.getString("Address");
                int availableLots = jsonObject.getInt("Available Lots");
                double price = jsonObject.getDouble("Price");
                double distance = jsonObject.getDouble("Distance From Location (in metres)");
                ParkingData data = new ParkingData(address, availableLots, price, distance);
                dataList.add(data);
            }
            this.dataList = dataList;
            ListDataProvider<ParkingData> dataProvider = new ListDataProvider<>(this.dataList);
            grid.setDataProvider(dataProvider);
        });
        // Create a button to submit the selected dates and times
        Button submitButton = new Button("Submit", event -> {
            // Get the selected start and end dates and times
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            LocalTime startTime = startTimePicker.getValue();
            LocalTime endTime = endTimePicker.getValue();
            double maxDist = doubleField.getValue();
            String locationInput = textField.getValue();
            this.startDateInput = startDate;
            this.endDateInput = endDate;
            this.startTimeInput = startTime;
            this.endTimeInput = endTime;
            this.maxDist = maxDist;
            this.locationInput = locationInput;
            InputLocation locationValidator= new InputLocation();
            locationValidator.input(locationInput);
            LocationApi locationApi = new LocationApi(locationValidator.getpostalCode());
            locationApi.urlconstructor();
            try {
                locationValidator.setLatLng(locationApi.GetLatLng());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
            try {
                availabilityApi.getAvailability(this.startDateInput, this.endDateInput, this.startTimeInput, this.endTimeInput);
            } catch (IOException | ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                availabilityApi.getNearestCarparks(maxDist, locationApi.GetLatLng());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.jsonObjCarparks = availabilityApi.getNearbyCarparks(); // assume you have a JSONArray object
            List<ParkingData> dataList = new ArrayList<>();
            JSONArray jsonArr = this.jsonObjCarparks.getJSONArray("Carparks");
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String address = jsonObject.getString("Address");
                int availableLots = jsonObject.getInt("Available Lots");
                double price = jsonObject.getDouble("Price");
                double distance = jsonObject.getDouble("Distance From Location (in metres)");
                ParkingData data = new ParkingData(address, availableLots, price, distance);
                dataList.add(data);
            }
            this.dataList = dataList;
            ListDataProvider<ParkingData> dataProvider = new ListDataProvider<>(this.dataList);
            grid.setDataProvider(dataProvider);
        });
        // Add a key press listener to the text field
            textField.addKeyPressListener(Key.ENTER, event -> {
                // Trigger the button's click event
                submitButton.click();
            });
            doubleField.addKeyPressListener(Key.ENTER, event -> {
                submitButton.click();
            }); 
        HorizontalLayout buttonLayout = new HorizontalLayout(sortByDistanceButton, sortByAvailabilityButton, sortByPriceButton);

        // Add the label, date input and time input layouts, and the submit button to the layout
        add(titleLabel, dateInputsLayout, timeInputsLayout, textField, doubleField, submitButton, buttonLayout, grid);
            
        // Set the alignment of the layout to center
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
