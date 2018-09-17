package test.com.assessment.Class;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModel implements Parcelable {

    private String name;
    private String designation;
    private String city;
    private String code;
    private String date;
    private String salary;

    public DataModel(String name, String designation, String city, String code, String date, String salary) {
        this.name = name;
        this.designation = designation;
        this.city = city;
        this.salary = salary;
        this.date = date;
        this.code = code;
    }

    protected DataModel(Parcel in) {
        name = in.readString();
        designation = in.readString();
        city = in.readString();
        code = in.readString();
        date = in.readString();
        salary = in.readString();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.name);
        parcel.writeString(this.designation);
        parcel.writeString(this.city);
        parcel.writeString(this.code);
        parcel.writeString(this.date);
        parcel.writeString(this.salary);

    }
}
