public class IDCard {
      private int idenNumber; // 6 numbers by default
      private String fullname;
      private String sex;
      private String birthday; // dd/mm/yyyy by default
      private String address;
      private int phoneNumber; // 7 numbers by default

      public IDCard(int idenNumber, String fullname, String sex, String birthday, String address, int phoneNumber) {
            this.idenNumber = idenNumber;
            this.fullname = fullname;
            this.sex = sex;
            this.birthday = birthday;
            this.address = address;
            this.phoneNumber = phoneNumber;
      }

      public int getAge() {
            String[] components = birthday.split("/");
            return 2023 - Integer.parseInt(components[2]);
      }

      public int getIdenNumber() {
            return idenNumber;
      }

      public int getPhoneNumber() {
            return phoneNumber;
      }

      public String toString() {
            return idenNumber + "," + fullname + "," + sex + "," + birthday + "," + address + "," + phoneNumber;
      }
}
