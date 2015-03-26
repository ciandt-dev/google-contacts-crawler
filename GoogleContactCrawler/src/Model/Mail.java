package Model;

public class Mail {
   
   
   private String endMail;

   public Mail(){
   }
   
   public Mail(String endMail) {
       this.endMail = endMail;
   }

   public String getEndMail() {
       return endMail;
   }

   public void setEndMail(String endMail) {
       this.endMail = endMail;
   }
}