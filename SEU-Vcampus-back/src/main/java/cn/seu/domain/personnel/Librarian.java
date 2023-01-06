package cn.seu.domain.personnel;

import java.util.Date;

public class Librarian extends Person {

    public Librarian(String account_number, String password, int jurisdiction) {
        this.account_number = account_number;
        this.setpassword(password);
        this.setjurisdiction(2);
    }

}
