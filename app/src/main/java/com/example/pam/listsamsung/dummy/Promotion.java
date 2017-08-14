package com.example.pam.listsamsung.dummy;

/**
 * Created by Pam on 7/8/2017.
 */

public class Promotion {

        public String image;
        public String name;
        public String shortDescription;
        public String endDate;
        public String status;
        public String key;

        public Promotion(String image, String name, String shortDescription, String endDate, String status, String key) {
            this.image = image;
            this.name = name;
            this.shortDescription = shortDescription;
            this.endDate = endDate;
            this.status = status;
            this.key = key;
        }

        public Promotion(){

        }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }
}
