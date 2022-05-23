package com.example.login;

import java.util.ArrayList;

public class DoctorTypeData {

    public String doctors[] = {"Dentist","cardiologist","Ent","General","Gynecologist","neurologist","orthopedic","Pediatrician","Pet Dog","Psychologist","Radiologist","Skin"};

    public String dentistDoctors[]={"Dr. Pooja Yadav","Dr. Aman Chitransh","Dr. Komal Kadam","Dr. Ritish Yadav","Dr. Rimi Singha","Dr. Ajit Kale"};
    public String cardiologistDoctors[]={"Dr. Komal Yadav","Dr. Aayush Patil","Dr.Swaraj Garud","Dr. Rupali Yadav","Dr. Sneha Patil","Dr. Kishor Kadam"};
    public String EntDoctors[]={"Dr. Swapnali Ghare","Dr. Aman Kale","Dr.Swapnil Sathi","Dr. Gayatri Kadam","Dr. Riya Kumar","Dr. Asish mane"};
    public String GeneralDoctors[]={"Dr. Arman Yadav","Dr. Amruta Chitransh","Dr.Kartiki Ghare","Dr. Ritish Yadav","Dr. Avanti Yadav","Dr. Satish Kale"};
    public String GynecologistDoctors[]={"Dr. Sayali Sutar","Dr. Akash Shinde","Dr. Rushi Garud","Dr. Swara Patil","Dr. Shreya Kale","Dr. Amit Patil"};
    public String neurologistDoctors[]={"Dr. Swati Kadam","Dr. Suyash Kadam","Dr.Pranita Pawar","Dr. Rupesh Ghare","Dr. Sakshi Yadav","Dr. Vedansh Kadam"};
    public String orthopedicDoctors[]={"Dr. Aarohi Kadam","Dr. Tanvi Patil","Dr.Santosh Patil","Dr. Harsh Yadav","Dr. Rani Yadav","Dr. Omkar Jadhav"};
    public String PediatricianDoctors[]={"Dr. Kavya Pawar","Dr. Arjun Jadhav","Dr.Sahil Kale","Dr. Sujata Patil","Dr. Aish Pawar","Dr. Prasad Desai"};
    public String PetDogDoctors[]={"Dr. Shital Kale","Dr. Arvind Patil","Dr.Kishor K.","Dr. Rutiki Jadhav","Dr. Sehal hosale","Dr. Karan Pawar"};
    public String PsychologistDoctors[]={"Dr. Ruhi Kadam","Dr. Manohar Kale","Dr. Jayash Rathi","Dr. Pari Pradhan","Dr. Gita Chavan","Dr. Akshay Gaurd"};
    public String RadiologistDoctors[]={"Dr. Mohini Shinde","Dr. Suhas Ghare","Dr.Vijay Jagtap","Dr. Alka Yadav","Dr. Anjana Pawar","Dr. Gaurav Gawale"};
    public String SkinDoctors[]={"Dr.Amruta Pawar","Dr. Dhiraj Yadav","Dr.Vikrant Kale","Dr. Anjali Patil","Dr. Aisha More","Dr. Praven Shinde"};

    public String timeStamp[] = {"10:00AM","10:15AM","10:30AM","10:45AM","11:00AM","11:15AM","11:30AM","11:45AM","12:00PM","12:15PM","12:30PM","12:45PM",
            "01:00PM","02:00PM","02:15PM","02:30PM","02:45PM","03:00PM","03:15PM","03:30PM","03:45PM","04:00PM","04:15PM","04:30PM","04:45PM"};

    ArrayList<String> dateUsed = new ArrayList<String>();

    public DoctorTypeData(){
        dateUsed.add("31/3/2020");
    }
    public String[] checkDocType(int docId) {

        String[] docList={};
        switch (docId){
            case 0:
                docList = dentistDoctors;
                break;
            case 1:
                docList = cardiologistDoctors;
                break;
            case 2:
                docList = EntDoctors;
                break;
            case 3:
                docList = GeneralDoctors;
                break;
            case 4:
                docList = GynecologistDoctors;
                break;
            case 5:
                docList = neurologistDoctors;
                break;
            case 6:
                docList = orthopedicDoctors;
                break;
            case 7:
                docList = PediatricianDoctors;
                break;
            case 8:
                docList = PetDogDoctors;
                break;
            case 9:
                docList = PsychologistDoctors;
                break;
            case 10:
                docList = RadiologistDoctors;
                break;
            case 11:
                docList = SkinDoctors;
                break;
        }
        return docList;

    }
}
