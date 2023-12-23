package com.internship;

/*
 * Creates an object of type PositionOpportunity when the scraper comes across a new job
 */
public class PositionOpportunity {
    private String dueDate, payRange, jobLink, jobTitle, company, location;
    private boolean applied;


    /*
     * Constructor for PositionOpportunity
     * 
     * All inputs are values scraped from the internet
     */
    public PositionOpportunity(String dueDate, String payRange, String jobLink, String jobTitle, String company, String location, boolean applied) {
        this.dueDate = dueDate;
        this.payRange = payRange;
        this.jobLink = jobLink;
        this.jobTitle = jobTitle;
        this.company = company;
        this.location = location;
        this.applied = false;
    }

    public String toString() {
        return (
            "Job Title: " + jobTitle + " | " + 
            "Company: " + company + " | " +
            "Location: " + location + " | " +
            "Application Due: " + dueDate + " | " +
            "Pay Range : " + payRange + " | " + jobLink
        );
    }
}