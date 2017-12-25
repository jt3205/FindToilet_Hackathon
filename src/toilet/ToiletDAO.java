package toilet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;import org.apache.catalina.storeconfig.IStoreFactory;

import jdbc.JdbcUtil;

public class ToiletDAO {
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private ToiletVO vo = null;
	private final String INSERT_TOILET = "INSERT INTO `toilet`(`kind`, `name`, `roadAddr`, `areaAddr`, `isUnisex`, `contact`, `opentime`, `x`, `y`, `management`, `forChildren`, `forDisabled`) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String SELECT_POINT = "SELECT `no`, `kind`, `name`, `x`, `y` FROM `toilet`";
	private final String SELECT_TOILET_INFO = "SELECT `no`, `kind`, `name`, `roadAddr`, `areaAddr`, `isUnisex`, `contact`, `opentime`, `x`, `y`, `management`, `forChildren`, `forDisabled` FROM `toilet` WHERE no=?";
	private final String SELECT_AROUND_TOILET = "SELECT `no`, `kind`, `name`, `x`, `y` FROM `toilet` where x < (?+0.015060) && x > (?- 0.015060) && y < (?+0.015060) && y > (?- 0.015060)";
	
	public void insertToilet(Connection conn, ToiletVO vo) {
		try {
			pstmt = conn.prepareStatement(INSERT_TOILET);
			
			pstmt.setString(1, vo.getKind());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getRoadAddr());
			pstmt.setString(4, vo.getAreaAddr());
			pstmt.setBoolean(5, vo.isUnisex());
			pstmt.setString(6, vo.getContact());
			pstmt.setString(7, vo.getOpenTime());
			if(vo.getX() == 0.0 || vo.getY() == 0.0) {
				return;
			}
			pstmt.setDouble(8, vo.getX());
			pstmt.setDouble(9, vo.getY());
			pstmt.setString(10, vo.getManagement());
			pstmt.setBoolean(11, vo.isForChiledren());
			pstmt.setBoolean(12, vo.isForDisabled());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcUtil.close(pstmt);
	}
	
	public ToiletVO[] getToiletPoint() {
		ToiletVO resultVO[] = null;
		
		Connection conn = JdbcUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(SELECT_POINT);
			rs = pstmt.executeQuery();
			int currentRow = rs.getRow();            // Get current row
			int rowCount = rs.last() ? rs.getRow() : 0; // Determine number of rows
			rs.beforeFirst();                     // We want next() to go to first r
			resultVO = new ToiletVO[rowCount];
			rs.beforeFirst();
			int i = 0;
			while(rs.next()) {
				resultVO[i] = new ToiletVO();
				resultVO[i].setNo(rs.getInt(1));
				resultVO[i].setKind(rs.getString(2));
				resultVO[i].setName(rs.getString(3));
				resultVO[i].setX(rs.getDouble(4));
				resultVO[i].setY(rs.getDouble(5));
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcUtil.close(conn);
		JdbcUtil.close(pstmt);
		JdbcUtil.close(rs);
		return resultVO;
	}
	
	public ToiletVO[] getAroundToilet(double x, double y) {
		ToiletVO resultVO[] = null;
		
		Connection conn = JdbcUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(SELECT_AROUND_TOILET);
			pstmt.setDouble(1, x);
			pstmt.setDouble(2, x);
			pstmt.setDouble(3, y);
			pstmt.setDouble(4, y);
			rs = pstmt.executeQuery();
			int currentRow = rs.getRow();            // Get current row
			int rowCount = rs.last() ? rs.getRow() : 0; // Determine number of rows
			rs.beforeFirst();                     // We want next() to go to first r
			resultVO = new ToiletVO[rowCount];
			int i = 0;
			while(rs.next()) {
				resultVO[i] = new ToiletVO();
				resultVO[i].setNo(rs.getInt(1));
				resultVO[i].setKind(rs.getString(2));
				resultVO[i].setName(rs.getString(3));
				resultVO[i].setX(rs.getDouble(4));
				resultVO[i].setY(rs.getDouble(5));
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcUtil.close(conn);
		JdbcUtil.close(pstmt);
		JdbcUtil.close(rs);
		return resultVO;
	}
	
	public ToiletVO getToiletInfo(int no) {
		ToiletVO vo = new ToiletVO();
		Connection conn = JdbcUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(SELECT_TOILET_INFO);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setKind(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setRoadAddr(rs.getString(4));
			vo.setAreaAddr(rs.getString(5));
			vo.setUnisex(rs.getBoolean(6));
			vo.setContact(rs.getString(7));
			vo.setOpenTime(rs.getString(8));
			vo.setX(rs.getDouble(9));
			vo.setY(rs.getDouble(10));
			vo.setManagement(rs.getString(11));
			vo.setForChiledren(rs.getBoolean(12));
			vo.setForDisabled(rs.getBoolean(13));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcUtil.close(conn);
		JdbcUtil.close(pstmt);
		JdbcUtil.close(rs);
		return vo;
	}
}
