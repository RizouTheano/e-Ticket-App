/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickets;

public class Ticket { //class for the non discounted tickets
    public static enum Kind{
        Electronic, Printed
    }
    public static enum Type{
        Regular, Discounted
    }
            int code; 
            private double price;
            private int dur;
            private Kind kind;
            private Type type;

            public Ticket(int code,Kind kind, Type type, double price, int dur) {
               
                this.kind = kind;
                this.type = type;
                this.price = price;
                this.dur = dur;
                this.code=code; 
            }

            public Ticket(){ //default constructor
                
            }
            public int getcode(){
                return code;
            }
            
            public Kind geteidos() {
                return kind;
            }

            public Type getType() {
                return type;
            }

            public double getprice() {
                return price;
            }

            public int getdur() {
                return dur;
            }
            
            public void setcode(int code){
                this.code=code;
            }
            
            public void setKind(Kind kind) {
                this.kind = kind;
            }

            public void setType(Type type) {
                this.type = type;
            }

            public void setprice(double price) {
                this.price = price;
            }

            public void setdur(int dur) {
                this.dur = dur;
            }
            public void setnewprice(double newp){
                this.price=this.price+newp;
            }
            
            @Override
            public String toString(){
                return "Ticket:"
                        +"\n\tCode: " + this.getcode()
                        +"\n\tKind: " + this.geteidos().name()
                        +"\n\tType: " + this.getType().name()
                        +"\n\tDuration: " + this.getdur()
                        +"\n\tPrice: " + this.getprice();
            }
}   
            
            
            
            
            
            