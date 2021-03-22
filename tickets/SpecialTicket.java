/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

// Defines a discounted or a monthly ticket

import java.time.LocalDate;

public class SpecialTicket extends Ticket {
    String name;
    String password;
    LocalDate date;
    String email;
    
    public SpecialTicket(){ 
        super();
    }
    public SpecialTicket(String name, String password, LocalDate date, int code,Kind kind, Type type, double price, int dur,String email ){
        super(code,kind,type,price,dur);
        this.name=name;
        this.password=password;
        this.date=date;
        this.email = email;
    }
    
    public SpecialTicket(Ticket ticket, String name, String email, String password, LocalDate date){
        super(ticket.getcode(), ticket.geteidos(), ticket.getType(), ticket.getprice(), ticket.getdur());
        this.name = name;
        this.password = password;
        this.date = date;
        this.email = email;
    }

    public String getname() {
        return name;
    }

    public String getpassword() {
        return password;
    }

    public LocalDate getdate() {
        return date;
    }

    public String getemail() {
        return email;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public void setdate(LocalDate date) {
        this.date = date;
    }

    public void setemail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        
        return super.toString()
                +"\n\tName: " + this.getname()
                +"\n\tEmail: " + this.getemail()
                + (date!=null? ("\n\tDate: " + this.getdate()) : "");
        
    }
}
