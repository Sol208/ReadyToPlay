package commu_bas.guest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class GuestDAO {
	
	Connection con;
	PreparedStatement ptmt;
	ResultSet rs;
	String sql;
		
	public GuestDAO() {
			
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/projectDB");
			con = ds.getConnection();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
}

	
	public int totalCnt() {
		
		sql = "select count(*) from commu_Bas_guest";
		
		try {
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
		
			rs.next();
				
			return rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return 0;
	}
	
	
	// ===================================================================================
	// ===================================================================================
	// ===================================================================================
	// ===================================================================================
	public int myTotalCnt(String user_id) {
		
		sql = "select count(*) from commu_Bas_guest where user_id = ? ";
		
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, user_id);
			rs = ptmt.executeQuery();
		
			rs.next();
				
			return rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return 0;
	}
	


	public int totalCntSearch(String search){
		
		sql = "select count(*) from commu_bas_guest where title like ? ";
		
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1,"%"+search+"%");
			rs = ptmt.executeQuery();
			
			rs.next();
			
			return rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int totalCntSearch(String field, String search){
			
		sql = "select count(*) from commu_bas_guest where " + field + " like ? ";
			
			try {
				ptmt = con.prepareStatement(sql);
				ptmt.setString(1,"%"+search+"%");
				rs = ptmt.executeQuery();
				
				rs.next();
				
				return rs.getInt(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return 0;
		}
	
	
	public ArrayList<GuestDTO> list(int start, int limit, String field, String search) {
		ArrayList<GuestDTO> res = new ArrayList<GuestDTO>();
		
		sql = "select * from commu_Bas_guest "
				+ "where " + field + " like ? order by reg_date desc limit ?, ? ";
		
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, "%"+search+"%");
			ptmt.setInt(2, start);
			ptmt.setInt(3, limit);
			
			rs = ptmt.executeQuery();
		
			while(rs.next()) {
				
				GuestDTO dto = new GuestDTO();
				
				dto.setPost_id(rs.getString("post_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setHead(rs.getString("head"));
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				dto.setCnt(rs.getInt("cnt"));
				
				res.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
		
	}
	
	
	public ArrayList<GuestDTO> list(int start, int limit, String search) {
		ArrayList<GuestDTO> res = new ArrayList<GuestDTO>();
		
		sql = "select * from commu_Bas_guest "
				+ "where title like ? order by reg_date desc limit ?, ? ";
		
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, "%" + search + "%");
			ptmt.setInt(2, start);
			ptmt.setInt(3, limit);
			
			rs = ptmt.executeQuery();
		
			while(rs.next()) {
				
				GuestDTO dto = new GuestDTO();
				
				dto.setPost_id(rs.getString("post_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setHead(rs.getString("head"));
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				dto.setCnt(rs.getInt("cnt"));
				
				res.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
		
	}
	
	public ArrayList<GuestDTO> list(int start, int limit) {
		ArrayList<GuestDTO> res = new ArrayList<GuestDTO>();
		
		sql = "select * from commu_Bas_guest "
				+ "order by reg_date desc limit ?, ? ";
		
		try {
			ptmt = con.prepareStatement(sql);
			
			ptmt.setInt(1, start);
			ptmt.setInt(2, limit);
			
			rs = ptmt.executeQuery();
		
			while(rs.next()) {
				
				GuestDTO dto = new GuestDTO();
				
				dto.setPost_id(rs.getString("post_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setHead(rs.getString("head"));
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				dto.setCnt(rs.getInt("cnt"));
				
				res.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	
	public GuestDTO detail(String post_id) {
		GuestDTO dto = null;
		
		sql = "select * from commu_Bas_guest where post_id =?";
		
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, post_id);
			rs = ptmt.executeQuery(); // 여기에 sql ㄴㄴ
		
			if(rs.next()) {
				
				dto = new GuestDTO();
				
				dto.setPost_id(rs.getString("post_id"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setHead(rs.getString("head"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setUpfile(rs.getString("upfile"));
				dto.setImg(rs.getString("img"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				dto.setCnt(rs.getInt("cnt"));					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return dto;
		
	}
	
	public void addCount(String post_id) {		
		sql = "update commu_Bas_guest set cnt = cnt + 1 where post_id =?";
		
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, post_id);
			ptmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void insert(GuestDTO dto) {	
		
		sql = "insert into commu_Bas_guest (head, title, content, "
				+ "upfile, img, user_id, post_id, reg_date, cnt) values " + "(?, ?, ?, ?, ?, ?, ?, sysdate(), 0)";
		
		try {
			ptmt = con.prepareStatement(sql);
			
			ptmt.setString(1, dto.head);
			ptmt.setString(2, dto.title);
			ptmt.setString(3, dto.content);
			ptmt.setString(4, dto.upfile);
			ptmt.setString(5, dto.img);
			ptmt.setString(6, dto.user_id);
			ptmt.setString(7, dto.post_id);
			
			ptmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close();
		}
		
	}
	
	
	public int modify(GuestDTO dto) {	
		int res = 0;
		
		sql = "update commu_Bas_guest set head =?, title =?, " + "content=?, upfile=?, img=?, "
				+ "where post_id=? ";
		
		try {
			ptmt = con.prepareStatement(sql);
			
			ptmt.setString(1, dto.getHead());
			ptmt.setString(2, dto.getTitle());
			ptmt.setString(3, dto.getContent());
			ptmt.setString(4, dto.getUpfile());
			ptmt.setString(5, dto.getImg());
			ptmt.setString(6, dto.getPost_id());
						
			res = ptmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close();
		}
		return res;
		
	}
	
	public int modifyFile(String post_id) {
		int res = 0;
		
		sql = "update postlist_market set img = ? where post_id = ?";

		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, "");
			ptmt.setString(2, post_id);

			res = ptmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	
	public int delete(GuestDTO dto) {	
		int res = 0;
		
		sql = "delete from commu_Bas_guest where post_id = ? ";
		
		try {
			ptmt = con.prepareStatement(sql);
			
			ptmt.setString(1, dto.post_id);
			
			res = ptmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close();
		}
		return res;
	}
	
	
		
	public void close() {
		if (rs!=null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
		if (ptmt!=null) try { ptmt.close(); } catch (SQLException e) {e.printStackTrace();}
		if (con!=null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
	}

}
