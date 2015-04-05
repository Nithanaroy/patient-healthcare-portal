package edu.asu.se.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PatientDAO {

	private DataSource dataSource;

	/**
	 * Fetch the connection string
	 */
	public PatientDAO() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx
					.lookup("java:comp/env/jdbc/HospitalDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new patient to patient table
	 * 
	 * @param p
	 *            Patient object having all required attributes to save to DB
	 * @return patient object if created, else null
	 */
	public Patient addPatient(Patient p) {
		try {
			Connection con = dataSource.getConnection();

			String loginSql = "INSERT INTO login (username,pwd,usertype)"
					+ "VALUES ('" + p.getUserName() + "','" + p.getPassword()
					+ "','" + p.getUserType() + "')";

			System.out.println(loginSql);

			PreparedStatement ps = con.prepareStatement(loginSql,
					Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int newPatientId = 0;
			if (rs.next()) {
				newPatientId = rs.getInt(1);
			} else {
				// do what you have to do
			}
			ps.close();

			String sql = "INSERT INTO patient (id,username, firstname, lastname, gender, email, mobilenumber, address, zipcode,age) "
					+ "VALUES ('"
					+ newPatientId
					+ "','"
					+ p.getUserName()
					+ "','"
					+ p.getFirstName()
					+ "', '"
					+ p.getLastName()
					+ "','"
					+ p.getGender()
					+ "','"
					+ p.getEmail()
					+ "','"
					+ p.getMobileNumber()
					+ "','"
					+ p.getAddress()
					+ "','"
					+ p.getZipCode() + "','" + p.getAge() + "')";

			System.out.println(sql);
			PreparedStatement ps1 = con.prepareStatement(sql);
			ps1.executeUpdate();
			con.close();
			return findPatient(newPatientId);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}

	public Patient findPatient(int id) {
		Patient e = null;
		try {
			Connection con = dataSource.getConnection();
			String sql = "select * from patient where id  = " + id;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				e = new Patient(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10));
			}
			con.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return e;
	}
<<<<<<< HEAD
	
	public int addESASRecord(EsasRecord esas)
	{
		int success=0;
		try {
			Connection con = dataSource.getConnection();
			String sql ="INSERT INTO `se_project`.`esas`"
					+ "(`username`,`pain`,`tiredness`,`nausea`,`depression`,"
					+ "`anxiety`,`drowsiness`,`appetite`,`wellbeing`,`breath`,`date`)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,esas.getUserName());
			ps.setString(2, esas.getPain());
			ps.setString(3, esas.getTiredness());
			ps.setString(4, esas.getNausea());
			ps.setString(5, esas.getDepression());
			ps.setString(6, esas.getAnxiety());
			ps.setString(7,esas.getDrowsiness());
			ps.setString(8, esas.getAppetitite());
			ps.setString(9, esas.getWellbeing());
			ps.setString(10, esas.getShortnessOfBreath());
			ps.setDate( 11, new java.sql.Date( esas.getSysdate().getTime() ));
			 success= ps.executeUpdate();		
			con.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return success;
	}
=======

	/**
	 * Saves the body pains information by user into body_part table
	 * 
	 * @param username
	 *            username string pulled from Session of current logged in user
	 * @param bodyPartIndices
	 *            A string of comma separated values of body part indices
	 * @return true if saved, else false
	 */
	public boolean addBodyPainInfo(String username, String bodyPartIndices) {
		try {
			Connection con = dataSource.getConnection();
			String sql = "INSERT INTO body_part (username, bodyparts_indices) VALUES ('"
					+ username + "', '" + bodyPartIndices + "')";
			System.out.println(sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//
	// public List<Patient> findByDesignation(String designation) {
	// List<Patient> employees = new LinkedList<Patient>();
	// try {
	// Connection con = dataSource.getConnection();
	// String sql = "select * from Employee where Designation  = '"
	// + designation + "'";
	// PreparedStatement ps = con.prepareStatement(sql);
	// ResultSet rs = ps.executeQuery();
	// while (rs.next()) {
	// employees.add(new Patient(rs.getInt(1), rs.getString(2), rs
	// .getString(3), rs.getFloat(4)));
	// }
	// con.close();
	// } catch (SQLException exp) {
	// exp.printStackTrace();
	// }
	// return employees;
	// }
>>>>>>> 3b0e94a02c376c2c440e0e7078013692859b5729
}
