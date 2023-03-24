package com.example.music.model;
public class User {
        private  String Name;
        private String Password;
        private String Email;
        private String Phone;
        public User(String name, String password, String email) {
            Name = name;
            Password = password;
            Email = email;

        }



        public void setName(String name) {
            Name = name;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getName() {
            return Name;
        }

        public String getPassword() {
            return Password;
        }

        public String getEmail() {
            return Email;
        }

        public String getPhone() {
            return Phone;
        }



        public User() {
        }
}


