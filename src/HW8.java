import java.sql.*;
import java.util.Scanner; 

public class HW8 {
	
	private Connection con = null;
	private Statement stmt = null;

    private Scanner scanner;	
    

    HW8(){
    }
	
	public static void main(String[] args) {
		HW8 db = new HW8();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			db.con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:4567/madang",
					"yousung","1234");
			db.stmt=db.con.createStatement(); 
			db.scanner = new Scanner(System.in);
			
			int chooseNum;
			while(true) {
				
				System.out.println("1.검색, 2.삽입, 3.삭제, 4.전체보기, 5.종료");
				chooseNum = db.scanner.nextInt();
				switch(chooseNum) {
				case 1:
					db.searchData(); 
					break;
				case 2:
					db.insertData();
					break;
				case 3:
					db.deleteData();
					break;
				case 4:
					db.showData(); 
					break;
				case 5:
					return;
				default:
					System.out.println("잘못 입력하셨습니다.");
				}
			}
		}catch(Exception e) {
			System.out.println(e.toString());
		}finally {
			try {
				db.stmt.close();
				db.con.close();
				db.scanner.close();
			}catch(Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	void searchData() {
		try {
			String bookname;
			System.out.print("검색할 책이름:");
			scanner.nextLine();
			bookname = scanner.nextLine();
			String sql = "SELECT * FROM Book WHERE bookname LIKE ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, bookname);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {											
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+
						" "+rs.getString(3) + " "+rs.getInt(4));
			}
			}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	void showData() {
		try {
			System.out.println("==전체 Book table 조회 ==");
			String viewStr = "SELECT * FROM Book";
			ResultSet rs = stmt.executeQuery(viewStr);
			while(rs.next()) 
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)); 
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	void insertData() {
		int bookid;
		String bookname;
		String publisher;
		int price;
		try {
			System.out.println("== Book table Data 삽입 ==");
			System.out.print("책id:");	
			bookid = scanner.nextInt();
			System.out.print("책이름:");
			bookname = scanner.next();
			System.out.print("출판사:");
			publisher = scanner.next();
			System.out.print("가격:");
			price = scanner.nextInt();
			String sql = "INSERT INTO Book VALUES(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bookid+1);
			pstmt.setString(2, bookname);	
			pstmt.setString(3, publisher);
			pstmt.setInt(4, price);
			pstmt.executeUpdate();		
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	void deleteData() {
		int bookid;
		try {
			System.out.println("== Book table Data 삭제 ==");
			String sql = "DELETE FROM Book WHERE bookid = ?";		//쿼리준비
			PreparedStatement pstmt = con.prepareStatement(sql);	//쿼리실행객체 선언
			System.out.print("삭제할 책id:");	
			bookid = scanner.nextInt();
			pstmt.setInt(1, bookid);								//?에 값 매핑
			pstmt.executeUpdate();									//쿼리실행
			
			System.out.println("삭제완료");	
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
