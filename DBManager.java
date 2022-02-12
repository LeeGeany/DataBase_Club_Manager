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
				System.out.println("\n���α׷��� �����մϴ�!");
				retry = false;
				break;
			}
		}
	}
	
	public static void showInitialPage() {
		boolean retry = true;
		
		while(retry) {
			System.out.println("\n<���ִ� ���Ƹ� ���� ���α׷��� ���� ���� ȯ���մϴ�>\n");
			System.out.println("1. ȸ������");
			System.out.println("2. �α���");
			System.out.print("\n�Է�: ");
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
				System.out.println("\n1, 2�� �Է����ּ���!");
				System.out.println("�ʱ�ȭ������ ���ư��ϴ�!");
			}
		}
	}
	
	public static void register() {		
		System.out.println("\n<ȸ������>\n");
		System.out.print("�й� �Է�: ");
		sID = scanner.nextLine();
		
		System.out.print("�̸� �Է�: ");
		name = scanner.nextLine();
		
		System.out.print("�а� �Է�: ");
		String department = scanner.nextLine();
		
		System.out.print("�̸��� �Է�: ");
		String email = scanner.nextLine();
		
		String sql = "insert into apply values('" + sID + "', '" + name + "', '" + department + "', " + "null, null, '" + email + "');";
		excuteUpdate(sql);
	}
	
	public static void login() {
		System.out.println("\n<�α���>\n");
		System.out.print("�й� �Է�: ");
		sID = scanner.nextLine();
		
		System.out.print("�̸� �Է�: ");
		name = scanner.nextLine();
	}
	
	public static void showMainMenu() {
		System.out.println("\n<���ִ� ���Ƹ� ���� ���α׷�>");
		System.out.println("�̸�: " + name + "    �й�: " + sID + "\n");
		System.out.println("MENU");
		System.out.println("1. ���Ƹ� ���� ����");
		System.out.println("2. ���Ƹ� ����(������)");
		System.out.println("3. ���Ƹ� ���� �� ����(�Ѱ�����)");
		System.out.println("4. �����ϱ�");
		System.out.print("\n�Է�: ");		
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice < 1 || choice > 4) {
			System.out.println("\n1, 2, 3, 4�� �Է����ּ���!");
			System.out.println("���θ޴��� ���ư��ϴ�!");
		}
	}
	
	public static void showClubInfo() {
		Connection conn = null;
		Statement state = null;
		
		System.out.println("\n<���Ƹ� ���� ����>\n");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			String sql = "select * from club;";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String clubName = rs.getString("���Ƹ�_�̸�");
				String clubDivision = rs.getString("���Ƹ�_�Ҽ�");
				String clubType = rs.getString("���Ƹ�_����");
				String clubIntroduction = rs.getString("���Ƹ�_�Ұ�");
				System.out.println("----------------------------------------------");
				System.out.println("���Ƹ�_�̸�: " + clubName);
				System.out.println("���Ƹ�_�Ҽ�: " + clubDivision);
				System.out.println("���Ƹ�_����: " + clubType);
				System.out.println("���Ƹ�_�Ұ�: " + clubIntroduction);
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
		
		System.out.println("1. ���Ƹ� �̸� �˻�");
		System.out.println("2. ���Ƹ� ���� �˻�");
		System.out.println("3. ���Ƹ� ��û");
		System.out.print("\n�Է�: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice < 1 || choice > 3) {
			System.out.println("\n1, 2, 3�� �Է����ּ���!");
			System.out.println("���θ޴��� ���ư��ϴ�!");
		}
	}
	
	public static void showSearchClubName() {
		Connection conn = null;
		Statement state = null;
		
		System.out.println("\n<���Ƹ� �̸� �˻�>\n");
		System.out.println("�˻��� ���Ƹ��� �̸��� �Է����ּ���.");
		System.out.print("\n�Է�: ");
		input = scanner.nextLine();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			String sql = "select * from club where ���Ƹ�_�̸� = '" + input + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String clubName = rs.getString("���Ƹ�_�̸�");
				String clubDivision = rs.getString("���Ƹ�_�Ҽ�");
				String clubType = rs.getString("���Ƹ�_����");
				String clubIntroduction = rs.getString("���Ƹ�_�Ұ�");
				System.out.println("----------------------------------------------");
				System.out.println("���Ƹ�_�̸�: " + clubName);
				System.out.println("���Ƹ�_�Ҽ�: " + clubDivision);
				System.out.println("���Ƹ�_����: " + clubType);
				System.out.println("���Ƹ�_�Ұ�: " + clubIntroduction);
			}
			System.out.println("----------------------------------------------\n");			
			System.out.println("���θ޴��� ���ư��ϴ�!");
			
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
		
		System.out.println("\n<���Ƹ� ���� �˻�>\n");
		System.out.println("�˻��� ���Ƹ��� ������ �Է����ּ���.");
		System.out.println("[IT, ����, ������, ����, ����, ����, ���, ����, ���]");
		System.out.print("\n�Է�: ");
		input = scanner.nextLine();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			String sql = "select * from club where ���Ƹ�_���� = '" + input + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String clubName = rs.getString("���Ƹ�_�̸�");
				String clubDivision = rs.getString("���Ƹ�_�Ҽ�");
				String clubType = rs.getString("���Ƹ�_����");
				String clubIntroduction = rs.getString("���Ƹ�_�Ұ�");
				System.out.println("----------------------------------------------");
				System.out.println("���Ƹ�_�̸�: " + clubName);
				System.out.println("���Ƹ�_�Ҽ�: " + clubDivision);
				System.out.println("���Ƹ�_����: " + clubType);
				System.out.println("���Ƹ�_�Ұ�: " + clubIntroduction);
			}
			System.out.println("----------------------------------------------\n");			
			System.out.println("���θ޴��� ���ư��ϴ�!");
			
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
		System.out.println("\n<���Ƹ� ��û>\n");
		System.out.println("��û�� ���ϴ� ���Ƹ��� �̸��� �Է����ּ���.");
		System.out.print("\n�Է�: ");
		input = scanner.nextLine();
		
		String sql = "update apply set ������_���Ƹ� = '" + input + "', �Ժ�_���ο��� = 'N' "
								+ "where �й� = '" + sID + "';";
		excuteUpdate(sql);
		
		System.out.println("���θ޴��� ���ư��ϴ�!");
	}
	
	public static void showAdminMenu() {
		System.out.println("\n<���Ƹ� ����(������)>\n");
		System.out.println("1. ���Ƹ� ȸ�� ���");
		System.out.println("2. �������");
		System.out.println("3. ���Ƹ� ��û�� ���");
		System.out.print("\n�Է�: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice < 1 || choice > 3) {
			System.out.println("\n1, 2, 3�� �Է����ּ���!");
			System.out.println("���θ޴��� ���ư��ϴ�!");
		}
	}
	
	public static void showClubMemberList() {
		Connection conn = null;
		Statement state = null;
		String clubName = "";
		String sql;
		int memberCount = 0;
		
		System.out.println("\n<���Ƹ� ȸ�� ���>\n");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			sql = "select ���Ƹ�_�̸� from club_member where �й� = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("���Ƹ�_�̸�");
			}
			
			sql = "select * from club_member where ���Ƹ�_�̸� = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String sID = rs.getString("�й�");
				String name = rs.getString("�̸�");
				String department = rs.getString("�а�");
				String authority = rs.getString("�����_����");
				String email = rs.getString("�̸���");
				System.out.println("----------------------------------------------");
				System.out.println("�й�: " + sID);
				System.out.println("�̸�: " + name);
				System.out.println("�а�: " + department);
				System.out.println("�����_����: " + authority);
				System.out.println("�̸���: " + email);
			}
			System.out.println("----------------------------------------------\n");
			
			sql = "select count(*) as memberCount from club_member where ���Ƹ�_�̸� = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				memberCount = rs.getInt("memberCount");
			}
			
			System.out.printf("ȸ����: %d\n\n", memberCount);
			
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
		
		System.out.println("1. ȸ�� ����");
		System.out.print("\n�Է�: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1) {
			System.out.println("\n������ ���ϴ� ȸ���� �й��� �Է����ּ���.");
			System.out.print("\n�Է�: ");
			input = scanner.nextLine();
			
			sql = "delete from club_member where �й� = '" + input + "';";
			excuteUpdate(sql);
			
			System.out.println("���θ޴��� ���ư��ϴ�!");
		}					
		else {
			System.out.println("\n1�� �Է����ּ���!");
			System.out.println("���θ޴��� ���ư��ϴ�!");
		}					
	}
	
	public static void showBudgetManagement() {
		Connection conn = null;
		Statement state = null;
		String clubName = "";
		String sql;
		int incomeExpenditureTotal = 0;
		
		System.out.println("\n<�������>\n");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			sql = "select ���Ƹ�_�̸� from club_member where �й� = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("���Ƹ�_�̸�");
			}

			sql = "select * from budget where ���Ƹ�_�̸� = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String date = rs.getString("��¥");
				String change = rs.getString("������");
				String detail = rs.getString("����");
				System.out.println("----------------------------------------------");
				System.out.println("��¥: " + date);
				System.out.println("������: " + change);
				System.out.println("����: " + detail);
			}
			System.out.println("----------------------------------------------\n");
			
			sql = "select sum(������) as incomeExpenditureTotal from budget where ���Ƹ�_�̸� = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				incomeExpenditureTotal = rs.getInt("incomeExpenditureTotal");
			}
			
			System.out.printf("���������հ�: %d\n\n", incomeExpenditureTotal);
			
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
		
		System.out.println("1. ������ �߰��ϱ�");
		System.out.println("2. ������ �����ϱ�");
		System.out.print("\n�Է�: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1) {
			insertBudgetList();
		}
		else if(choice == 2) {
			deleteBudgetList();
		}
		else {
			System.out.println("\n1, 2�� �Է����ּ���!");
			System.out.println("���θ޴��� ���ư��ϴ�!");
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
			
			sql = "select ���Ƹ�_�̸� from club_member where �й� = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("���Ƹ�_�̸�");
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
		
		System.out.println("\n<������ �߰��ϱ�>\n");

		System.out.print("��¥ �Է�(YYYY-MM-DD): ");
		String date = scanner.nextLine();
		
		System.out.print("������ �Է�: ");
		String change = scanner.nextLine();
		
		System.out.print("���� �Է�: ");
		String detail = scanner.nextLine();
		
		sql = "insert into budget values('" + clubName + "', '" + date + "', '" + change + "', '" + detail + "');";
		excuteUpdate(sql);
		
		System.out.println("���θ޴��� ���ư��ϴ�!");
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
			
			sql = "select ���Ƹ�_�̸� from club_member where �й� = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("���Ƹ�_�̸�");
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
		
		System.out.println("\n<������ �����ϱ�>\n");

		System.out.println("������ ���ϴ� �������� ��¥�� �Է����ּ���.");
		System.out.print("\n�Է�: ");
		input = scanner.nextLine();
		
		sql = "delete from budget where ���Ƹ�_�̸� = '" + clubName + "' and ��¥ = '" + input + "';";
		excuteUpdate(sql);
		
		System.out.println("���θ޴��� ���ư��ϴ�!");
	}
	public static void showClubApplicantList() {
		Connection conn = null;
		Statement state = null;
		String clubName = "";
		String sql;
		
		System.out.println("\n<���Ƹ� ��û�� ���>\n");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ajouclubmanagement?serverTimezone=UTC", 
                    "root", "1234"); 
			state = conn.createStatement();
			
			sql = "select ���Ƹ�_�̸� from club_member where �й� = '" + sID + "';";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				clubName = rs.getString("���Ƹ�_�̸�");
			}
			
			sql = "select * from apply where ������_���Ƹ� = '" + clubName + "';";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String sID = rs.getString("�й�");
				String name = rs.getString("�̸�");
				String department = rs.getString("�а�");
				String email = rs.getString("�̸���");
				System.out.println("----------------------------------------------");
				System.out.println("�й�: " + sID);
				System.out.println("�̸�: " + name);
				System.out.println("�а�: " + department);
				System.out.println("�̸���: " + email);
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
		
		System.out.println("1. ��û�� ����");
		System.out.println("2. ��û�� ����");
		System.out.print("\n�Է�: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1) {
			approveApplicant();
		}
		else if(choice == 2) {
			refuseApplicant();
		}
		else {
			System.out.println("\n1, 2�� �Է����ּ���!");
			System.out.println("���θ޴��� ���ư��ϴ�!");
		}
	}
	
	public static void approveApplicant() {
		System.out.println("\n<��û�� ����>\n");
		System.out.println("������ ���ϴ� �л��� �й��� �Է����ּ���.");
		System.out.print("\n�Է�: ");
		input = scanner.nextLine();
		
		String sql1 = "update apply set �Ժ�_���ο��� = 'Y' where �й� = '" + input + "';";		
		String sql2 = "delete from apply where �й� = '" + input + "';";
		excuteUpdateTransaction(sql1, sql2);
		
		System.out.println("���θ޴��� ���ư��ϴ�!");
	}
	
	public static void refuseApplicant() {
		System.out.println("\n<��û�� ����>\n");
		System.out.println("������ ���ϴ� �л��� �й��� �Է����ּ���.");
		System.out.print("\n�Է�: ");
		input = scanner.nextLine();
		
		String sql = "delete from apply where �й� = '" + input + "';";
		excuteUpdate(sql);
		
		System.out.println("���θ޴��� ���ư��ϴ�!");
	}
	
	public static void showTotalAdminMenu() {
		System.out.println("\n<���Ƹ� ���� �� ����(�Ѱ�����)>\n");
		System.out.println("1. ���Ƹ� ����");
		System.out.println("2. ���Ƹ� ����");
		System.out.print("\n�Է�: ");
		choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1) {
			createClub();
		}					
		else if(choice == 2) {
			deleteClub();
		}
		else {
			System.out.println("\n1, 2�� �Է����ּ���!");
			System.out.println("���θ޴��� ���ư��ϴ�!");
		}
	}
	
	public static void createClub() {
		System.out.println("\n<���Ƹ� ����>\n");

		System.out.print("���Ƹ� �̸� �Է�: ");
		String clubName = scanner.nextLine();
		
		System.out.print("���Ƹ� �Ҽ� �Է�: ");
		String clubDivision = scanner.nextLine();
		
		System.out.print("���Ƹ� ���� �Է�: ");
		String clubType = scanner.nextLine();
		
		System.out.print("���Ƹ� �Ұ� �Է�: ");
		String clubIntroduction = scanner.nextLine();
		
		String sql = "insert into club values('" + clubName + "', '" + clubDivision + "', '" + clubType + "', '" + clubIntroduction + "');";
		excuteUpdate(sql);
		
		System.out.println("���θ޴��� ���ư��ϴ�!");
	}
	
	public static void deleteClub() {
		System.out.println("\n<���Ƹ� ����>\n");
		
		System.out.println("������ ���ϴ� ���Ƹ��� �̸��� �Է����ּ���.");
		System.out.print("\n�Է�: ");
		input = scanner.nextLine();
		
		String sql = "delete from club where ���Ƹ�_�̸� = '" + input + "';";
		excuteUpdate(sql);
		
		System.out.println("���θ޴��� ���ư��ϴ�!");
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