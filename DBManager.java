import java.sql.*;
import java.util.Scanner;

public class DBManager {
	static Scanner scanner = new Scanner(System.in);
	static int choice;
	static String input;
	static String sID;
	static String name;
	public static void main(String[] args) {
		boolean retry = true;
		
		showInitialPage();
		
		while(retry) {
			showMainMenu();
			
			switch(choice) {
			case 1:
				showClubInfo();
				
				switch(choice) {
				case 1:
					showSearchClubName();
					break;
					
				case 2:
					showSearchClubType();
					break;
					
				case 3:
					showClubApplication();
					break;
				}				
				break;
				
			case 2:
				showAdminMenu();
				
				switch(choice) {
				case 1:
					showClubMemberList();
					break;
					
				case 2:
					showBudgetManagement();
					break;
					
				case 3:
					showClubApplicantList();
					break;
				}				
				break;
				
			case 3:
				showTotalAdminMenu();
				break;
				
			case 4:
				System.out.println("\n프로그램을 종료합니다!");
				retry = false;
				break;
			}
		}
	}
	
	public static void showInitialPage() {
		boolean retry = true;
		
		while(retry) {
			System.out.println("\n<아주대 동아리 관리 프로그램에 오신 것을 환영합니다>\n");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.print("\n입력: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			if(choice == 1) {
				register();
				break;
			}
			else if(choice == 2) {
				login();
				break;
			}
			else {
				System.out.println("\n1, 2만 입력해주세요!");
				System.out.println("초기화면으로 돌아갑니다!");
			}
		}
	}
	
	public static void register() {		
		System.out.println("\n<회원가입>\n");
		System.out.print("학번 입력: ");
		sID = scanner.nextLine();
		
		System.out.print("이름 입력: ");
		name = scanner.nextLine();
		
		System.out.print("학과 입력: ");
		String department = scanner.nextLine();
		
		System.out.print("이메일 입력: ");
		String email = scanner.nextLine();
		
		String sql = "insert into apply values('" + sID + "', '" + name + "', '" + department + "', " + "null, null, '" + email + "');";
		excuteUpdate(sql);
	}
	
	public static void login() {
		System.out.println("\n<로그인>\n");
		System.out.print("학번 입력: ");
		sID = scanner.nextLine();
		
		System.out.print("이름 입력: ");
		name = scanner.nextLine();
	}
	
