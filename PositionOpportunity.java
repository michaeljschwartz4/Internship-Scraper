package com.internship;

/*
 * Creates an object of type PositionOpportunity based on input from the user
 */
public class PotentialJob {
    private String dueDate, payRange, jobLink, jobTitle, company, location;
    private boolean applied;


    /*
     * Constructor for PositionOpportunity
     */
    public PotentialJob(String jobTitle, String company, String compensation, String dueDate, String location, String jobLink) {
        this.dueDate = dueDate;
        this.payRange = compensation;
        this.jobLink = jobLink;
        this.jobTitle = jobTitle;
        this.company = company;
        this.location = location;
        this.applied = false;
    }

    public String getDate() {
        return dueDate;
    }

    public String getPay() {
        return payRange;
    }

    public String getLink() {
        return jobLink;
    }

    public String getTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public boolean getStatus() {
        return applied;
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
