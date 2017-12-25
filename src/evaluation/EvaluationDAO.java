package evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import toilet.ToiletVO;

public class EvaluationDAO {
	PreparedStatement pstmt;
	ResultSet rs;

	private final String INSERT_EVALUATION = "INSERT INTO `evaluation`(`no`, `comment`, `star`, `date`) VALUES (?,?,?,?)";
	private final String SELECT_EVALUATION = "SELECT `comment`, `star`, `date` FROM `evaluation` WHERE no=?";
	
	public boolean insertEvaluation(EvaluationVO vo) {
		boolean result = false;
		Connection conn = JdbcUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(INSERT_EVALUATION);
			pstmt.setInt(1, vo.getNo());
			if(vo.getComment() == null) {
				vo.setComment("");
			}
			pstmt.setString(2, vo.getComment());
			pstmt.setDouble(3, vo.getStar());
			pstmt.setString(4, vo.getDate());

			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcUtil.close(conn);
		JdbcUtil.close(pstmt);
		JdbcUtil.close(rs);
		return result;
	}
	
	public EvaluationVO[] selectEvaluation(int no) {
		Connection conn = JdbcUtil.getConnection();
		EvaluationVO vo[] = null;
		try {
			pstmt = conn.prepareStatement(SELECT_EVALUATION);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			int currentRow = rs.getRow();            // Get current row
			int rowCount = rs.last() ? rs.getRow() : 0; // Determine number of rows
			rs.beforeFirst();                     // We want next() to go to first r
			vo = new EvaluationVO[rowCount];
			int i = 0;
			while(rs.next()) {
				vo[i] = new EvaluationVO();
				vo[i].setComment(rs.getString(1));
				vo[i].setStar(rs.getInt(2));
				vo[i].setDate(rs.getString(3));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcUtil.close(conn);
		JdbcUtil.close(pstmt);
		JdbcUtil.close(rs);
		return vo;
	}
}