	public static void showMainMenu() {
		System.out.println("\n<아주대 동아리 관리 프로그램>");
		System.out.println("이름: " + name + "    학번: " + sID + "\n");
		System.out.println("MENU");
		System.out.println("1. 동아리 정보 보기");
		System.out.println("2. 동아리 관리(관리자)");
		System.out.println("3. 동아리 생성 및 삭제(총관리자)");
		System.out.println("4. 종료하기");
		System.out.print("\n입력: ");		
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice < 1 || choice > 4) {
			System.out.println("\n1, 2, 3, 4만 입력해주세요!");
			System.out.println("메인메뉴로 돌아갑니다!");
		}
	}
	
	public static void showClubInfo() {
		Connection conn = null;
		Statement state = null;
		
		System.out.println("\n<동아리 정보 보기>\n");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			String sql = "select * from club;";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String clubName = rs.getString("동아리_이름");
				String clubDivision = rs.getString("동아리_소속");
				String clubType = rs.getString("동아리_유형");
				String clubIntroduction = rs.getString("동아리_소개");
				System.out.println("----------------------------------------------");
				System.out.println("동아리_이름: " + clubName);
				System.out.println("동아리_소속: " + clubDivision);
				System.out.println("동아리_유형: " + clubType);
				System.out.println("동아리_소개: " + clubIntroduction);
			}
			System.out.println("----------------------------------------------\n");
			
			rs.close();
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
		
		System.out.println("1. 동아리 이름 검색");
		System.out.println("2. 동아리 유형 검색");
		System.out.println("3. 동아리 신청");
		System.out.print("\n입력: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice < 1 || choice > 3) {
			System.out.println("\n1, 2, 3만 입력해주세요!");
			System.out.println("메인메뉴로 돌아갑니다!");
		}
	}
	
	public static void showSearchClubName() {
		Connection conn = null;
		Statement state = null;
		
		System.out.println("\n<동아리 이름 검색>\n");
		System.out.println("검색할 동아리의 이름을 입력해주세요.");
		System.out.print("\n입력: ");
		input = scanner.nextLine();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			String sql = "select * from club where 동아리_이름 = '" + input + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String clubName = rs.getString("동아리_이름");
				String clubDivision = rs.getString("동아리_소속");
				String clubType = rs.getString("동아리_유형");
				String clubIntroduction = rs.getString("동아리_소개");
				System.out.println("----------------------------------------------");
				System.out.println("동아리_이름: " + clubName);
				System.out.println("동아리_소속: " + clubDivision);
				System.out.println("동아리_유형: " + clubType);
				System.out.println("동아리_소개: " + clubIntroduction);
			}
			System.out.println("----------------------------------------------\n");			
			System.out.println("메인메뉴로 돌아갑니다!");
			
			rs.close();
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
	}
	
	public static void showSearchClubType() {
		Connection conn = null;
		Statement state = null;
		
		System.out.println("\n<동아리 유형 검색>\n");
		System.out.println("검색할 동아리의 유형을 입력해주세요.");
		System.out.println("[IT, 문학, 스포츠, 종교, 봉사, 음악, 언어, 예술, 취미]");
		System.out.print("\n입력: ");
		input = scanner.nextLine();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			String sql = "select * from club where 동아리_유형 = '" + input + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String clubName = rs.getString("동아리_이름");
				String clubDivision = rs.getString("동아리_소속");
				String clubType = rs.getString("동아리_유형");
				String clubIntroduction = rs.getString("동아리_소개");
				System.out.println("----------------------------------------------");
				System.out.println("동아리_이름: " + clubName);
				System.out.println("동아리_소속: " + clubDivision);
				System.out.println("동아리_유형: " + clubType);
				System.out.println("동아리_소개: " + clubIntroduction);
			}
			System.out.println("----------------------------------------------\n");			
			System.out.println("메인메뉴로 돌아갑니다!");
			
			rs.close();
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
	}
	
	public static void showClubApplication() {
		System.out.println("\n<동아리 신청>\n");
		System.out.println("신청을 원하는 동아리의 이름을 입력해주세요.");
		System.out.print("\n입력: ");
		input = scanner.nextLine();
		
		String sql = "update apply set 지원한_동아리 = '" + input + "', 입부_승인여부 = 'N' "
								+ "where 학번 = '" + sID + "';";
		excuteUpdate(sql);
		
		System.out.println("메인메뉴로 돌아갑니다!");
	}
	
	public static void showAdminMenu() {
		System.out.println("\n<동아리 관리(관리자)>\n");
		System.out.println("1. 동아리 회원 목록");
		System.out.println("2. 예산관리");
		System.out.println("3. 동아리 신청자 목록");
		System.out.print("\n입력: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice < 1 || choice > 3) {
			System.out.println("\n1, 2, 3만 입력해주세요!");
			System.out.println("메인메뉴로 돌아갑니다!");
		}
	}
	
	public static void showClubMemberList() {
		Connection conn = null;
		Statement state = null;
		String clubName = "";
		String sql;
		int memberCount = 0;
		
		System.out.println("\n<동아리 회원 목록>\n");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			sql = "select 동아리_이름 from club_member where 학번 = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("동아리_이름");
			}
			
			sql = "select * from club_member where 동아리_이름 = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String sID = rs.getString("학번");
				String name = rs.getString("이름");
				String department = rs.getString("학과");
				String authority = rs.getString("사용자_권한");
				String email = rs.getString("이메일");
				System.out.println("----------------------------------------------");
				System.out.println("학번: " + sID);
				System.out.println("이름: " + name);
				System.out.println("학과: " + department);
				System.out.println("사용자_권한: " + authority);
				System.out.println("이메일: " + email);
			}
			System.out.println("----------------------------------------------\n");
			
			sql = "select count(*) as memberCount from club_member where 동아리_이름 = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				memberCount = rs.getInt("memberCount");
			}
			
			System.out.printf("회원수: %d\n\n", memberCount);
			
			rs.close();
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
		
		System.out.println("1. 회원 삭제");
		System.out.print("\n입력: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1) {
			System.out.println("\n삭제를 원하는 회원의 학번을 입력해주세요.");
			System.out.print("\n입력: ");
			input = scanner.nextLine();
			
			sql = "delete from club_member where 학번 = '" + input + "';";
			excuteUpdate(sql);
			
			System.out.println("메인메뉴로 돌아갑니다!");
		}					
		else {
			System.out.println("\n1만 입력해주세요!");
			System.out.println("메인메뉴로 돌아갑니다!");
		}					
	}
	
	public static void showBudgetManagement() {
		Connection conn = null;
		Statement state = null;
		String clubName = "";
		String sql;
		int incomeExpenditureTotal = 0;
		
		System.out.println("\n<예산관리>\n");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			sql = "select 동아리_이름 from club_member where 학번 = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("동아리_이름");
			}

			sql = "select * from budget where 동아리_이름 = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String date = rs.getString("날짜");
				String change = rs.getString("변동액");
				String detail = rs.getString("내용");
				System.out.println("----------------------------------------------");
				System.out.println("날짜: " + date);
				System.out.println("변동액: " + change);
				System.out.println("내용: " + detail);
			}
			System.out.println("----------------------------------------------\n");
			
			sql = "select sum(변동액) as incomeExpenditureTotal from budget where 동아리_이름 = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				incomeExpenditureTotal = rs.getInt("incomeExpenditureTotal");
			}
			
			System.out.printf("수입지출합계: %d\n\n", incomeExpenditureTotal);
			
			rs.close();
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
		
		System.out.println("1. 예산목록 추가하기");
		System.out.println("2. 예산목록 삭제하기");
		System.out.print("\n입력: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1) {
			insertBudgetList();
		}
		else if(choice == 2) {
			deleteBudgetList();
		}
		else {
			System.out.println("\n1, 2만 입력해주세요!");
			System.out.println("메인메뉴로 돌아갑니다!");
		}
	}
	
	public static void insertBudgetList() {
		Connection conn = null;
		Statement state = null;
		String clubName = "";
		String sql;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			sql = "select 동아리_이름 from club_member where 학번 = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("동아리_이름");
			}

			rs.close();
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
		
		System.out.println("\n<예산목록 추가하기>\n");

		System.out.print("날짜 입력(YYYY-MM-DD): ");
		String date = scanner.nextLine();
		
		System.out.print("변동액 입력: ");
		String change = scanner.nextLine();
		
		System.out.print("내용 입력: ");
		String detail = scanner.nextLine();
		
		sql = "insert into budget values('" + clubName + "', '" + date + "', '" + change + "', '" + detail + "');";
		excuteUpdate(sql);
		
		System.out.println("메인메뉴로 돌아갑니다!");
	}
	
	public static void deleteBudgetList() {
		Connection conn = null;
		Statement state = null;
		String clubName = "";
		String sql;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			sql = "select 동아리_이름 from club_member where 학번 = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("동아리_이름");
			}

			rs.close();
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
		
		System.out.println("\n<예산목록 삭제하기>\n");

		System.out.println("삭제를 원하는 예산목록의 날짜를 입력해주세요.");
		System.out.print("\n입력: ");
		input = scanner.nextLine();
		
		sql = "delete from budget where 동아리_이름 = '" + clubName + "' and 날짜 = '" + input + "';";
		excuteUpdate(sql);
		
		System.out.println("메인메뉴로 돌아갑니다!");
	}
	public static void showClubApplicantList() {
		Connection conn = null;
		Statement state = null;
		String clubName = "";
		String sql;
		
		System.out.println("\n<동아리 신청자 목록>\n");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			sql = "select 동아리_이름 from club_member where 학번 = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("동아리_이름");
			}
			
			sql = "select * from apply where 지원한_동아리 = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String sID = rs.getString("학번");
				String name = rs.getString("이름");
				String department = rs.getString("학과");
				String email = rs.getString("이메일");
				System.out.println("----------------------------------------------");
				System.out.println("학번: " + sID);
				System.out.println("이름: " + name);
				System.out.println("학과: " + department);
				System.out.println("이메일: " + email);
			}
			System.out.println("----------------------------------------------\n");

			rs.close();
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
		
		System.out.println("1. 신청자 승인");
		System.out.println("2. 신청자 거절");
		System.out.print("\n입력: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1) {
			approveApplicant();
		}
		else if(choice == 2) {
			refuseApplicant();
		}
		else {
			System.out.println("\n1, 2만 입력해주세요!");
			System.out.println("메인메뉴로 돌아갑니다!");
		}
	}
	
	public static void approveApplicant() {
		System.out.println("\n<신청자 승인>\n");
		System.out.println("승인을 원하는 학생의 학번을 입력해주세요.");
		System.out.print("\n입력: ");
		input = scanner.nextLine();
		
		String sql1 = "update apply set 입부_승인여부 = 'Y' where 학번 = '" + input + "';";		
		String sql2 = "delete from apply where 학번 = '" + input + "';";
		excuteUpdateTransaction(sql1, sql2);
		
		System.out.println("메인메뉴로 돌아갑니다!");
	}
	
	public static void refuseApplicant() {
		System.out.println("\n<신청자 거절>\n");
		System.out.println("거절을 원하는 학생의 학번을 입력해주세요.");
		System.out.print("\n입력: ");
		input = scanner.nextLine();
		
		String sql = "delete from apply where 학번 = '" + input + "';";
		excuteUpdate(sql);
		
		System.out.println("메인메뉴로 돌아갑니다!");
	}
	
	public static void showTotalAdminMenu() {
		System.out.println("\n<동아리 생성 및 삭제(총관리자)>\n");
		System.out.println("1. 동아리 생성");
		System.out.println("2. 동아리 삭제");
		System.out.print("\n입력: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1) {
			createClub();
		}					
		else if(choice == 2) {
			deleteClub();
		}
		else {
			System.out.println("\n1, 2만 입력해주세요!");
			System.out.println("메인메뉴로 돌아갑니다!");
		}
	}
	
	public static void createClub() {
		System.out.println("\n<동아리 생성>\n");

		System.out.print("동아리 이름 입력: ");
		String clubName = scanner.nextLine();
		
		System.out.print("동아리 소속 입력: ");
		String clubDivision = scanner.nextLine();
		
		System.out.print("동아리 유형 입력: ");
		String clubType = scanner.nextLine();
		
		System.out.print("동아리 소개 입력: ");
		String clubIntroduction = scanner.nextLine();
		
		String sql = "insert into club values('" + clubName + "', '" + clubDivision + "', '" + clubType + "', '" + clubIntroduction + "');";
		excuteUpdate(sql);
		
		System.out.println("메인메뉴로 돌아갑니다!");
	}
	
	public static void deleteClub() {
		System.out.println("\n<동아리 삭제>\n");
		
		System.out.println("삭제를 원하는 동아리의 이름을 입력해주세요.");
		System.out.print("\n입력: ");
		input = scanner.nextLine();
		
		String sql = "delete from club where 동아리_이름 = '" + input + "';";
		excuteUpdate(sql);
		
		System.out.println("메인메뉴로 돌아갑니다!");
	}
	
	public static void excuteUpdate(String sql) {
		Connection conn = null;
		Statement state = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			state.executeUpdate(sql);
			
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
	}
	
	public static void excuteUpdateTransaction(String sql, String sql2) {
		Connection conn = null;
		Statement state = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			conn.setAutoCommit(false);
			state = conn.createStatement();
			state.executeUpdate(sql);
			state.executeUpdate(sql2);
			conn.commit();
			
			state.close();
			conn.close();
		}
		catch(Exception e) {System.out.println("[MYSQL CONNECTION FAILED]\n");}
		
		finally {
			try {
				if(state != null)
					state.close();
			}
			catch(SQLException ex1) {}
			
			try {
				if(conn!= null)
					conn.close();
			}
			catch(SQLException ex2) {}
		}
	}
}