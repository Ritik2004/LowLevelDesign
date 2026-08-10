package LibraryManagementSystem;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

enum BookCopyStatus{
     AVAILABLE,
     ISSUED,
     RESERVE,
     LOST
}

class Member{
    int id;
    String name;
   public Member(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
class Book{
    int id;
    String title;
    String ISBN;
    String author;
    List<BookCopy> copies;
     public Book(int id, String title, String ISBN, String author) {
        this.id = id;
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.copies = new ArrayList<>();
    }
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getIsbn(){
        return ISBN;
    }
    public String getAuthor(){
        return author;
    }
}

class Loan{
    int id;
    Member member;
    BookCopy bookcopy;
    LocalDate issuedate;
    LocalDate duedate;
     LocalDate returnDate;
    
      public Loan(int id, Member member, BookCopy bookcopy) {
        this.id = id;
        this.member = member;
        this.bookcopy = bookcopy;
        this.issuedate = LocalDate.now();
        this.duedate = LocalDate.now().plusDays(14);
    }


    public boolean isOverDue(){
       return LocalDate.now().isAfter(duedate);
    }
    public double calculateFine(){
        if(!isOverDue()){
            return 0;
        }
        System.out.println("Your fine is 10");
        return 10;
    }
    public void closeLoan(){
        returnDate = LocalDate.now();
        bookcopy.markAvailable();
    }
}

class BookCopy{
    int copyid;
    Book book;
    BookCopyStatus status;
  
     public BookCopy(int copyid, Book book) {
        this.copyid = copyid;
        this.book = book;
        this.status = BookCopyStatus.AVAILABLE;
    }
    public boolean isAvailable(){
        return status == BookCopyStatus.AVAILABLE;

    }
    public void markIssued(){
           status = BookCopyStatus.ISSUED;
    }
    public void markAvailable(){
        status = BookCopyStatus.AVAILABLE;
    }
    public BookCopyStatus getStatus(){
           return status;
    }
}

class LibraryManagementSystem{
    List<Book> books;
    List<Member> members;
    List<Loan> loans;
    List<BookCopy>copies;
      public LibraryManagementSystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public Book searchBook(String title){
        for(Book book : books){
            if(book.getTitle().equals(title)){
                return book;
            }
        }
        return null;
    }
    public boolean checkAvailability(Book book){
          for(BookCopy copy:book.copies){
            if(copy.isAvailable()){
                return true;
            }
          }
          return false;
    }

    public Loan issueBook(Member member, String title){
       Book book = searchBook(title);

       if(book == null){
        return null;
       }
       if(!checkAvailability(book)){
        return null;
       }
       //issue book
       for(BookCopy copy:book.copies){
            if(copy.isAvailable()){
                copy.markIssued();
                
                Loan loan = new Loan(loans.size()+1, member, copy);
                loans.add(loan);
                System.out.println("Book "+book.getTitle()+" is issued to "+member.name);
                return loan;
            }
       }
       return null;
      
    }

    public double returnBook(Loan loan){
      loan.closeLoan();
      System.out.println("Book is returned");
      System.out.println("Your fine is 10");
      return loan.calculateFine();
    }
}

public class Library {
    public static void main(String[] args){
          LibraryManagementSystem library = new LibraryManagementSystem();

          Book book = new Book(
            1,"Clean code","12345","Robert"
          );
          BookCopy copy1 = new BookCopy(101, book);
          BookCopy copy2 = new BookCopy(102, book);
          
          book.copies.add(copy1);
          book.copies.add(copy2);
          
          library.books.add(book);

          Member member1 = new Member(1,"Ritik");
          Member member2 = new Member(2,"Messi");
          library.members.add(member1);
          library.members.add(member2);
         
             Loan loan1 = library.issueBook(member1, "Clean code");
        //   System.out.println();

        ///return
         library.returnBook(loan1);

    } 
}
